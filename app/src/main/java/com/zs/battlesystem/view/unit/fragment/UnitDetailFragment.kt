package com.zs.battlesystem.view.unit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.CHA
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.CON
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.DEX
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.INT
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.STR
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.WIS
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.HP
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.user.User
import com.zs.battlesystem.view.base.BaseFragment
import com.zs.battlesystem.view.unit.viewmodel.UnitDetailViewModel
import kotlinx.android.synthetic.main.fragment_unit_detail.*

class UnitDetailFragment : BaseFragment() {
    companion object {
        fun newInstance(unit: BaseUnit): UnitDetailFragment {
            return UnitDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("unit id", unit.id)
                }
            }
        }
    }

    private val viewModel = UnitDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unit_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        handleArguments()

        viewModel.unit.observe(viewLifecycleOwner, Observer { updateUnitInfo(it) })
    }

    private fun handleArguments() {
        arguments?.apply {
            getString("unit id")?.also { id ->
                viewModel.unit.value = User.units.find { unit -> unit.id == id }
            }
        }
    }

    private fun updateUnitInfo(unit: BaseUnit) {
        name.text = unit.name
        hpBarText.text = String.format("%d", unit.currentStat.secondStat.get(HP))
        str.text = String.format("힘 : %02d", unit.totalStat.baseStat.values[STR]?.toInt() ?: 0)
        dex.text = String.format("민첩 : %02d", unit.totalStat.baseStat.values[DEX]?.toInt() ?: 0)
        lnt.text = String.format("지능 : %02d", unit.totalStat.baseStat.values[INT]?.toInt() ?: 0)
        con.text = String.format("체질 : %02d", unit.totalStat.baseStat.values[CON]?.toInt() ?: 0)
        wis.text = String.format("정신 : %02d", unit.totalStat.baseStat.values[WIS]?.toInt() ?: 0)
        cha.text = String.format("매력 : %02d", unit.totalStat.baseStat.values[CHA]?.toInt() ?: 0)
    }
}
