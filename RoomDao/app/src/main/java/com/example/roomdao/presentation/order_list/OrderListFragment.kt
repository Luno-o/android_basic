package com.example.roomdao.presentation.order_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdao.R
import com.example.roomdao.databinding.FragmentOrderListBinding
import com.example.roomdao.presentation.order_list.adapter.ProductWithCountAdapter
import kotlinx.android.synthetic.main.fragment_order_list.*
import kotlinx.android.synthetic.main.fragment_products.*


class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private var productWithCountAdapter: ProductWithCountAdapter? = null
private val viewModel:OrderListViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
initList()
        observeViewModel()
        viewModel.loadList()
        binding.buyButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    private fun initList(){
        productWithCountAdapter = ProductWithCountAdapter()
        with(recyclerViewOrder){
            adapter = productWithCountAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
    private fun observeViewModel(){
        viewModel.productLiveData.observe(viewLifecycleOwner){
            productWithCountAdapter?.items =it
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productWithCountAdapter = null
    }
}