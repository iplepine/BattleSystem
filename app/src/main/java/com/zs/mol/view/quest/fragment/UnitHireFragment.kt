package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.zs.mol.R
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.detail.reward.UnitReward
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.view.unit.fragment.UnitDetailFragment

class UnitHireFragment : DialogFragment(), QuestView {
    var quest: Quest? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hire, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
    }

    override fun onClickAccept() {
    }

    override fun onClickReject() {
    }

    override fun onClickClose() {
    }

    override fun onClickDetail() {
        quest?.apply {
            val unitReward = rewards[0] as? UnitReward ?: return
            showUnitDetailView(unitReward.unit)
        }
    }

    private fun showUnitDetailView(unit: BattleUnit) {
        findNavController().navigate(
            R.id.action_hireQuestFragment_to_unitDetailFragment,
            bundleOf(UnitDetailFragment.KEY_UNIT_ID to unit.id)
        )
    }
}