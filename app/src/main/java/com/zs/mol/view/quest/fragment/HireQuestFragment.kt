package com.zs.mol.view.quest.fragment

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zs.mol.R
import com.zs.mol.model.quest.detail.reward.UnitReward
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.view.unit.fragment.UnitDetailFragment

class HireQuestFragment : NewQuestFragment() {

    override fun init() {
        super.init()

        if (quest == null) {
            dismiss()
        }
    }

    override fun onClickDetail() {
        quest?.apply {
            val unitReward = rewards[0] as? UnitReward
            if (unitReward == null) {
                dismiss()
            } else {
                showUnitDetailView(unitReward.unit)
            }
        }
    }

    private fun showUnitDetailView(unit: BattleUnit) {
        findNavController().navigate(
            R.id.action_hireQuestFragment_to_unitDetailFragment,
            bundleOf(UnitDetailFragment.KEY_UNIT_ID to unit.id)
        )
    }
}