package com.zs.mol.view.quest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.detail.QuestDetailItem

class QuestDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val infoView = itemView.findViewById<TextView>(R.id.info)

    fun bind(item: QuestDetailItem) {
        infoView.text = item.toDescription()
    }
}