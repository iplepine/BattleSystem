package com.zs.mol.view.unit.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.view.unit.viewholder.UnitManageViewHolder
import com.zs.mol.view.unit.viewmodel.UnitViewModel

class UnitAdapter(private val viewModel: UnitViewModel) :
    RecyclerView.Adapter<UnitManageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitManageViewHolder {
        return UnitManageViewHolder(parent, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.getUnitCount()
    }

    override fun onBindViewHolder(holder: UnitManageViewHolder, position: Int) {
        holder.bind(viewModel.getUnit(position))
    }

    override fun onViewAttachedToWindow(holder: UnitManageViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: UnitManageViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetached()
    }
}