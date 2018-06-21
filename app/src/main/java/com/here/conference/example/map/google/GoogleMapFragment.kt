package com.here.conference.example.map.google


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.here.conference.example.map.AppMapFragment
import com.here.conference.example.map.CameraState
import com.here.conference.example.map.MapsAdapter
import com.here.conference.example.map.R
import kotlinx.android.synthetic.main.fragment_google_map.*
import nz.co.trademe.mapme.googlemaps.GoogleMapAnnotationFactory

class GoogleMapFragment : AppMapFragment<GoogleMap>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleMapView.onCreate(savedInstanceState)
    }

    override fun createMapAdapter(): MapsAdapter<GoogleMap> = MapsAdapter(requireContext(), GoogleMapAnnotationFactory())

    override fun initMap(onInit: (GoogleMap) -> Unit) {
        googleMapView.getMapAsync(onInit)
    }

    override fun onChanged(cameraState: CameraState?) {
        when (cameraState) {

            is CameraState.Center -> {
                val center = LatLng(cameraState.center.latitude, cameraState.center.longitude)
                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(center, cameraState.zoomLevel.toFloat()))
            }
            is CameraState.Fit -> {
                val builder = LatLngBounds.builder()
                cameraState.coordinates.forEach {
                    builder.include(LatLng(it.latitude, it.longitude))
                }
                map?.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        googleMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        googleMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        googleMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        googleMapView.onStop()
    }

    override fun onDestroyView() {
        googleMapView.onDestroy()
        super.onDestroyView()
    }
}
