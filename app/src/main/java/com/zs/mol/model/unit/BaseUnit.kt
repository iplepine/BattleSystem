package com.zs.mol.model.unit

import com.zs.mol.model.item.EquipItem
import com.zs.mol.model.manager.StatManager
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.skill.active.NormalAttack
import com.zs.mol.model.stat.SecondStat
import com.zs.mol.model.stat.Stat

open class BaseUnit(val owner: String = "enemy", val id: String) {
    var level = 1
    var exp = 0L
    var name = "Noname"
    var job = "Novice"

    var originalStat = Stat()
    var totalStat: Stat = calculateTotalStat()
    var currentStat: Stat = calculateTotalStat()

    var skills: ArrayList<UnitSkill> = ArrayList<UnitSkill>().apply {
        add(UnitSkill(NormalAttack))
    }

    var equipItems = HashMap<String, EquipItem>()

    var action = UnitAction.IDLE

    init {
        calculateTotalStat()
    }

    override fun toString(): String {
        return StringBuilder()
            .append("Lv. $level $name ")
            .append(totalStat)
            .toString()
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

    fun addSkill(id: Int) {

    }

    fun removeSkill(id: Int) {
        val removeSkills = skills.filter {
            it.skill.id == id
        }
        skills.removeAll(removeSkills)
    }
}