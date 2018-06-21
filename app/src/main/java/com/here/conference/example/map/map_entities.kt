package com.here.conference.example.map

interface GeoCoordinate {
    val latitude: Double
    val longitude: Double

    data class Impl(override val latitude: Double, override val longitude: Double) : GeoCoordinate
}

sealed class MapItem {
    abstract val id: String

    sealed class Path : MapItem() {
        data class Route(override val id: String, val path: List<Point>) : Path()
    }

    sealed class Point : MapItem(), GeoCoordinate {
        abstract val coordinate: GeoCoordinate

        sealed class Marker(override val id: String, override val coordinate: GeoCoordinate) : Point(), GeoCoordinate by coordinate {

            data class Search(override val id: String, override val coordinate: GeoCoordinate) : Marker(id, coordinate)

            data class Place(override val id: String, override val coordinate: GeoCoordinate) : Marker(id, coordinate)
        }
    }
}

sealed class CameraState {
    object None : CameraState()
    data class Center(val center: GeoCoordinate, val zoomLevel: Int) : CameraState()
    data class Fit(val coordinates: List<GeoCoordinate>) : CameraState()
}
