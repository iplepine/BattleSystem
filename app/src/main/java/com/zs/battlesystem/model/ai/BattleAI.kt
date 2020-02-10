package com.zs.battlesystem.model.ai

import com.zs.battlesystem.model.battle.Battle

class BattleAI(val battle: Battle) {
    fun onTurn() {
        battle.checkFinish()
    }
}