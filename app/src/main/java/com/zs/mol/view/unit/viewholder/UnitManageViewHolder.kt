package com.zs.mol.view.unit.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.action.UnitAction
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.SecondStat.Companion.MP
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.UnitState
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import kotlinx.android.synthetic.main.item_unit.view.*

class UnitManageViewHolder(parent: ViewGroup, private val viewModel: UnitViewModel) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_unit, parent, false
        )
    ) {
    val thumbnail = itemView.thumbnail
    val level = itemView.level
    val name = itemView.name
    val hpBar = itemView.hpBar
    val hpBarText = itemView.hpBarText
    val mpBar = itemView.mpBar
    val mpBarText = itemView.mpBarText
    val actionView = itemView.action
    val timeView = itemView.time

    var unit: BattleUnit? = null

    init {
        itemView.cardView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitSubject.onNext(it)
            }
        }

        actionView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitActionSubject.onNext(it)
            }
        }
    }

    fun bind(unit: BattleUnit) {
        this.unit = unit

        level.text = String.format("Lv.%d", unit.getLevel())
        name.text = unit.getName()

        // hp
        val maxHp = unit.totalStat.secondStat.get(HP).toInt()
        val currentHp = unit.currentStat.secondStat.get(HP).toInt()

        if (maxHp == 0) {
            hpBar.progress = 0
        } else {
            hpBar.progress = currentHp * 100 / maxHp
        }
        hpBarText.text = "$currentHp / $maxHp"

        // mp
        val maxMp = unit.totalStat.secondStat.get(MP).toInt()
        val currentMp = unit.currentStat.secondStat.get(MP).toInt()

        if (maxMp == 0) {
            mpBar.progress = 0
        } else {
            mpBar.progress = currentMp * 100 / maxMp
        }
        mpBarText.text = "$currentMp / $maxMp"

        // thumbnail
        //thumbnail.setImageResource(R.drawable.knight_idle_anim_f0)

        // action
        bindUnitAction(unit.status.action)
    }

    private fun bindUnitAction(action: UnitAction?) {
        action?.apply {
            when (state) {
                UnitState.IDLE -> actionView.text = "대기"
                UnitState.WAITING -> actionView.text = "준비 중..."
                UnitState.EXPEDITION -> actionView.text = "모험 중..."
                UnitState.BATTLE -> actionView.text = "전투 중..."
                UnitState.REST -> actionView.text = "휴식 중"
                UnitState.DIE -> actionView.text = "사망"
            }
        }

        timeView.visibility = View.GONE
    }
}