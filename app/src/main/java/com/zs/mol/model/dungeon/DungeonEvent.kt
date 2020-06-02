package com.zs.mol.model.dungeon

class DungeonEvent {
    var title = ""
    var description = ""

    var isEnabled = true

    open fun onSuccess() {
    }

    open fun onFailed() {
    }

    fun finish() {
        isEnabled = false
    }
}