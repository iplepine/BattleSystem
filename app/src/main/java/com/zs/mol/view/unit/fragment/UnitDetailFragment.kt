package com.zs.mol.view.unit.fragment

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
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
import com.zs.mol.view.base.BaseFragment
import com.zs.mol.view.unit.viewmodel.UnitDetailViewModel
import kotlinx.android.synthetic.main.fragment_unit_detail.*

class UnitDetailFragment : BaseFragment() {

    val viewModel: UnitDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(UnitDetailViewModel::class.java)
    }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.unitTabComponent().create().inject(this)
    }

    private fun init() {
        handleArguments()
        initStatViews(baseStatSequence, baseStatViews, baseStatLayout)
        initStatViews(secondStatSequence, secondStatViews, secondStatLayout)

        viewModel.unit.observe(viewLifecycleOwner, Observer { updateUnitInfo(it) })
        renameButton.setOnClickListener { onClickRename() }
    }

    private fun handleArguments() {
        arguments?.apply {
            getString(KEY_UNIT_ID)?.also { id ->
                // 퀘스트로 들어오는 유닛도 일종의 유닛 매니저에 저장해서 거기서 찾아야함
                viewModel.unit.value = viewModel.findUnit(id)
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
        level.text = "Lv. ${unit.status.level}"
        name.text = unit.getName()
        royalty.text = "(${unit.hiringStatus.royalty})"

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

    private fun onClickRename() {
        context?.also {
            val input = EditText(it)
            input.inputType = InputType.TYPE_CLASS_TEXT

            AlertDialog.Builder(it)
                .setTitle("이름을 입력 해 주세요.")
                .setView(input)
                .setPositiveButton("변경") { _, _ -> viewModel.rename(input.text.toString()) }
                .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()

            input.requestFocus()
            showSoftKey(input)
        }
    }

    companion object {
        const val KEY_UNIT_ID = "unitId"

        fun newInstance(unitId: String): UnitDetailFragment {
            return UnitDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_UNIT_ID, unitId)
                }
            }
        }
    }
}
