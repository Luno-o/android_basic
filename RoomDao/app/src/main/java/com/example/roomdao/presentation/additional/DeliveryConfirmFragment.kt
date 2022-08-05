package com.example.roomdao.presentation.additional

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomdao.R
import com.example.roomdao.data.db.models.CustomerWithAddress
import kotlinx.android.synthetic.main.fragment_delivery_confirm.*


class DeliveryConfirmFragment : Fragment() {

private val viewModel : DeliveryConfirmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_delivery_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
observeViewModel()
        viewModel.getCustomerWithAddress()
            confirmDeliveryButton.setOnClickListener{
                confirmOrder()
                Toast.makeText(context,"Order Finished",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_deliveryConfirmFragment_to_FirstFragment)
            }
    }

    private fun observeViewModel(){
        return viewModel.customerWithAddressLiveData.observe(viewLifecycleOwner){customer->
            addressEditText.setText(customer.address.deliveryAddress)
            emailEditText.setText(customer.customer.email)
            contactPhoneEditText.setText(customer.customer.contactPhone)
            firstNameEditText.setText(customer.customer.firstName)
            lastNameEditText.setText(customer.customer.lastName)
        }
    }

    private fun confirmOrder(){
        viewModel.confirmOrder()
    }
}