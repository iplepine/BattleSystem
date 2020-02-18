package com.zs.mol.model.battle

object BattleState {
    const val NONE = 0
    const val INPUT = 1
    const val ACTION = 2

    object Result {
        const val WIN = 0
        const val LOSE = -1
        const val DRAW = 1
    }
}