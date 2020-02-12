package com.zs.battlesystem.view.hero.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.HP
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.MP
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.battle.unit.UnitAction
import com.zs.battlesystem.view.hero.viewmodel.UnitViewModel
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

    var unit: BaseUnit? = null

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

    fun bind(unit: BaseUnit) {
        this.unit = unit

        level.text = String.format("Lv.%02d", unit.level)
        name.text = unit.name

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
        thumbnail.setImageResource(R.drawable.knight_idle_anim_f0)

        // action
        bindUnitAction(unit.action)
    }

    private fun bindUnitAction(action: UnitAction) {
        when (action) {
            UnitAction.IDLE -> actionView.setImageResource(R.drawable.baseline_play_arrow_black_36)
            UnitAction.ADVENTURE -> actionView.setImageResource(R.drawable.baseline_play_arrow_black_48)
            UnitAction.BATTLE -> actionView.setImageResource(R.drawable.weapon_sword_1)
            UnitAction.REST -> actionView.setImageResource(R.drawable.baseline_hotel_black_48)
            UnitAction.DIE -> actionView.setImageResource(R.drawable.baseline_clear_black_48)
        }

        timeView.visibility = View.GONE
    }
}