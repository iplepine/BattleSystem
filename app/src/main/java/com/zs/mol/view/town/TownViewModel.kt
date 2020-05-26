package com.zs.mol.view.town

import android.util.Log
import androidx.lifecycle.ViewModel

class TownViewModel : ViewModel() {
    fun getDungeonText(): String {
        return "더언저언"
    }

    fun onClickDungeon() {
        Log.e("aaaa", "aaaaaaa")
    }
}