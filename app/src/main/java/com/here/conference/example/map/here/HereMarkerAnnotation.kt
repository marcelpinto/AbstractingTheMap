package com.here.conference.example.map.here

import android.content.Context
import android.graphics.Bitmap
import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.Image
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapMarker
import nz.co.trademe.mapme.LatLng
import nz.co.trademe.mapme.annotations.MarkerAnnotation

class HereMarkerAnnotation(latLng: LatLng,
                           title: String?,
                           icon: Bitmap? = null) : MarkerAnnotation(latLng, title, icon) {

    private val marker = MapMarker()

    override fun addToMap(map: Any, context: Context) {
        val hereMap = map as Map
        hereMap.addMapObject(marker)
    }

    override fun annotatesObject(nativeObject: Any): Boolean = marker == nativeObject

    override fun onUpdateAlpha(alpha: Float) {
        // Not supported
    }

    override fun onUpdateIcon(icon: Bitmap?) {
        marker.icon = Image().apply {
            bitmap = icon
        }
    }

    override fun onUpdatePosition(position: LatLng) {
        marker.coordinate = GeoCoordinate(position.latitude, position.longitude)
    }

    override fun onUpdateTitle(title: String?) {
        marker.title = title
    }

    override fun onUpdateZIndex(index: Float) {
        marker.zIndex = index.toInt()
    }

    override fun removeFromMap(map: Any, context: Context) {
        val hereMap = map as Map
        hereMap.removeMapObject(marker)
    }
}
