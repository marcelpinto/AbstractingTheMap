package com.here.conference.example.map.here


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.here.android.mpa.common.*
import com.here.android.mpa.mapping.Map
import com.here.conference.example.map.AppMapFragment
import com.here.conference.example.map.CameraState
import com.here.conference.example.map.MapsAdapter
import com.here.conference.example.map.R
import kotlinx.android.synthetic.main.fragment_here_map.*

class HereMapFragment : AppMapFragment<Map>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_here_map, container, false)
    }

    override fun createMapAdapter(): MapsAdapter<Map> = MapsAdapter(requireContext(), HereMapAnnotationFactory())

    override fun initMap(onInit: (Map) -> Unit) {
        MapEngine.getInstance().init(ApplicationContext(context)) { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                val map = Map()
                hereMapView.map = map
                onInit(map)
            }
        }
    }

    override fun onChanged(cameraState: CameraState?) {
        when (cameraState) {

            is CameraState.Center -> {
                val center = GeoCoordinate(cameraState.center.latitude, cameraState.center.longitude)
                map?.setCenter(center, Map.Animation.NONE, cameraState.zoomLevel.toDouble(), 0f, 0f)
            }
            is CameraState.Fit -> {
                val builder = ArrayList<GeoCoordinate>()
                cameraState.coordinates.forEach {
                    builder.add(GeoCoordinate(it.latitude, it.longitude))
                }
                val area = GeoBoundingBox.getBoundingBoxContainingGeoCoordinates(builder)
                map?.setCenter(area.center, Map.Animation.BOW, 9.0, 0f, 0f)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hereMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        hereMapView.onPause()
    }
}
