package com.zs.battlesystem.data.battle.unit.stat

data class Stat(
    var hp: Int = 10,
    var mp: Int = 10,
    var minAtk: Int = 1,
    var maxAtk: Int = 5,
    var def: Int = 1,
    var minMAtk: Int = 1,
    var maxMAtk: Int = 1,
    var mdef: Int = 1,
    var speed: Int = 1,
    var hit: Int = 1,
    var evade: Int = 1,
    var critical: Int = 1
)