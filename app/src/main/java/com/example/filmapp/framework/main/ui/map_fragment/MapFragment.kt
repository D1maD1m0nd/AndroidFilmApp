package com.example.filmapp.framework.main.ui.map_fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.io.IOException


class MapFragment : Fragment() {
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = ArrayList()
    private lateinit var binding: FragmentMapBinding
    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {

        } else {
            Toast.makeText(context, getString(R.string.not_permission), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }

        val initialPlace = LatLng(DEF_LAT, DEF_LAG)
        val marker = googleMap.addMarker(
            MarkerOptions().position(initialPlace).title(getString(R.string.start_marker))
        )
        marker?.let { markers.add(it) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        googleMap.setOnMapLongClickListener { latLng ->
            setMarker(latLng, getString(R.string.long_click))
            getAddressAsync(latLng)
            drawLine()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        registerForContextMenu(binding.mode)
        binding.mode.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.showContextMenu()
            }
        }
        mapFragment?.getMapAsync(callback)
        initSearchByAddress()
    }

    private fun checkPermission() {
        activity?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                }
                else -> {
                    requestPermission()
                }
            }
        }

    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.map_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map_mode_normal -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.menu_map_mode_satellite -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.menu_map_mode_terrain -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            R.id.menu_map_traffic -> {
                map.isTrafficEnabled = !map.isTrafficEnabled
                return true
            }
        }

        return false
    }

    private fun initSearchByAddress() = with(binding) {
        buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, MAX_RESULT)
                    if (addresses.isNotEmpty()) {
                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun getAddressAsync(location: LatLng) = with(binding) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val addresses =
                        geoCoder.getFromLocation(location.latitude, location.longitude, MAX_RESULT)
                    textAddress.post {
                        textAddress.text = addresses.first().getAddressLine(DEF_INDEX)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(WIDTH)
            )
        }
    }

    private fun goToAddress(addresses: MutableList<Address>, view: View, searchText: String) {
        val location = LatLng(addresses.first().latitude, addresses.first().longitude)
        view.post {
            setMarker(location, searchText)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    ZOOM
                )
            )
        }
    }

    private fun setMarker(location: LatLng, searchText: String) {
        val marker = map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )
        marker?.let { markers.add(marker) }
    }

    companion object {
        const val DEF_INDEX = 0
        const val MAX_RESULT = 1
        const val DEF_LAG = 34.102417
        const val DEF_LAT = 44.952117
        const val WIDTH = 5F
        const val ZOOM = 15F
        fun newInstance() = MapFragment()
    }
}