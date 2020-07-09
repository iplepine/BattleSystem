package com.zs.mol.view.dungeon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
import com.zs.mol.databinding.FragmentDungeonBinding
import com.zs.mol.view.base.MainTabFragment
import kotlinx.android.synthetic.main.fragment_dungeon.*

class DungeonTabFragment : MainTabFragment() {
    lateinit var binding: FragmentDungeonBinding
    val viewModel: DungeonViewModel by lazy {
        ViewModelProvider(this).get(DungeonViewModel::class.java).apply {
            binding.viewModel = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDungeonBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.dungeonTabComponent().create().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
    }

    private fun changeDungeon() {
        recyclerView.adapter = SelectionAdapter(viewLifecycleOwner)
    }
}