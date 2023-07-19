package com.example.pupuku.ui

import android.app.Application
import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pupuku.R
import com.example.pupuku.application.FertilizerApplication
import com.example.pupuku.databinding.FragmentSecondBinding
import com.example.pupuku.model.Fertilizer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val fertilizerViewModel: FertilizerViewModel by viewModels {
        FertilizerViewModelFactory((applicationContext as FertilizerApplication).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var fertilizer: Fertilizer? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fertilizer = args.fertilizer
        // kita cek jika fertilizer null maka tampilan deflaut nambah  distributor pupuk
        // jika fertilizer tidak nul tampilan sedikit berubah ada tombol hapus dan pupuk
        if (fertilizer != null) {
            binding.SaveButton.text = "Ubah"
            binding.nameEditText.setText(fertilizer?.name)
            binding.addressEditText.setText(fertilizer?.address)
            binding.typeEditText.setText(fertilizer?.type)
        }


        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
checkPermission()

        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val type = binding.typeEditText.text
        binding.SaveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (type.isEmpty()) {
                Toast.makeText(context, "Jenis Pupuk tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                //k
                if (fertilizer == null) {
                    val fertilizer =
                        Fertilizer(0, name.toString(), address.toString(), type.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    fertilizerViewModel.insert(fertilizer)
                } else {
                    val fertilizer = Fertilizer(
                        fertilizer?.id!!, name.toString(), address.toString(), type.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    fertilizerViewModel.update(fertilizer)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini

            }

            binding.DeleteButton.setOnClickListener {
                fertilizer?.let { fertilizerViewModel.delete(it) }
                findNavController().popBackStack()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) { 
        mMap = googleMap
        // implement drag marker
        mMap.setOnMarkerDragListener(this)

val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // ngecek jika permissin tidak disetujui maka akan berhenti kondisi if
       if (ActivityCompat.checkSelfPermission(
               applicationContext,
               android.Manifest.permission.ACCESS_FINE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED
       ) {
           return
       }

//untuk test current Location coba run di device langsung atau build apknya terus install di hp masing-masing
      fusedLocationClient.lastLocation
          .addOnSuccessListener { location ->
              if (location != null) {
                  var latLang = LatLng(location.latitude, location.longitude)
                  currentLatLang = latLang
                  var title = "Marker"

                  if (fertilizer != null) {
                      title = fertilizer?.name.toString()
                      val newCurrentLocation = LatLng(fertilizer?.latitude!!, fertilizer?.longitude!!)
                      latLang = newCurrentLocation
                  }
                  val markerOption = MarkerOptions()
                      .position(latLang)
                      .title(title)
                      .draggable(true)
                      .icon(BitmapDescriptorFactory.fromResource(R.drawable.fertilizer))
                  mMap.addMarker(markerOption)
                  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
              }
          }
    }
}



