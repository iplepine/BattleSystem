package com.zs.battlesystem.data.battle.unit.stat

data class BattleStat(
    var hp: Int = 10,
    var mp: Int = 10,
    var atk: Int = 1,
    var def: Int = 1,
    var matk: Int = 1,
    var mdef: Int = 1,
    var speed: Int = 1,
    var hit: Int = 1,
    var evade: Int = 1,
    var critical: Int = 1
)