package com.zs.mol.view.dungeon

import androidx.lifecycle.MutableLiveData
import com.zs.lib.view.liverecyclerview.LiveViewModel
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.dungeon.SimpleDungeon
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker

class DungeonViewModel : LiveViewModel() {

    val dungeon = MutableLiveData<SimpleDungeon>()

    val currentPlace = MutableLiveData<TileAndGraphBasedMaker.TiledPlace>()
    val dungeonTitle = MutableLiveData<String>()

    val actionLimit = DefaultLiveData(10)

    fun enterDungeon() {
        dungeon.value = SimpleDungeon.create(10, 6)?.also {
            currentPlace.value = it.entrance
        }

        dungeonTitle.value = "이름 없는 던전"
    }

    fun changePlace(place: TileAndGraphBasedMaker.TiledPlace?) {
        currentPlace.value = place
    }
}