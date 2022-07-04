package com.example.permissionsanddate

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_location_list.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.text.FieldPosition
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.nextInt

private const val LOCATION_ARRAY = "location array"
private const val ARG_PARAM2 = "param2"


class LocationListFragment : Fragment(R.layout.fragment_location_list) {
    private var locationList: ArrayList<DatasetLocation> = java.util.ArrayList()
    private val imageLinks = listOf(
        "https://img.freepik.com/free-photo/map-location-marker-icon-illustration_53876-64685.jpg?t=st=1656680728~exp=1656681328~hmac=af0d77c6b901dde31a1c5a44d0a2a815d398a4963a8b95ab69bb1b664aef960e&w=1060",
        "https://img.freepik.com/free-photo/map-pin-location-direction-position-graphic_53876-124530.jpg?t=st=1656680728~exp=1656681328~hmac=3dd17d5b3ad841e240dd7a21c30ab9884c9c699cfbe098a012739c7837c98ab4&w=1060",
        "https://img.freepik.com/free-photo/aerial-view-of-city-buildings-3d-rendering-with-red-point-map_1379-4752.jpg?w=1060"
    )
    private var locationAdapter: DatasetLocationAdapter? = null
    private var selectedLocationDateInstant: Instant? = null
    private lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            locationList = savedInstanceState.getParcelableArrayList(LOCATION_ARRAY)!!
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.size > 1)
                    showLastLocation()
                Toast.makeText(requireContext(),"locationUpdateCallback",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        LocationServices.getFusedLocationProviderClient(requireContext()).requestLocationUpdates(
            com.google.android.gms.location.LocationRequest.create().apply { interval = TimeUnit.SECONDS.toMillis(10)
                                                                           fastestInterval = TimeUnit.SECONDS.toMillis(5)
                                                                           maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                                                                           priority = LocationRequest.QUALITY_HIGH_ACCURACY},
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()

        addLocationButton.setOnClickListener {
            showLastLocation()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LOCATION_ARRAY, locationList)
    }

    private fun showLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainActivityContainer, RequestPermissionPlugFragment())
                .commit()
        }

        LocationServices.getFusedLocationProviderClient(requireContext())
            .lastLocation
            .addOnSuccessListener {
                val dataset = DatasetLocation(
                    Random.nextInt(),
                    Instant.now(),
                    it,
                    imageLinks[Random.nextInt(0..2)]
                )
                addLocation(dataset)
            }
    }

    private fun initList() {
        noLocationsTextView.isVisible = locationList.isEmpty()
        locationAdapter = DatasetLocationAdapter {
            initTimePicker(it)
        }
        with(locationListRecyclerView) {
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun addLocation(dataset: DatasetLocation) {

        val emptyArrayList = ArrayList<DatasetLocation>(locationList.size + 1)
        emptyArrayList.add(dataset)
        emptyArrayList.addAll(locationList)
        locationList = emptyArrayList
        locationAdapter?.items = locationList
        locationListRecyclerView.scrollToPosition(0)
        noLocationsTextView.isVisible = locationList.isEmpty()
    }

    private fun initTimePicker(position: Int) {

        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        selectedLocationDateInstant =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneId.systemDefault()).toInstant()
                        locationList[position].timeStamp = selectedLocationDateInstant!!
                        locationAdapter?.notifyItemChanged(position)
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                ).show()
            },
            currentDateTime.year,
            currentDateTime.monthValue - 1,
            currentDateTime.dayOfMonth
        ).show()
        selectedLocationDateInstant = null
    }

}