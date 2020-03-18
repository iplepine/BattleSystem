package com.zs.mol.model.unit.action

import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager

object UnitActionManager {

    fun updateTime(time: Long) {
        UserManager.getUnits().forEach {
            updateAction(it, time)
        }
    }

    private fun updateAction(unit: BattleUnit, time: Long) {
        unit.status.action?.apply {
            if (isRunning()) {
                updateTime(time)
            } else {
                //this.time = (Random.nextLong(10) + 5) * 1000
                this.time = 3000
            }
        }
    }
}