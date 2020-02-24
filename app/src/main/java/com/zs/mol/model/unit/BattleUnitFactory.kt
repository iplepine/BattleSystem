package com.zs.mol.model.unit

import com.zs.mol.model.const.ManNamePool
import com.zs.mol.model.const.WomanNamePool
import com.zs.mol.model.stat.StatFactory
import com.zs.mol.model.user.UserManager

object BattleUnitFactory {

    fun createTestUnit(owner: String = "enemy"): BattleUnit {
        val stat = StatFactory.randomStat()
        return BattleUnit(owner)
    }

    fun createMyUnit(name: String = randomName()): BattleUnit {
        return BattleUnit(UserManager.getUserId()).apply {
            this.name = name
        }
    }

    fun createEnemy(name: String = randomName()): BattleUnit {
        return BattleUnit(UserManager.getEnemyId()).apply {
            this.name = name
        }
    }

    private fun randomName(): String {
        return if (Math.random() < 0.5) {
            // man
            val index = (Math.random() * ManNamePool.NAME_LIST.size).toInt()
            ManNamePool.NAME_LIST[index]
        } else {
            // woman
            val index = (Math.random() * WomanNamePool.NAME_LIST.size).toInt()
            WomanNamePool.NAME_LIST[index]
        }
    }
}
