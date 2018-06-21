package com.here.conference.example.map.mapbox


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.here.conference.example.map.AppMapFragment
import com.here.conference.example.map.CameraState
import com.here.conference.example.map.MapsAdapter
import com.here.conference.example.map.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.fragment_mapbox_map.*
import nz.co.trademe.mapme.mapbox.MapboxAnnotationFactory

class MapboxMapFragment : AppMapFragment<MapboxMap>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapbox_map, container, false)
    }

    override fun createMapAdapter(): MapsAdapter<MapboxMap> = MapsAdapter(requireContext(), MapboxAnnotationFactory())

    override fun initMap(onInit: (MapboxMap) -> Unit) {
        Mapbox.getInstance(requireContext(), "{YOUR_MAPBOX_KEY")
        mapboxMapView.getMapAsync(onInit)
    }

    override fun onChanged(cameraState: CameraState?) {
        when (cameraState) {

            is CameraState.Center -> {
                val center = LatLng(cameraState.center.latitude, cameraState.center.longitude)
                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(center, cameraState.zoomLevel.toDouble()))
            }
            is CameraState.Fit -> {
                val builder = LatLngBounds.Builder()
                cameraState.coordinates.forEach {
                    builder.include(LatLng(it.latitude, it.longitude))
                }
                map?.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapboxMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapboxMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapboxMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapboxMapView.onStop()
    }

    override fun onDestroyView() {
        mapboxMapView.onDestroy()
        super.onDestroyView()
    }
}
