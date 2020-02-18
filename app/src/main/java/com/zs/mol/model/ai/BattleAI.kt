package com.zs.mol.model.ai

import com.zs.mol.model.battle.Battle

class BattleAI(val battle: Battle) {
    fun onTurn() {
        battle.checkFinish()
    }
}