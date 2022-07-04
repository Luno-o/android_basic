package com.example.permissionsanddate
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


class MainActivity : AppCompatActivity() {
    private val REQUEST_AVAILABLE = 10
    private val errorRequest = 11
    override fun onCreate(savedInstanceState: Bundle?) {
    val isLocationServiceAvailable = GoogleApiAvailability().isGooglePlayServicesAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isLocationServiceAvailable == ConnectionResult.SUCCESS) {
            if (checkPermissions()) {
                showLocationListFragment()
            } else {
                showRequestPermissionFragment()

            }
        }
        if (isLocationServiceAvailable == ConnectionResult.SERVICE_UPDATING){
            GoogleApiAvailability().showErrorDialogFragment(this,isLocationServiceAvailable,REQUEST_AVAILABLE)
        }
        else{
            val dialog = GoogleApiAvailability().getErrorDialog(this,isLocationServiceAvailable,errorRequest)
            dialog?.setCancelable(false)
            dialog?.setOnDismissListener { this.finish() }
            dialog?.show()


        }
    }


    private fun showRequestPermissionFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityContainer, RequestPermissionPlugFragment())
            .commit()
    }

    private fun checkPermissions(): Boolean {
        val isCoarseLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isFineLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return isFineLocationPermissionGranted && isCoarseLocationPermissionGranted
    }

    private fun showLocationListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityContainer, LocationListFragment())
            .commit()
    }
}