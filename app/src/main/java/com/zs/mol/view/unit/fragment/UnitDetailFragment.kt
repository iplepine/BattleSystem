package com.zs.mol.view.unit.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.zs.mol.R
import com.zs.mol.model.stat.BaseStat.Companion.CHA
import com.zs.mol.model.stat.BaseStat.Companion.CON
import com.zs.mol.model.stat.BaseStat.Companion.DEX
import com.zs.mol.model.stat.BaseStat.Companion.INT
import com.zs.mol.model.stat.BaseStat.Companion.STR
import com.zs.mol.model.stat.BaseStat.Companion.WIS
import com.zs.mol.model.stat.SecondStat.Companion.ATK
import com.zs.mol.model.stat.SecondStat.Companion.CRI
import com.zs.mol.model.stat.SecondStat.Companion.DEF
import com.zs.mol.model.stat.SecondStat.Companion.EVADE
import com.zs.mol.model.stat.SecondStat.Companion.HIT
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.SecondStat.Companion.MATK
import com.zs.mol.model.stat.SecondStat.Companion.MDEF
import com.zs.mol.model.stat.SecondStat.Companion.SPEED
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager
import com.zs.mol.view.base.BaseFragment
import com.zs.mol.view.unit.viewmodel.UnitDetailViewModel
import kotlinx.android.synthetic.main.fragment_unit_detail.*


class UnitDetailFragment : BaseFragment() {

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
        initStatViews(secondStatSequence, secondStatViews, baseStatLayout)

        viewModel.unit.observe(viewLifecycleOwner, Observer { updateUnitInfo(it) })
    }

    private fun handleArguments() {
        arguments?.apply {
            getString("unitId")?.also { id ->
                viewModel.unit.value = UserManager.user.units?.find { unit -> unit.id == id }
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
                    value.minWidth = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        120f,
                        resources.displayMetrics
                    ).toInt()

                    value.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END

                    addView(value)
                    views[it] = value
                }
            }
        }
    }

    private fun updateUnitInfo(unit: BattleUnit) {
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
