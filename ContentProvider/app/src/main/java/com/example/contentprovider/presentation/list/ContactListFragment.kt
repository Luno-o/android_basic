package com.example.contentprovider.presentation.list

import android.content.Intent
import android.net.Uri
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
import com.example.contentprovider.databinding.FragmentContactListBinding
import com.example.contentprovider.presentation.list.adapters.ContactListAdapter
import com.example.contentprovider.utils.autoCleared
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest


class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ContactListViewModel by viewModels()
    private var contactAdapter: ContactListAdapter by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initList()
        binding.addContactButton.setOnClickListener {
            findNavController().navigate(R.id.action_ContactListFragment_to_SecondFragment)
        }
        bindViewModel()
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                android.Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                onPermissionDenied = ::onContactPermissionDenied,
                requiresPermission = {
                    viewModel.loadList()
                    Log.d("ContactFragment", "loadList")
                }
            ).launch()
        }
    }

    private fun initList() {
        contactAdapter = ContactListAdapter(viewModel::callToContact)
        with(binding.contactList) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun toDetailFragment(){
        val action = ContactListFragmentDirections.actionContactListFragmentToSecondFragment()
        findNavController()
    }
    private fun bindViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            contactAdapter.items = it
        }
        viewModel.callLiveData.observe(viewLifecycleOwner, ::callToPhone)
    }

    private fun callToPhone(phone: String) {
        Intent(Intent.ACTION_DIAL)
            .apply {
                data = Uri.parse("tel:$phone")
            }
            .also { startActivity(it) }
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionDenied() {
        Toast.makeText(
            requireContext(), "Доступ к чтению контактов запрещен",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onContactPermissionNeverAskAgain() {
        Toast.makeText(
            requireContext(),
            "Разрешите доступ к чтению контактов в настройках приложения",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}