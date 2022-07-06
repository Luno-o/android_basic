package com.example.viewmodelandnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.viewmodelandnavigation.adapters.TransportAdapter

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragmentLayout : Fragment(R.layout.fragment_list),
    TransportClickListener {

    private var transportAdapter: TransportAdapter? = null
    private val viewModel: TransportListViewModel by viewModels()
    private val args: ListFragmentLayoutArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        transportAdapter?.notifyDataSetChanged()
        addFab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragmentLayout_to_transportDialogFragment)
        }
        observeViewModelState()
    }

    private fun getLayoutManagerType(): RecyclerView.LayoutManager {
        return when (args.layoutType) {
            LINEAR_LAYOUT -> LinearLayoutManager(context)
            GRID_LAYOUT -> GridLayoutManager(context, 3)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            else -> LinearLayoutManager(context)
        }
    }

    private fun initList() {
        emptyTransport.isVisible = viewModel.transports.value.orEmpty().isEmpty()
        transportAdapter = TransportAdapter( {deleteTransport(it)}) {

            val action = viewModel.transports.value?.get(it)?.let { transport ->
                ListFragmentLayoutDirections.actionListFragmentLayoutToDetailFragment(
                    transport
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }

        with(transportList) {
            itemAnimator = SlideInUpAnimator()
            adapter = transportAdapter

            layoutManager = getLayoutManagerType()
            setHasFixedSize(true)
        }
    }

    override fun onTransportSelected(transport: Transport) {
        viewModel.addTransport(transport)
        transportList.scrollToPosition(0)
    }


    private fun deleteTransport(position: Int) {
        viewModel.deleteTransport(position)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        transportAdapter = null

    }

    private fun observeViewModelState() {
        viewModel.transports.observe(viewLifecycleOwner) { newTransports ->
            transportAdapter?.items = newTransports
            emptyTransport.isVisible = viewModel.transports.value.orEmpty().isEmpty()
        }
        viewModel.showToast.
        observeSingleEvent(viewLifecycleOwner){
            Toast.makeText(requireContext(),"Транспорт удален",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LINEAR_LAYOUT = 1
        const val GRID_LAYOUT = 2
        const val STAGGERED_LAYOUT = 3

    }

}