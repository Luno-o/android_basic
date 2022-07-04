package com.example.permissionsanddate

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.fragment_request_permission_plug.*


class RequestPermissionPlugFragment : Fragment(R.layout.fragment_request_permission_plug) {

private val singlePermission =
        registerForActivityResult(RequestPermission()){ granted->
            when{
                granted->{
parentFragmentManager.beginTransaction()
    .replace(R.id.mainActivityContainer,LocationListFragment())
    .commit()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)->{
openSettings()
                }
                else->{
                    Toast.makeText(activity,"Доступ запрещен",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        permissionButton.setOnClickListener {
            requestLocationPermission()

        }
    parentFragmentManager.setFragmentResultListener(RATIONALE_KEY,this) { _, bundle ->
        val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
        if (isWantToAllowAfterRationale) {
            singlePermission.launch(Manifest.permission.
            ACCESS_COARSE_LOCATION)
        }
    }
    }
    private fun requestLocationPermission() {
if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)){
  showRationaleDialog()
}
        else{
            singlePermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun showRationaleDialog() {
        RationaleFragment().show(parentFragmentManager, RationaleFragment.TAG)
    }
    companion object {
        const val RATIONALE_KEY = "rationale_tag"

        const val RESULT_KEY = "camera_result_key"
    }
}