package com.here.conference.example.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.here.conference.example.map.MainInteractor.State.*
import com.here.conference.example.map.google.GoogleMapFragment
import com.here.conference.example.map.here.HereMapFragment
import com.here.conference.example.map.mapbox.MapboxMapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggleAction.setOnClickListener {
            val state = MainInteractor.instance.mainState.value!!
            MainInteractor.instance.mainState.value = when (state) {
                ALL -> PLACES
                PLACES -> SEARCH
                SEARCH -> ALL
            }
        }

        navigation.setOnNavigationItemSelectedListener {
            setMapFragment(when (it.itemId) {
                R.id.navigation_google -> GoogleMapFragment()
                R.id.navigation_here -> HereMapFragment()
                R.id.navigation_mapbox -> MapboxMapFragment()
                else -> throw NotImplementedError()
            })
            true
        }
        navigation.selectedItemId = R.id.navigation_google
    }

    private fun setMapFragment(mapFragment: AppMapFragment<*>) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.mapContainer, mapFragment)
                .commit()
    }
}
