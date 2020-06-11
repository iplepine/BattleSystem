package com.zs.mol.view.dungeon

import androidx.lifecycle.MutableLiveData
import com.zs.lib.view.liverecyclerview.LiveViewModel
import com.zs.mol.model.dungeon.DungeonEvent
import com.zs.mol.model.dungeon.DungeonPlace

class DungeonViewModel : LiveViewModel() {

    var currentPlace = MutableLiveData<DungeonPlace>()

    val direction = MutableLiveData<DungeonPlace.Direction>()
    val currentEvent = MutableLiveData<DungeonEvent>()

    var adapter: SelectionAdapter? = null


}