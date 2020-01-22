package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.skill.active.NormalAttack
import com.zs.battlesystem.data.battle.skill.buff.Buff
import com.zs.battlesystem.data.battle.skill.debuff.Debuff
import com.zs.battlesystem.data.battle.unit.stat.BaseStat
import com.zs.battlesystem.data.battle.unit.stat.BattleStat

open class BaseUnit {
    var level = 0
    var exp = 0L
    var name = "이름"
    var job = "직업"

    var baseStat: BaseStat = BaseStat()
    var battleStat = BattleStat()

    var skills: List<Skill> = ArrayList<Skill>().apply { add(NormalAttack) }
    var buffs = ArrayList<Buff>()
    var debuffs = ArrayList<Debuff>()

    fun levelUp() {
        level++
    }
}