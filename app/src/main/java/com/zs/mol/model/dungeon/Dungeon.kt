package com.zs.mol.model.dungeon

import androidx.lifecycle.MutableLiveData

class Dungeon(val startPlace: DungeonPlace) {
    var currentPlace = MutableLiveData<DungeonPlace>().apply {
        value = startPlace
    }

    var direction = MutableLiveData<DungeonPlace.Direction>().apply {
        DungeonPlace.Direction.NONE
    }

    var currentEvent = MutableLiveData <DungeonEvent<String>>()

    val map = startPlace

    fun onEnter() {
        currentPlace.value = startPlace
    }
}