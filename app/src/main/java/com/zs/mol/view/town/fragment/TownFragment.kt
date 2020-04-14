package com.zs.mol.view.town.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.mol.R
import com.zs.mol.view.base.MainFragment
import kotlinx.android.synthetic.main.fragment_town.*

class TownFragment : MainFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_town, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        dungeon.setOnClickListener { onClickDungeon() }
    }

    private fun onClickDungeon() {

    }
}