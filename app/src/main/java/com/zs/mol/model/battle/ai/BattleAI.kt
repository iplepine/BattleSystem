package com.zs.mol.model.battle.ai

import com.zs.mol.model.battle.Battle

class BattleAI(val battle: Battle) {
    fun onTurn() {
        battle.checkFinish()
    }
}