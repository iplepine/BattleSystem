package com.zs.mol.view.dungeon

import androidx.lifecycle.MutableLiveData
import com.zs.lib.view.liverecyclerview.LiveViewModel
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.SimpleDungeon

class DungeonViewModel : LiveViewModel() {

    val dungeon = MutableLiveData<SimpleDungeon>()

    val currentPosition = MutableLiveData<Position>()
    val dungeonTitle = MutableLiveData<String>()

    val actionLimit = DefaultLiveData(10)

    val eyesight = 2

    fun enterDungeon() {
        dungeon.value = SimpleDungeon.create(10, 6)?.also {
            currentPosition.value = it.entrance.position
        }

        dungeonTitle.value = "이름 없는 던전"
    }

    fun changePosition(position: Position) {
        currentPosition.value = position
        actionLimit.value = (actionLimit.value - 1).coerceAtLeast(0)
    }
}