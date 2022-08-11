package com.example.scopedstorage.presentation

import android.Manifest
import android.app.Activity
import android.app.RemoteAction
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scopedstorage.R
import com.example.scopedstorage.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest


class VideosFragment : Fragment() {
//https://storage.coverr.co/videos/Cy4xyMjY1NOM00nN26eZNAoDfl2sjN3tS?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6Ijg3NjdFMzIzRjlGQzEzN0E4QTAyIiwiaWF0IjoxNjYwMTIxMjc3fQ.jnCxx386yLeZ3j3H8c1pwMtc2ZaSjX8g1DCvjO5Np4c
//    https://storage.coverr.co/videos/rhOM3iuhDqxedD7lKLpPO34yN2lhf5Kk?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6Ijg3NjdFMzIzRjlGQzEzN0E4QTAyIiwiaWF0IjoxNjYwMTI0NDc4fQ.IQf4CASry7_0ktlFRy5KTVrhj2PAz7JBrbChZ4qdtos
    private var _binding: FragmentFirstBinding? = null
    private val viewModel: VideosFragmentViewModel by viewModels()
    private var videoAdapter: VideoAdapter? = null
    private val binding get() = _binding!!

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.secondFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
//        initPermissionResultListener()
        initRecoverableActionListener()
        initList()
        observeViewModel()
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onShowRationale = ::onContactPermissionShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                onPermissionDenied = viewModel::permissionDenied,
                requiresPermission = {
                    viewModel.permissionGranted()
                    viewModel.getVideo()
                    Log.d("FirstFragment", "loadList")
                }
            ).launch()
        }
        binding.fab.setOnClickListener {
            showDialog()
        }
        parentFragmentManager.setFragmentResultListener(RATIONALE_KEY, this) { _, bundle ->
            val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
            val nameFile = bundle.getString(NAME_KEY)
            val urlFile = bundle.getString(UTL_KEY)
            val uriStringFile = bundle.getString(URI_KEY)
            val uriFile = Uri.parse(uriStringFile)
            if (!nameFile.isNullOrBlank() && !urlFile.isNullOrBlank()) {
                viewModel.saveVideo(nameFile, urlFile)
                Log.d("whatif","working")
            }
            if (!uriFile.path.isNullOrBlank()&& !urlFile.isNullOrBlank()){
                viewModel.saveVideo(uriFile,urlFile)
                Log.d("whatif2","working uri =$uriFile")
            }

        }

    }

    private fun showDialog() {
        RationaleFragment().show(parentFragmentManager, RationaleFragment.TAG)
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

    private fun initList() {
        videoAdapter = VideoAdapter(viewModel::deleteVideo)
        with(binding.recyclerView) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

    }

//    private fun initPermissionResultListener() {
//        requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissionGrantedMap: Map<String, Boolean> ->
//            if (permissionGrantedMap.values.all { it }) {
//                viewModel.permissionGranted()
//            } else {
//                viewModel.permissionDenied()
//            }
//        }
//    }

    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            val isConfirm = activityResult.resultCode == Activity.RESULT_OK
            if (isConfirm) {
                viewModel.confirmDelete()
            } else {
                viewModel.declineDelete()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.imageLiveData.observe(viewLifecycleOwner) {
            videoAdapter?.items = it
        }
        viewModel.permissionGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUI)
        viewModel.recoverableActionLiveData.observe(viewLifecycleOwner) {
            handleRecoverableAction(it)
        }
    }

    private fun handleRecoverableAction(remoteAction: RemoteAction) {
        val request = IntentSenderRequest.Builder(remoteAction.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }

    //    private fun requestPermissions(){
//
//        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
//    }
    private fun updatePermissionUI(isGranted: Boolean) {
        binding.fab.isVisible = isGranted
        recyclerView.isVisible = isGranted
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        videoAdapter = null
    }

    companion object {
        const val RATIONALE_KEY = "rationale_tag"
const val URI_KEY = "uri_key"
        const val RESULT_KEY = "camera_result_key"
        const val NAME_KEY = "name_key"
        const val UTL_KEY = " url_key"

        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE.takeIf {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
            }
        )
    }
}