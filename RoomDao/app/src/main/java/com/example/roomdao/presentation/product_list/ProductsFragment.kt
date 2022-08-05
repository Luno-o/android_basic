package com.example.roomdao.presentation.product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdao.R
import com.example.roomdao.data.db.models.*
import com.example.roomdao.databinding.FragmentProductsBinding
import com.example.roomdao.presentation.product_list.adapter.ProductAdapter
import kotlinx.android.synthetic.main.fragment_products.*
import org.threeten.bp.Instant


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
private var productAdapter: ProductAdapter? = null
    private val binding get() = _binding!!
private val viewModel: ProductListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel.addAddress(Address(1,"Great wall 4"))
       viewModel.addCustomer(Customer(1,1,"yui@maol.ti",
            "23239222","Yui","Hong",null))
        viewModel.addProduct(Product(1,"motorcycle",10000, null,"faster in the world"))
        viewModel.addProduct(Product(2,"bicycle",1000, null,"faster then motorcycle"))
        viewModel.addProduct(Product(3,"scooter",100, null,"if you don't like people byu this!!!"))
        viewModel.addOrder(Order(1,1,OrderStatuses.CREATED
            , Instant.now()
        ))

        observeViewModel()
initList()
        viewModel.loadList()

        binding.placeAnOrderButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun initList(){
        productAdapter = ProductAdapter(viewModel::addProductToOrder)
        with(recyclerViewProduct){
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productAdapter = null
    }
    private fun observeViewModel(){
        viewModel.productLiveData.observe(viewLifecycleOwner){
            productAdapter?.items = it
        }
    }
}