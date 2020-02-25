package com.zs.mol.model.unit

import com.zs.mol.model.const.ManNamePool
import com.zs.mol.model.const.WomanNamePool
import com.zs.mol.model.stat.StatFactory
import com.zs.mol.model.user.UserManager

object BattleUnitFactory {

    fun createTestUnit(owner: String = "enemy"): BattleUnit {
        return BattleUnit(owner).apply {
            originalStat = StatFactory.randomStat()
            updateStat()
        }
    }

    fun createMyUnit(name: String = randomName()): BattleUnit {
        return BattleUnit(UserManager.getUserId()).apply {
            this.name = name
            originalStat = StatFactory.randomStat()
            updateStat()
        }
    }

    fun createEnemy(name: String = randomName()): BattleUnit {
        return BattleUnit(UserManager.getEnemyId()).apply {
            this.name = name
            originalStat = StatFactory.randomStat()
            updateStat()
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
