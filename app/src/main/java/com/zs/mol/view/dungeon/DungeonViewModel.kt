package com.zs.mol.view.dungeon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.dungeon.Dungeon
import com.zs.mol.model.dungeon.DungeonEvent
import com.zs.mol.model.dungeon.DungeonPlace

class DungeonViewModel : ViewModel() {
    lateinit var dungeon: Dungeon

    var currentPlace = MutableLiveData<DungeonPlace>()

    val direction = MutableLiveData<DungeonPlace.Direction>()
    val currentEvent = MutableLiveData<DungeonEvent>()


}