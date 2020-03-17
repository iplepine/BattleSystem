package com.zs.mol.view.base

import com.zs.mol.model.game.GameManager

open class MainFragment : BaseFragment() {
    override fun onStop() {
        super.onStop()
        context?.apply {
            GameManager.saveGame(applicationContext)
        }
    }
}