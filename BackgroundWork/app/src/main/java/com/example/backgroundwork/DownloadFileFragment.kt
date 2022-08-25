package com.example.backgroundwork

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.example.backgroundwork.databinding.FragmentDownloadFileBinding
import com.example.backgroundwork.other.DownloadFileViewModel
import com.example.backgroundwork.other.DownloadService
import com.example.backgroundwork.other.DownloadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import timber.log.Timber
import java.util.concurrent.TimeUnit


class DownloadFileFragment : Fragment() {
    private val receiver = BatteryBroadcastReceiver()
    private var _binding: FragmentDownloadFileBinding? = null
    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>
    private val binding get() = _binding!!
private val viewModel: DownloadFileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDownloadFileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            .observe(viewLifecycleOwner){
                handleWorkInfo(it.first())
            }
initCreateDocumentLauncher()
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onShowRationale = ::onContactPermissionShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                onPermissionDenied = viewModel::permissionDenied,
                requiresPermission = {
                    viewModel.permissionGranted()
                   Timber.d("loadList")
                }
            ).launch()
        }
        binding.startDownloadingButton.setOnClickListener {
            if(!receiver.isBatteryLow() && isConnected(requireContext())){
createFle()
            }else {
                lifecycleScope.launch(Dispatchers.Main){
                    Toast.makeText(context, "Battery low or not connected WI-WI", Toast.LENGTH_SHORT).show()
                }
            }
        }
binding.cancelDownloadButton.setOnClickListener {
    stopDownload()
}
        DownloadState.downloadState.onEach {
            binding.startDownloadingButton.isVisible = !it
            binding.progress.isVisible = it
        }.launchIn(lifecycleScope)
    }
    private fun stopDownload(){
        WorkManager.getInstance(requireContext())
            .cancelUniqueWork(DOWNLOAD_WORK_ID)
    }
    private fun createFle(){
        createDocumentLauncher.launch("new file.txt")
    }
    private fun initCreateDocumentLauncher(){
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()){uri->
handleCreateFile(uri)
        }
    }
    private fun handleCreateFile(uri: Uri?){
        if (uri == null){
            Toast.makeText(context, "File not created", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                startDownload(uri)
            }
        }
    }
    private fun isConnected(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false
        connMgr.allNetworks.forEach { network ->
            connMgr.getNetworkInfo(network)?.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }
        Timber.d("Wifi connected: $isWifiConn")
        Timber.d("Mobile connected: $isMobileConn")
        return isWifiConn
    }
    private fun startDownload(uri: Uri){
        val urlToDownload = binding.urlEditText.text.toString()

        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload,
            DownloadWorker.DOWNLOAD_URI_KEY to uri.toString()
        )
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR,20, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(
                DOWNLOAD_WORK_ID,
                ExistingWorkPolicy.KEEP,workRequest)



    }


    private fun startService(url: String,uri: Uri){
        val downloadIntent = Intent(requireContext(), DownloadService::class.java)
            .putExtra(KEY_URL,url)
            .putExtra(KEY_URI,uri.toString())
        requireContext().startService(downloadIntent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()

    }

    private fun onContactPermissionDenied() {
        Toast.makeText(
            requireContext(), "Доступ к чтению хранилища запрещен",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onContactPermissionNeverAskAgain() {
        Toast.makeText(
            requireContext(),
            "Разрешите доступ к чтению хранилища в настройках приложения",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun handleWorkInfo(workInfo: WorkInfo) {
        Timber.d("handleWorkInfo new state ${workInfo.state}")
        val isShowDownloadButton = workInfo.state == WorkInfo.State.SUCCEEDED
                || workInfo.state == WorkInfo.State.CANCELLED
                || workInfo.state == WorkInfo.State.BLOCKED

        binding.startDownloadingButton.isVisible = isShowDownloadButton
        binding.cancelDownloadButton.isVisible = workInfo.state == WorkInfo.State.RUNNING
                ||workInfo.state == WorkInfo.State.ENQUEUED
        binding.progress.isVisible = workInfo.state == WorkInfo.State.RUNNING
        //  workInfo.state == WorkInfo.State.SUCCEEDED
        binding.retryButton.isVisible = workInfo.state == WorkInfo.State.FAILED
        binding.waitingTextView.isVisible= workInfo.state == WorkInfo.State.ENQUEUED
        if (workInfo.state == WorkInfo.State.SUCCEEDED){
            Toast.makeText(context, "Work finished with state = ${workInfo.state}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            receiver, IntentFilter(
                Intent.ACTION_BATTERY_LOW
            )
        )
        requireContext().registerReceiver(
            receiver, IntentFilter(
                Intent.ACTION_BATTERY_OKAY
            )
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }
    companion object{
        const val KEY_URL = "url_key"
        const val KEY_URI = "uri_key"
        private const val DOWNLOAD_WORK_ID = "download work"
    }
}