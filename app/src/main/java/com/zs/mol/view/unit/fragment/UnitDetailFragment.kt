package com.zs.mol.view.unit.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.zs.mol.model.stat.SecondStat.Companion.MP
import com.zs.mol.model.stat.SecondStat.Companion.SPEED
import com.zs.mol.model.stat.SecondStat.Companion.WILL
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
        initStatViews(secondStatSequence, secondStatViews, secondStatLayout)

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
            // row 부터 다 넣어두고,
            val columnCount = 2
            val rowCount = stats.size / columnCount + 1
            for (i in 0 until rowCount) {
                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.HORIZONTAL
                layout.addView(linearLayout)
            }

            val margin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8f,
                resources.displayMetrics
            ).toInt()

            stats.forEachIndexed { index, stat ->
                val layoutParams =
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                        weight = 1.0f
                        leftMargin = margin
                        rightMargin = margin
                    }
                val rowLayout = layout.getChildAt(index / columnCount) as LinearLayout

                val title = TextView(context)
                title.text = stat

                rowLayout.addView(title, layoutParams)

                val value = TextView(context)
                value.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END

                rowLayout.addView(value, layoutParams)
                views[stat] = value
            }
        }
    }

    private fun updateUnitInfo(unit: BattleUnit) {
        level.text = "Lv. ${unit.level}"
        name.text = unit.name
        royalty.text = "(${unit.royalty})"

        hpBarText.text = String.format(
            "HP : %d/%d",
            unit.currentStat.secondStat.get(HP)?.toInt(),
            unit.totalStat.secondStat.get(HP)?.toInt()
        )

        mpBarText.text = String.format(
            "MP : %d/%d",
            unit.currentStat.secondStat.get(MP)?.toInt(),
            unit.totalStat.secondStat.get(MP)?.toInt()
        )

        willBarText.text = String.format(
            "WILL : %d/%d",
            unit.currentStat.secondStat.get(WILL)?.toInt(),
            unit.totalStat.secondStat.get(WILL)?.toInt()
        )

        baseStatSequence.forEach {
            baseStatViews[it]?.text = unit.totalStat.baseStat[it].toInt().toString()
        }

        secondStatSequence.forEach {
            secondStatViews[it]?.text = unit.totalStat.secondStat[it].toInt().toString()
        }

        updateSkillViews()
    }

    private fun updateSkillViews() {
        skillLayout.removeAllViews()

        val margin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f,
            resources.displayMetrics
        ).toInt()

        viewModel.unit.value?.also { unit ->
            unit.skills.forEach {
                val title = TextView(context)
                title.text = it.getSkill().skillData.name
                title.setPadding(margin, 0, margin, 0)
                skillLayout.addView(title)
            }
        }
    }
}
