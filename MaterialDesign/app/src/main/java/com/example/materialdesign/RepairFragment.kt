package com.example.materialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.materialdesign.databinding.FragmentRepairBinding
import com.google.android.material.snackbar.Snackbar


class RepairFragment : Fragment() {

    private var _binding: FragmentRepairBinding? = null

    private val binding get() = _binding!!
private var shipAdapter: CardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRepairBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateShip()
        populateShip()
        populateShip()

with(binding){
    with(recyclerView){
        shipAdapter = CardAdapter(shipList)
        layoutManager = GridLayoutManager(requireContext(),2)
        adapter = shipAdapter
        setHasFixedSize(true)
    }
}
        showSnackbar()

    }

private fun populateShip(){

    shipList.add(
        PirateShip("Little Pirate Ship","Not large, but fast pirate ship",
"https://kartinkin.net/uploads/posts/2021-07/1625793012_16-kartinkin-com-p-piratskie-oboi-krasivie-17.jpg"
    )
    )
    shipList.add(
        PirateShip("Large Pirate Ship","large, but slow pirate ship",
            "https://thumb.shopotam.com/large/7a/cb/7acb993881b195126977f5b9d6647a52733295a9.jpg"
        )
    )
}
private fun showSnackbar(){

//    Snackbar.make(requireActivity().findViewById(android.R.id.content),"Test snackbar",Snackbar.LENGTH_SHORT)
//        .show()

}
}