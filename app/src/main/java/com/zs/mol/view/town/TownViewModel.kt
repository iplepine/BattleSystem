package com.zs.mol.view.town

import androidx.lifecycle.ViewModel
import com.zs.mol.model.common.Logger

class TownViewModel : ViewModel() {
    fun getDungeonText(): String {
        return "더언저언"
    }

    fun onClickDungeon() {
        Logger.e("온클릭던전")
    }
}