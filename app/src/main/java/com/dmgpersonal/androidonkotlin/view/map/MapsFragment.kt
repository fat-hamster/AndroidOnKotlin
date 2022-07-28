package com.dmgpersonal.androidonkotlin.view.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dmgpersonal.androidonkotlin.MyApp
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.databinding.FragmentMapsBinding
import com.dmgpersonal.androidonkotlin.model.getAddress
import com.dmgpersonal.androidonkotlin.model.getDefaultCity
import com.dmgpersonal.androidonkotlin.utils.REQUEST_CODE_READ_CONTACTS
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private var currentLocation = getDefaultCity()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        val currentPosition = LatLng(currentLocation.lat, currentLocation.lon)
        googleMap.addMarker(MarkerOptions()
            .position(currentPosition).title("Marker in ${currentLocation.name}"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12F))
    }

    companion object {
        fun newInstance() = MapsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        getCurrentLocation()

        binding.addressButton.setOnClickListener {
            if (binding.addressEditText.text.isNullOrEmpty()) {

            }
        }
    }

    /*************************************** Location ***************************************/
    private fun checkPermission(permission: String, title: String, message: String): Boolean {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else if (permResult != PackageManager.PERMISSION_GRANTED) {
            permissionRequest(permission)
        } else {
            return true
        }
        return false
    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    private fun getCurrentLocation() {
        val locationManager =
            MyApp.appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLocation = getAddress(location.latitude, location.longitude)
            }
        }

        if (hasNetwork || hasGps && checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                getString(R.string.location_alert_title),
                getString(R.string.location_alert_request_text)
            )) {
            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500,
                    0F, locationListener)
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500,
                    0F, locationListener)
            }
        }
    }
}