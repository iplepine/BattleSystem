package com.zs.mol.view.dungeon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.R
import com.zs.mol.view.base.MainTabFragment
import kotlinx.android.synthetic.main.fragment_dungeon.*

class DungeonTabFragment : MainTabFragment() {
    var viewModel: DungeonViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dungeon, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DungeonViewModel::class.java)
    }

    private fun changeDungeon() {
        recyclerView.adapter = SelectionAdapter(viewLifecycleOwner)
    }
}