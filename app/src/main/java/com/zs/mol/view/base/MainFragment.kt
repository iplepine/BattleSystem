package com.zs.mol.view.base

import android.content.Context
import com.zs.mol.MainActivity
import com.zs.mol.model.game.GameEngine
import javax.inject.Inject

open class MainFragment : BaseFragment() {

    @Inject
    lateinit var gameEngine: GameEngine

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.inject(this)
    }

    override fun onStop() {
        super.onStop()
        context?.apply {
            gameEngine.saveGame(applicationContext)
        }
    }
}