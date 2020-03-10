package com.zs.mol.view.quest

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.view.base.OnClickItemListener

class QuestDetailItemAdapter(
    private val items: ArrayList<out QuestDetailItem>,
    private val clickItemListener: OnClickItemListener<QuestDetailItem>
) :
    RecyclerView.Adapter<QuestDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestDetailViewHolder {
        return QuestDetailViewHolder(parent, clickItemListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuestDetailViewHolder, position: Int) {
        if (position < items.size) {
            holder.bind(items[position])
        }
    }
}