package com.zs.mol.view.base

import com.zs.mol.model.GameManager

open class MainFragment : BaseFragment() {
    override fun onStop() {
        super.onStop()
        context?.apply {
            GameManager.saveGame(this)
        }
    }
}