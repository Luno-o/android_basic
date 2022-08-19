package com.example.notifications

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notifications.databinding.FragmentMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {
    val category = "com.example.category.IMG_SHARE_TARGET"
private val receiver = BatteryBroadcastReceiver()
    private val binding by viewBinding(FragmentMainBinding::bind, onViewDestroyed = {
        vb:FragmentMainBinding->
        //reset view
    })



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
with(binding){
synchronizeButton.setOnClickListener {
    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
}

    toFirebaseFragmentButton.setOnClickListener {
        findNavController().navigate(R.id.action_FirstFragment_to_firebaseFragment)
    }
}
    }



    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(receiver, IntentFilter(
            Intent.ACTION_BATTERY_LOW
        ))
        requireContext().registerReceiver(receiver, IntentFilter(
            Intent.ACTION_BATTERY_OKAY
        ))
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }
    companion object{

        private const val SUMMARY_NOTIFICATION_ID = 12346
        private const val MESSAGE_NOTIFICATION_ID = 12344
    }
}