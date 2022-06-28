package com.example.lists1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lists1.adapters.TransportAdapter
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragmentStaggeredLayout : Fragment(R.layout.fragment_list), TransportClickListener {

    private var transports = emptyList<Transport>()
    private var transportAdapter: TransportAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initList()
        transportAdapter?.items = transports
        transportAdapter?.notifyDataSetChanged()
        addFab.setOnClickListener {
            TransportDialogFragment.newInstance().show(childFragmentManager, "Dialog Fragment")
        }
    }

    private fun initList() {
        emptyTransport.isVisible = transports.isEmpty()
        transportAdapter = TransportAdapter {
            deleteTransport(it)
        }
        with(transportList) {
            itemAnimator = FlipInLeftYAnimator()
            adapter = transportAdapter
            addItemDecoration(ItemOffsetDecoration(context))
            layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }


    override fun onTransportSelected(transport: Transport) {

        transports = listOf(transport) + transports
        transportAdapter?.items = transports
        transportList.scrollToPosition(0)
        emptyTransport.isVisible = transports.isEmpty()

    }


    private fun deleteTransport(position: Int) {
        transports = transports.filterIndexed { index, _ -> index != position }
        transportAdapter?.items = transports
        emptyTransport.isVisible = transports.isEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        transportAdapter = null

    }

}