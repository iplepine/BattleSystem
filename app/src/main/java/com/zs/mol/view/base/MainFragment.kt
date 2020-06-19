package com.zs.mol.view.base

import com.zs.mol.model.game.GameEngine
import javax.inject.Inject

open class MainFragment : BaseFragment() {

    @Inject
    lateinit var gameEngine: GameEngine

    override fun onStop() {
        super.onStop()
        context?.apply {
            gameEngine.saveGame(applicationContext)
        }
    }
}