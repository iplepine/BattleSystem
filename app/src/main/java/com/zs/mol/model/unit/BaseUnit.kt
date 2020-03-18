package com.zs.mol.model.unit

import com.zs.mol.model.item.EquipItem
import com.zs.mol.model.skill.SkillManager
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.skill.active.NormalAttack
import com.zs.mol.model.stat.SecondStat
import com.zs.mol.model.stat.Stat
import com.zs.mol.model.stat.StatManager

open class BaseUnit(var owner: String = "enemy", val id: String) {
    var status = UnitStatus()
    var hiringStatus = HiringStatus()

    var originalStat = Stat()
        set(value) {
            field = value
            updateStat()
        }

    var totalStat: Stat = Stat()

    var skills: ArrayList<UnitSkill> = ArrayList<UnitSkill>().apply {
        add(UnitSkill(NormalAttack.id))
    }

    var equipItems = HashMap<String, EquipItem>()

    var reports = ArrayList<Report>()

    override fun toString(): String {
        return StringBuilder()
            .appendln(status.toLevelName())
            .append(totalStat)
            .toString()
    }

    fun levelUp() {
        status.level++
        addLevelUpStats()
        updateStat()
    }

    fun getLevel(): Int {
        return status.level
    }

    fun getName(): String {
        return status.name
    }

    fun setName(name: String) {
        status.name = name
    }

    // 기본 증가값 + 현재 총 기본 스텟에 따른 증가값
    private fun addLevelUpStats() {
        originalStat.secondStat.apply {
            plus(StatManager.defaultLevelUpFactor)
            plus(SecondStat.createFromBaseStat(calculateTotalStat().baseStat))
        }
    }

    fun hasEquiped(type: String): Boolean {
        return equipItems.contains(type)
    }

    fun equip(item: EquipItem) {
        equipItems[item.equipType] = item
        calculateTotalStat()
    }

    open fun updateStat() {
        totalStat = calculateTotalStat()
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
        SkillManager.getSkill(id)?.also {
            skills.add(UnitSkill(id))
        }
    }

    fun removeSkill(id: Int) {
        val removeSkills = skills.filter {
            it.id == id
        }
        skills.removeAll(removeSkills)
    }
}