//package com.example.scopedstorage.presentation
//
//import android.net.Uri
//import android.os.Bundle
//import android.provider.DocumentsContract
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.example.scopedstorage.BuildConfig
//import com.example.scopedstorage.R
//import com.example.scopedstorage.databinding.FragmentSecondBinding
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import timber.log.Timber
//import timber.log.Timber.DebugTree
//
//
//class SecondFragment : Fragment() {
//
//    private var _binding: FragmentSecondBinding? = null
//    private lateinit var openDocumentLauncher: ActivityResultLauncher<Array<String>>
//
//    private lateinit var selectDocumentDirectoryLauncher: ActivityResultLauncher<Uri?>
//
//
//    private val binding get() = _binding!!
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initOpenDocumentLauncher()
//        initSelectDocumentFolderLauncher()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(DebugTree())
//        }
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        _binding = FragmentSecondBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.readFileButton.setOnClickListener {
//            readFile()
//        }
//
//        binding.selectDirectoryButton.setOnClickListener {
//            selectDirectory()
//        }
//
//
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
//    }
//
//    private fun initOpenDocumentLauncher() {
//        openDocumentLauncher = registerForActivityResult(
//            ActivityResultContracts.OpenDocument()
//        ) { uri ->
//            handleOpenDocument(uri)
//        }
//    }
//
//    private fun readFile() {
//        openDocumentLauncher.launch(arrayOf("text/plain"))
//    }
//
//
//
//
//
//    private fun initSelectDocumentFolderLauncher() {
//
//        selectDocumentDirectoryLauncher = registerForActivityResult(
//            ActivityResultContracts.OpenDocumentTree()
//        ) {
//            handleSelectDirectory(uri = it)
//        }
//    }
//
//    private fun handleSelectDirectory(uri: Uri?) {
//        if (uri == null) {
//            Toast.makeText(context, "not selected directory", Toast.LENGTH_SHORT).show()
//            return
//        } else {
//            Toast.makeText(context, "selected directory $uri", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun selectDirectory() {
//        selectDocumentDirectoryLauncher.launch(null)
//    }
//
//    private fun handleOpenDocument(uri: Uri?) {
//
//        if (uri == null) {
//            Toast.makeText(context, "not selected", Toast.LENGTH_SHORT).show()
//            return
//        } else {
//            lifecycleScope.launch(Dispatchers.IO) {
//                requireContext().contentResolver.openInputStream(uri)
//                    ?.bufferedReader()
//                    ?.use {
//                        val content = it.readLines().joinToString("\n")
//                        Timber.d("read content\n$content")
//                    }
//            }
//        }
//    }
//
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}