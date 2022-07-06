package com.example.viewmodelandnavigation

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.viewmodelandnavigation.R
import kotlinx.android.synthetic.main.fragment_transport_dialog.*


class TransportDialogFragment : DialogFragment(R.layout.fragment_transport_dialog) {
    private val pictures = listOf(
        "https://proprikol.ru/wp-content/uploads/2020/12/samolety-krasivye-kartinki-27.jpg",
        "https://www.stickbutik.ru/upload/resize_cache/iblock/7dd/800_800_1/7ddbd5e323954c017532d98c81d916ab.jpg",
        "https://sc01.alicdn.com/kf/H8825c5e203f043708151d1d68acd7d87O/233450101/H8825c5e203f043708151d1d68acd7d87O.jpg",
        "https://sc02.alicdn.com/kf/HTB18pheXsTxK1Rjy0Fgq6yovpXaz/231320591/HTB18pheXsTxK1Rjy0Fgq6yovpXaz.jpg",
        "https://files.pravda-nn.ru/2018/09/apakp.jpg",
        "https://avatars.mds.yandex.net/get-zen_doc/2783222/pub_6214d2dbf33cc11de10907bc_6214e15f6b5340372f03d123/scale_1200",
        "https://avatars.mds.yandex.net/get-ynews/5562908/834bcf3ad55ca875ea36917a61b5089a/796x448",
        "http://ryazan-news.net/img/20220620/62755fc7f1e790540fb8178a3fc36bc8.jpg",
        "https://proprikol.ru/wp-content/uploads/2020/03/kartinki-avtobusy-36.jpg"
    )
    private val transportClickListener:TransportClickListener?
        get() = parentFragmentManager.primaryNavigationFragment?.let { it as ListFragmentLayout }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aircraftTextView = view.findViewById<TextView>(R.id.enterFlightRangeTextView)
        val aircraftEditText = view.findViewById<TextView>(R.id.flightRangeEditText)
        val watercraftTextView = view.findViewById<TextView>(R.id.enterDisplacementTextView)
        val watercraftEditText = view.findViewById<TextView>(R.id.displacementEditText)
        val roadTransportTextView = view.findViewById<TextView>(R.id.enterFuelConsumptionTextView)
        val roadTransportEditText = view.findViewById<TextView>(R.id.fuelConsumptionEditText)
        val spinner = view.findViewById<Spinner>(R.id.transportSpinner)
val id = (requireActivity() as MainActivity).getId()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0->
                    {aircraftEditText?.visibility = View.VISIBLE
                        aircraftTextView?.visibility = View.VISIBLE
                        modelTransport.text = getString(R.string.choose_model,getString(R.string.aircraft))
                        watercraftEditText?.visibility = View.GONE
                        watercraftTextView?.visibility = View.GONE
                        roadTransportEditText?.visibility = View.GONE
                        roadTransportTextView?.visibility = View.GONE
                        transportType =0
                    }
                    1->
                    {aircraftEditText?.visibility = View.GONE
                        aircraftTextView?.visibility = View.GONE
                        watercraftEditText?.visibility = View.VISIBLE
                        watercraftTextView?.visibility = View.VISIBLE
                        modelTransport.text = getString(R.string.choose_model,getString(R.string.watercraft))
                        roadTransportEditText?.visibility = View.GONE
                        roadTransportTextView?.visibility = View.GONE
                        transportType = 1
                    }
                    2->
                    {aircraftEditText?.visibility = View.GONE
                        aircraftTextView?.visibility = View.GONE
                        watercraftEditText?.visibility = View.GONE
                        watercraftTextView?.visibility = View.GONE
                        roadTransportEditText?.visibility = View.VISIBLE
                        roadTransportTextView?.visibility = View.VISIBLE
                        modelTransport.text = getString(R.string.choose_model,getString(R.string.road_transport))
                        transportType = 2
                    }
                    else->   Toast.makeText(requireContext(),"something wrong",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(),"nothing",Toast.LENGTH_SHORT).show()
            }
        }
        buttonAccept.setOnClickListener {
            when(transportType){
                0->{val airCraft = Transport.AirCraft(id,modelEditText.text.toString(),pictures[(0..2).random()],flightRangeEditText.text.toString())
                transportClickListener?.onTransportSelected(airCraft)
                }
                1->{val watercraft = Transport.WaterCraft(id,modelEditText.text.toString(),pictures[(3..5).random()],watercraftEditText.text.toString())
                    transportClickListener?.onTransportSelected(watercraft)}
                2->{val roadTransport = Transport.RoadTransport(id,modelEditText.text.toString(),pictures[(6..8).random()],fuelConsumptionEditText.text.toString())
                    transportClickListener?.onTransportSelected(roadTransport)}
            }
dialog?.dismiss()
        }
        buttonCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }
companion object{
    private var transportType = 0
}

}