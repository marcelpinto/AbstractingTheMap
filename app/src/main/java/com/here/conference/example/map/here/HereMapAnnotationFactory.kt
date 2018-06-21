package com.here.conference.example.map.here

import android.graphics.Bitmap
import com.here.android.mpa.mapping.Map
import nz.co.trademe.mapme.LatLng
import nz.co.trademe.mapme.annotations.AnnotationFactory
import nz.co.trademe.mapme.annotations.MarkerAnnotation

class HereMapAnnotationFactory : AnnotationFactory<Map> {
    override fun clear(map: Map) {
    }

    override fun createMarker(latLng: LatLng, icon: Bitmap?, title: String?): MarkerAnnotation {
        return HereMarkerAnnotation(latLng, title, icon)
    }

    override fun setOnInfoWindowClickListener(map: Map, onClick: (marker: Any) -> Boolean) {
        // TODO
    }

    override fun setOnMarkerClickListener(map: Map, onClick: (marker: Any) -> Boolean) {
        // TODO
    }
}
