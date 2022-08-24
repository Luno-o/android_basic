package com.example.backgroundwork

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.backgroundwork.databinding.FragmentSecondBinding
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            .observe(viewLifecycleOwner){
                handleWorkInfo(it.first())
            }
        binding.startDownloadButton.setOnClickListener {
            //in repo
startDownload()
        }
        binding.cancelDownloadButton.setOnClickListener {
            stopDownload()
        }
    }
    private fun stopDownload(){
WorkManager.getInstance(requireContext())
    .cancelUniqueWork(DOWNLOAD_WORK_ID)
    }
private fun startDownload(){
val urlToDownload = binding.urlEditText.text.toString()

    val workData = workDataOf(
DownloadWorker.DOWLOAD_URL_KEY to urlToDownload
    )
    val workConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_ROAMING)
        .build()
    val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
        .setInputData(workData)
        .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)
        .setConstraints(workConstraints)
        .build()

    WorkManager.getInstance(requireContext())
        .enqueueUniqueWork(DOWNLOAD_WORK_ID,ExistingWorkPolicy.REPLACE,workRequest)


}
    private fun handleWorkInfo(workInfo: WorkInfo){
Timber.d("handleWorkInfo new state ${workInfo.state}")

val isFinished = workInfo.state.isFinished
        binding.startDownloadButton.isVisible = isFinished
        binding.cancelDownloadButton.isVisible = !isFinished
        binding.progress.isVisible = !isFinished
      //  workInfo.state == WorkInfo.State.SUCCEEDED
        if (isFinished){
            Toast.makeText(context, "Work finished with state = ${workInfo.state}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        private const val DOWNLOAD_WORK_ID = "download work"
    }
}