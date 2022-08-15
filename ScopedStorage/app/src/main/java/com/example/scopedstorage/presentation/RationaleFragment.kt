package com.example.scopedstorage.presentation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.scopedstorage.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.f_rationale.*

class RationaleFragment : BottomSheetDialogFragment() {
    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>
    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreateDocumentLauncher()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_rationale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val url = urlEditText.text.toString()
            parentFragmentManager.setFragmentResult(
                VideosFragment.RATIONALE_KEY,
                bundleOf(VideosFragment.RESULT_KEY to true,
                VideosFragment.NAME_KEY to name,
                VideosFragment.UTL_KEY to url)
            )
            dismiss()
        }
        downloadToDirectoryButton.setOnClickListener {
            createFile()
        }
    }
    private fun initCreateDocumentLauncher() {

        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()
        ) {

            handleCreateFile(uri = it)
        }
    }
    private fun handleCreateFile(uri: Uri?) {
        if (uri == null) {
            Toast.makeText(context, "file not created", Toast.LENGTH_SHORT).show()
            return
        } else {
            val url = urlEditText.text.toString()
            Toast.makeText(context, "file not created url = $url", Toast.LENGTH_SHORT).show()
            parentFragmentManager.setFragmentResult(
                VideosFragment.RATIONALE_KEY,
                bundleOf(VideosFragment.RESULT_KEY to true,
                    VideosFragment.URI_KEY to uri.toString(),
                VideosFragment.UTL_KEY to url)
            )
            dismiss()
//            lifecycleScope.launch(Dispatchers.IO) {
//                requireContext().contentResolver.openOutputStream(uri)?.bufferedWriter()
//                    ?.use {
//                        it.write("test text file")
//                    }
//            }
        }
    }
    private fun createFile() {
        createDocumentLauncher.launch("new file.mp4")
    }
    companion object {
        const val TAG = "rationale_tag"
    }
}