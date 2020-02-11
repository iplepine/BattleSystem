package com.zs.battlesystem.model.battle.unit

import com.zs.battlesystem.model.battle.skill.Skill
import com.zs.battlesystem.model.battle.skill.active.NormalAttack
import com.zs.battlesystem.model.battle.stat.SecondStat
import com.zs.battlesystem.model.battle.stat.Stat
import com.zs.battlesystem.model.item.EquipItem
import com.zs.battlesystem.model.manager.StatManager

open class BaseUnit(name: String, stat: Stat = Stat(), currentStat: Stat? = null) {
    var level = 1
    var exp = 0L
    var name = name
    var job = "Job class"

    var action: UnitAction = UnitAction.IDLE

    val originalStat = stat
    val totalStat: Stat = calculateTotalStat()
    val currentStat: Stat = currentStat ?: calculateTotalStat()

    var skills: ArrayList<Skill> = ArrayList<Skill>().apply { add(NormalAttack()) }
    var equipItems = HashMap<String, EquipItem>()

    init {
        calculateTotalStat()
    }

    fun levelUp() {
        level++
        addLevelUpStats()
        calculateTotalStat()
    }

    // 기본 증가값 + 현재 총 기본 스텟에 따른 증가값
    private fun addLevelUpStats() {
        originalStat.secondStat.apply {
            plus(StatManager.defaultLevelUpFactor)
            plus(SecondStat.createFromBaseStat(totalStat.baseStat))
        }
    }

    fun hasEquiped(type: String): Boolean {
        return equipItems.contains(type)
    }

    fun equip(item: EquipItem) {
        equipItems[item.equipType] = item
        calculateTotalStat()
    }

    fun calculateTotalStat(): Stat {
        val ret = originalStat.deepCopy()

        val flatStat = Stat()
        val percentStat = Stat.createInitializedStat(1.0, 1.0)

        equipItems?.forEach {
            flatStat.add(it.value.flatStat)
            percentStat.add(it.value.percentStat)
        }

        ret.baseStat.plus(flatStat.baseStat)
        ret.secondStat.plus(flatStat.secondStat)
        ret.baseStat.times(percentStat.baseStat)
        ret.secondStat.times(percentStat.secondStat)

        return ret
    }

    override fun toString(): String {
        return StringBuilder()
            .append("Lv. $level $name ")
            .append(totalStat)
            .toString()
    }
}