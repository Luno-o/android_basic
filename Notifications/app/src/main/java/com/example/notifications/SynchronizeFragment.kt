package com.example.notifications

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.example.notifications.databinding.FragmentSynchronizeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SynchronizeFragment : Fragment() {
    private val receiver = NetworkBroadcastReceiver()
    private var _binding: FragmentSynchronizeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSynchronizeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.synchronizeButton.setOnClickListener {
            synchronizeData(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }

    private fun synchronizeData(context: Context) {
        if (!receiver.isConnected(context)) Toast.makeText(
            context,
            "Need Wi-Fi connection",
            Toast.LENGTH_SHORT
        ).show()
        else {
            binding.synchronizeButton.isEnabled = false
            showSynchronizingNotification(context)
        }
    }

    private fun showSynchronizingNotification(context: Context) {

        val notificationBuilder =
            NotificationCompat.Builder(context, NotificationChannels.INFO_CHANNEL_ID)
                .setContentTitle("Synchronizing")
                .setContentText("Synchronize in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.drawable.ic_notifications)

        val maxProgress = 10
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()
                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)
                delay(500)
            }
            val finalNotification = notificationBuilder
                .setContentText("Synchronizing is completed")
                .setProgress(0, 0, false)
                .build()
            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
            delay(1000)
            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
            withContext(Dispatchers.Main){
                binding.synchronizeButton.isEnabled = true
            }
        }

    }


    companion object {
        private const val PROGRESS_NOTIFICATION_ID = 12347
    }
}