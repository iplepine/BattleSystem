package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.item.EquipItem
import com.zs.battlesystem.data.battle.item.Item
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.skill.active.NormalAttack
import com.zs.battlesystem.data.battle.skill.buff.Buff
import com.zs.battlesystem.data.battle.skill.debuff.Debuff
import com.zs.battlesystem.data.battle.stat.SecondStat
import com.zs.battlesystem.data.battle.stat.Stat
import com.zs.battlesystem.data.manager.StatManager

open class BaseUnit {
    var level = 0
    var exp = 0L
    var name = "이름"
    var job = "직업"

    var originalStat = Stat()
    var totalStat = Stat()

    var skills: ArrayList<Skill> = ArrayList<Skill>().apply { add(NormalAttack()) }
    var buffs = ArrayList<Buff>()
    var debuffs = ArrayList<Debuff>()

    var equipItems = HashMap<String, EquipItem>()

    fun levelUp() {
        level++
        addLevelUpStats()
        updateTotalStats()
    }

    // 기본 증가값 + 현재 총 기본 스텟에 따른 증가값
    private fun addLevelUpStats() {
        originalStat.secondStat.apply {
            add(StatManager.defaultLevelUpFactor)
            add(SecondStat.createFromBaseStat(totalStat.baseStat))
        }
    }

    private fun updateTotalStats() {
        val flatStat = Stat()
        val percentStat = Stat.createInitializedStat(1.0, 1.0)

        equipItems.forEach {
            flatStat.add(it.value.flatStat)
            percentStat.add(it.value.percentStat)
        }

        totalStat.add(flatStat)
        totalStat.multiple(percentStat)
    }

    fun hasEquiped(type: String): Boolean {
        return equipItems.contains(type)
    }

    fun equip(item: Item) {
    }

    fun updateStat() {
        totalStat = originalStat.copy()

        equipItems.forEach {
            totalStat.add(it.value.flatStat)
            totalStat
        }
    }
}