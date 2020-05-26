package com.zs.mol.view.dungeon

import androidx.lifecycle.ViewModel
import com.zs.mol.model.dungeon_.Dungeon

class DungeonViewModel : ViewModel() {
    lateinit var dungeon : Dungeon

    fun onClickSelection(index: Int) {
        //dungeon.currentEvent.choice(index)
    }
}