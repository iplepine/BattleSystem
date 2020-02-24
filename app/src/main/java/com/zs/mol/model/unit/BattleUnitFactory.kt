package com.zs.mol.model.unit

import com.zs.mol.model.stat.StatFactory
import com.zs.mol.model.user.UserManager

object BattleUnitFactory {

    fun createTestUnit(owner: String = "enemy"): BattleUnit {
        val stat = StatFactory.randomStat()
        return BattleUnit(owner)
    }

    fun createMyUnit(name: String): BattleUnit {
        return BattleUnit(UserManager.getUserId()).apply {
            this.name = name
        }
    }

    fun createEnemy(name : String): BattleUnit {
        return BattleUnit(UserManager.getEnemyId()).apply {
            this.name = name
        }
    }
}
