package com.example.contentprovider.presentation.add

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentprovider.R
import com.example.contentprovider.databinding.FragmentSecondBinding
import com.example.contentprovider.presentation.list.adapters.ContactListAdapter
import kotlinx.android.synthetic.main.fragment_second.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }
private fun saveContactWithPermissionCheck(){
    constructPermissionsRequest(
        android.Manifest.permission.WRITE_CONTACTS,
        onShowRationale = :: onContactPermissionShowRationale,
        onNeverAskAgain = :: onContactPermissionNeverAskAgain,
        onPermissionDenied = :: onContactPermissionDenied,
        requiresPermission = {viewModel.save(
            name = getFullName(),
            phone = phoneEditText.text.toString(),
            email = mailEditText.text.toString()
        )}
    ).launch()
}
    private fun getFullName():String{
       return nameEditText.text.toString() + " " + secondNameEditText.text.toString()
    }
    private fun bindViewModel(){
        binding.addNewContactButton.setOnClickListener {
            saveContactWithPermissionCheck()
        }
        viewModel.saveErrorLiveData.observe(viewLifecycleOwner){
            Toast.makeText(context, "save error", Toast.LENGTH_SHORT).show()
        }
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner){
        findNavController().navigate(R.id.action_SecondFragment_to_ContactListFragment)
        }
    }
    private fun onContactPermissionShowRationale(request: PermissionRequest){
        request.proceed()
    }
    private fun onContactPermissionDenied(){
        Toast.makeText(requireContext(), "Доступ к записи контактов запрещен",
            Toast.LENGTH_SHORT).show()
    }
    private fun onContactPermissionNeverAskAgain(){
        Toast.makeText(requireContext(),
            "Разрешите доступ к чтению контактов в настройках приложения",
            Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}