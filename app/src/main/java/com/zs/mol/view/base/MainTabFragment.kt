package com.zs.mol.view.base

import android.content.Context
import com.zs.mol.MainActivity
import com.zs.mol.model.game.GameEngine
import javax.inject.Inject

open class MainTabFragment : BaseFragment() {

    @Inject
    lateinit var gameEngine: GameEngine

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).gameComponent.inject(this)
    }

    override fun onStop() {
        super.onStop()
        gameEngine.saveGame()
    }
}