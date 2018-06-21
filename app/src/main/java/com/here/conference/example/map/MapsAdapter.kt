package com.here.conference.example.map

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.util.DiffUtil
import nz.co.trademe.mapme.LatLng
import nz.co.trademe.mapme.MapMeAdapter
import nz.co.trademe.mapme.annotations.AnnotationFactory
import nz.co.trademe.mapme.annotations.MapAnnotation
import nz.co.trademe.mapme.annotations.MarkerAnnotation

open class MapsAdapter<MapType>(context: Context, factory: AnnotationFactory<MapType>)
    : MapMeAdapter<MapType>(context, factory), Observer<List<MapItem>> {

    private val placeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_my_place)
    private val searchIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_my_search)

    private var markers: List<MapItem.Point> = emptyList()

    override fun onChanged(items: List<MapItem>?) {
        // Since we don't support Path yet just cast it
        val newMarkers = items.orEmpty().map { it as MapItem.Point }
        val diff = DiffUtil.calculateDiff(MarkersDiffCallback(markers, newMarkers))
        markers = newMarkers
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = markers.size

    override fun onCreateAnnotation(factory: AnnotationFactory<MapType>, position: Int, annotationType: Int): MapAnnotation {
        val item = this.markers[position]
        return factory.createMarker(item.toLatLng(), getIcon(item), item.id)
    }

    override fun onBindAnnotation(annotation: MapAnnotation, position: Int, payload: Any?) {
        if (annotation is MarkerAnnotation) {
            val item = this.markers[position]
            annotation.latLng = item.toLatLng()
            annotation.icon = getIcon(item)
        }
    }

    private fun getIcon(item: MapItem): Bitmap {
        return when (item) {
            is MapItem.Path.Route -> TODO()
            is MapItem.Point.Marker.Search -> searchIcon
            is MapItem.Point.Marker.Place -> placeIcon
        }
    }
}

private fun MapItem.Point.toLatLng(): LatLng = LatLng(latitude, longitude)
