package com.zs.mol.model.db.monster

import com.zs.mol.model.stat.BaseStat
import com.zs.mol.model.unit.monster.MonsterStatus

object MonsterDB {
    private val statusDB = HashMap<Long, MonsterStatus>()
    private val baseStatDB = HashMap<Long, BaseStat>()

    init {

    }

    fun getStatus(id: Long) : MonsterStatus? {
        return statusDB[id]
    }

    fun getBaseStat(id: Long) : BaseStat? {
        return baseStatDB[id]
    }
}
