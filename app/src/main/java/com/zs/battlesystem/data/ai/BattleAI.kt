package com.zs.battlesystem.data.ai

import com.zs.battlesystem.data.battle.Battle

class BattleAI(val battle: Battle) {
    fun onTurn() {
        battle.isFinish()
    }
}