package com.zs.mol.model.unit.monster

import com.zs.mol.model.consts.ReservedUserId
import com.zs.mol.model.unit.BattleUnit

class Monster : BattleUnit(ReservedUserId.ENEMY) {
    var monsterStatus = MonsterStatus()
}