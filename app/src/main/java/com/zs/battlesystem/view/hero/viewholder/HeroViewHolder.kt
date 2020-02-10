package com.zs.battlesystem.view.hero.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.stat.SecondStat.Companion.HP
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.view.hero.viewmodel.HeroViewModel
import kotlinx.android.synthetic.main.item_hero.view.*

class HeroViewHolder(parent: ViewGroup, private val viewModel: HeroViewModel) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_hero, parent, false
        )
    ) {
    val thumbnail = itemView.thumbnail
    val level = itemView.level
    val name = itemView.name
    val hpBar = itemView.hpBar
    val action = itemView.action

    var unit: BaseUnit? = null

    init {
        parent.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitSubject.onNext(it)
                viewModel.onClickHero(it)
            }
        }

        action.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitActionSubject.onNext(it)
                viewModel.onClickHeroAction(it)
            }
        }
    }

    fun bind(unit: BaseUnit) {
        this.unit = unit

        level.text = unit.level.toString()
        name.text = unit.name
        hpBar.text =
            "${unit.currentStat.secondStat.get(HP).toInt()} / ${unit.totalStat.secondStat.get(HP).toInt()}"

    }
}