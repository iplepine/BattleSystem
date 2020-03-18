package com.zs.mol.view.base

import com.zs.mol.model.game.GameEngine

open class MainFragment : BaseFragment() {
    override fun onStop() {
        super.onStop()
        context?.apply {
            GameEngine.saveGame(applicationContext)
        }
    }
}