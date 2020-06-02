package com.zs.mol.model.dungeon

import androidx.lifecycle.MutableLiveData

class Dungeon(val startPlace: DungeonPlace) {
    var currentPlace = MutableLiveData<DungeonPlace>(startPlace)

    val direction = MutableLiveData<DungeonPlace.Direction>()
    val currentEvent = MutableLiveData<DungeonEvent>()

    val map = startPlace

    fun onEnter() {
        currentPlace.value = startPlace
    }
}