package com.zs.mol.view.town.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zs.mol.R
import com.zs.mol.databinding.FragmentTownBinding
import com.zs.mol.view.base.MainTabFragment
import com.zs.mol.view.town.TownViewModel

class TownTabFragment : MainTabFragment() {
    lateinit var binding: FragmentTownBinding
    val viewModel: TownViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TownViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTownBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        binding.viewModel = viewModel
        //dungeon.setOnClickListener { onClickDungeon() }
    }

    private fun onClickDungeon() {
        // TODO dungeon Id 세팅 제대로 해야 함
        findNavController().navigate(
            R.id.action_town_to_dungeon,
            bundleOf("dungeonId" to "0000")
        )
    }
}