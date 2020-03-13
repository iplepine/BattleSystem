package com.zs.mol.model.unit.monster

import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager

class Monster : BattleUnit(UserManager.ReservedUserId.ENEMY) {
    var monsterStatus = MonsterStatus()
}