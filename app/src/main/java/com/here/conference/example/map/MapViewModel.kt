package com.here.conference.example.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _mapItems = MediatorLiveData<List<MapItem>>()
    private val _cameraState = MutableLiveData<CameraState>()

    private val searchRepository = SearchRepository()
    private val placeRepository = PlaceRepository()

    init {
        // Add sources to the _mapItems mediator
        _mapItems.addSource(searchRepository.searchItems) {
            updateItems()
        }
        _mapItems.addSource(placeRepository.placeItems) {
            updateItems()
        }
        _mapItems.addSource(MainInteractor.instance.mainState) {
            updateItems()
        }
    }

    fun getMapItems(): LiveData<List<MapItem>> = _mapItems

    fun getCameraState(): LiveData<CameraState> = _cameraState

    private fun updateItems() {
        val items = ArrayList<MapItem.Point>()
        val state = MainInteractor.instance.mainState.value!!

        if (state == MainInteractor.State.ALL || state == MainInteractor.State.SEARCH) {
            items.addAll(searchRepository.searchItems.value?.map {
                MapItem.Point.Marker.Search(it.id, it.position)
            }.orEmpty())
        }
        if (state == MainInteractor.State.ALL || state == MainInteractor.State.PLACES) {
            items.addAll(placeRepository.placeItems.value?.map {
                MapItem.Point.Marker.Place(it.id, it.position)
            }.orEmpty())
        }
        _mapItems.postValue(items.toList())

        // This could be in a separate method/logic for this example just center or fit according to the items
        _cameraState.postValue(when (items.size) {
            0 -> CameraState.None
            1 -> CameraState.Center(items.first().coordinate, 10)
            else -> CameraState.Fit(items.map { it.coordinate })
        })
    }
}
