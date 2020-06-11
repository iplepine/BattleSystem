package com.zs.mol.model.dungeon

import android.text.Selection

class DungeonEvent {
    var title = ""
    var description = ""

    var isEnabled = true

    var selections = ArrayList<Selection>()

    open fun onSuccess() {
    }

    open fun onFailed() {
    }

    fun finish() {
        isEnabled = false
    }
}