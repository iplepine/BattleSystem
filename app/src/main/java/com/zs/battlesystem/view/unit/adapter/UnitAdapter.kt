package com.zs.battlesystem.view.unit.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.battlesystem.view.unit.viewholder.UnitManageViewHolder
import com.zs.battlesystem.view.unit.viewmodel.UnitViewModel

class UnitAdapter(private val viewModel: UnitViewModel) : RecyclerView.Adapter<UnitManageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitManageViewHolder {
        return UnitManageViewHolder(parent, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.getHeroCount()
    }

    override fun onBindViewHolder(holder: UnitManageViewHolder, position: Int) {
        holder.bind(viewModel.getHero(position))
    }
}