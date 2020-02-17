package com.zs.battlesystem.view.unit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.CHA
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.CON
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.DEX
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.INT
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.STR
import com.zs.battlesystem.model.battle.stat.BaseStat.Companion.WIS
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.ATK
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.CRI
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.DEF
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.EVADE
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.HIT
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.HP
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.MATK
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.MDEF
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.SPEED
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
                    putString("unitId", unit.id)
                }
            }
        }
    }

    private val viewModel = UnitDetailViewModel()

    private val baseStatSequence = arrayOf(STR, DEX, INT, CON, WIS, CHA)
    private val secondStatSequence = arrayOf(ATK, MATK, DEF, MDEF, HIT, EVADE, SPEED, CRI)

    private val baseStatViews = HashMap<String, TextView>()
    private val secondStatViews = HashMap<String, TextView>()

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
        initStatViews(baseStatSequence, baseStatViews, baseStatLayout)
        initStatViews(secondStatSequence, secondStatViews, secondStatLayout)

        viewModel.unit.observe(viewLifecycleOwner, Observer { updateUnitInfo(it) })
    }

    private fun handleArguments() {
        arguments?.apply {
            getString("unitId")?.also { id ->
                viewModel.unit.value = User.units.find { unit -> unit.id == id }
            }
        }
    }

    private fun initStatViews(
        stats: Array<String>,
        views: HashMap<String, TextView>,
        layout: ViewGroup
    ) {
        if (context == null) {
            return
        }

        if (views.isEmpty()) {
            layout.apply {
                stats.forEach {
                    val title = TextView(context)
                    title.text = it
                    addView(title)

                    val value = TextView(context)
                    addView(value)

                    views[it] = value
                }
            }
        }
    }

    private fun updateUnitInfo(unit: BaseUnit) {
        name.text = unit.name
        hpBarText.text = String.format(
            "HP : %d/%d",
            unit.currentStat.secondStat.get(HP)?.toInt(),
            unit.totalStat.secondStat.get(HP)?.toInt()
        )

        baseStatSequence.forEach {
            baseStatViews[it]?.text = unit.currentStat.baseStat.get(it).toInt().toString()
        }

        secondStatSequence.forEach {
            secondStatViews[it]?.text = unit.currentStat.secondStat.get(it).toInt().toString()
        }
    }
}