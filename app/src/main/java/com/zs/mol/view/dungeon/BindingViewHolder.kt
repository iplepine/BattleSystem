package com.zs.mol.view.dungeon

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<T : BindingItem>(var binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: T) {
        binding.setVariable(item.getVariableId(), item)
    }
}