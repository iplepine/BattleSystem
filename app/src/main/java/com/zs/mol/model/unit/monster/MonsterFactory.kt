package com.zs.mol.model.unit.monster

import com.zs.mol.model.db.monster.MonsterDB
import com.zs.mol.model.stat.StatFactory

object MonsterFactory {
    fun createMonster(id: Long, level: Int): Monster? {
        val baseStat = MonsterDB.getBaseStat(id) ?: StatFactory.randomStat().baseStat
        val monsterStatus = MonsterDB.getStatus(id) ?: MonsterStatus().apply {
            this.name = "몬스터"
        }

        return Monster().apply {
            unitStatus = monsterStatus
            originalStat.baseStat = baseStat

            for (i in 0 until level) {
                levelUp()
            }
        }
    }
}