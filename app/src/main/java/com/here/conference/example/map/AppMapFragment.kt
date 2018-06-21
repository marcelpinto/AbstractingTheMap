package com.here.conference.example.map


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AppMapFragment<MapType> : Fragment(), Observer<CameraState> {

    private lateinit var adapter: MapsAdapter<MapType>

    private lateinit var viewModel: MapViewModel

    protected var map: MapType? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_app_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        adapter = createMapAdapter()
        initMap {
            map = it
            adapter.attach(view, it)
            viewModel.getMapItems().observe(this, adapter)
            viewModel.getCameraState().observe(this, this)
        }
    }

    abstract fun createMapAdapter(): MapsAdapter<MapType>
    abstract fun initMap(onInit: (MapType) -> Unit)
}
