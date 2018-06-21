package com.here.conference.example.map

import android.arch.lifecycle.MutableLiveData

data class SearchItem(val id: String, val name: String, val position: GeoCoordinate)

class SearchRepository {

    val searchItems = MutableLiveData<List<SearchItem>>().apply {
        val item = SearchItem("1", "Sydney", GeoCoordinate.Impl(-34.0, 151.0))
        value = listOf(item)
    }
}

data class PlaceItem(val id: String, val name: String, val position: GeoCoordinate)

class PlaceRepository {

    val placeItems = MutableLiveData<List<PlaceItem>>().apply {
        val item = PlaceItem("1", "Pylon Lookout", GeoCoordinate.Impl(-33.8553416, 151.2101064))
        value = listOf(item)
    }
}
