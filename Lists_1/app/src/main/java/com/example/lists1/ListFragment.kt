package com.example.lists1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(R.layout.fragment_list), TransportClickListener {

    private var transports = emptyList<Transport>()
    private var transportsArrayList:java.util.ArrayList<Transport>? = java.util.ArrayList<Transport>()
    private var transportAdapter: TransportAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (savedInstanceState !== null) {
                transportsArrayList =arguments?.getParcelableArrayList(TRANSPORT_KEY)
                Log.d("load", "in let ${transportsArrayList?.size}")
                if (transportsArrayList !== null) {
                    transports = transportsArrayList!!.toList()
                    Log.d("load", "number ${transports.size}")
                }
            }

        initList()
        transportAdapter?.updateTransports(transports)
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
            adapter = transportAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }


    override fun onTransportSelected(transport: Transport) {

        transports = listOf(transport) + transports
        transportAdapter?.updateTransports(transports)
        transportAdapter?.notifyItemInserted(0)
        transportList.scrollToPosition(0)
        emptyTransport.isVisible = transports.isEmpty()

    }


    private fun deleteTransport(position: Int) {
        transports = transports.filterIndexed { index, _ -> index != position }
        transportAdapter?.updateTransports(transports)
        transportAdapter?.notifyItemRemoved(position)
        emptyTransport.isVisible = transports.isEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        transportAdapter = null

    }

    companion object {
        private const val TRANSPORT_KEY = "Transport key"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        transportsArrayList?.addAll(transports)
        arguments?.putParcelableArrayList(TRANSPORT_KEY,transportsArrayList)

    }

}