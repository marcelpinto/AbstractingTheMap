package com.here.conference.example.map

import android.arch.lifecycle.MutableLiveData

class MainInteractor private constructor() {

    private object Holder {
        val instance = MainInteractor()
    }

    companion object {
        /**
         * Evil Singleton, please use DI instead
         */
        val instance: MainInteractor by lazy { Holder.instance }
    }

    val mainState = MutableLiveData<State>().apply {
        value = State.ALL
    }

    enum class State {
        ALL,
        PLACES,
        SEARCH
    }
}
