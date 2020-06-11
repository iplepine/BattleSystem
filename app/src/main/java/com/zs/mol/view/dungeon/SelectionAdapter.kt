package com.zs.mol.view.dungeon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.zs.lib.view.liverecyclerview.LiveItemViewModel
import com.zs.lib.view.liverecyclerview.LiveViewAdapter
import com.zs.lib.view.liverecyclerview.LiveViewHolder
import com.zs.mol.BR
import com.zs.mol.databinding.ItemSelectionBinding

class SelectionAdapter(lifecycleOwner: LifecycleOwner) :
    LiveViewAdapter<SelectionAdapter.SelectionItemViewModel, SelectionAdapter.SelectionItemViewHolder>(
        lifecycleOwner
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionItemViewHolder {
        return SelectionItemViewHolder(parent)
    }

    class SelectionItemViewModel(val message: String) : LiveItemViewModel() {

    }

    class SelectionItemViewHolder(parent: ViewGroup) :
        LiveViewHolder(ItemSelectionBinding.inflate(LayoutInflater.from(parent.context))) {
        override fun getVariableId(): Int {
            return BR.selectionItemViewModel
        }
    }
}
