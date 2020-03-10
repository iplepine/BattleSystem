package com.zs.mol.view.quest

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.view.base.OnClickItemListener

class QuestDetailViewHolder(
    parent: ViewGroup,
    private val clickItemListener: OnClickItemListener<QuestDetailItem>
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_quest_reward, parent, false)
) {
    private val infoView = itemView.findViewById<TextView>(R.id.info)
    private var item: QuestDetailItem? = null

    init {
        infoView.apply {
            setOnClickListener {
                clickItemListener.onClickItem(item)
            }
            setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN ->
                        clickItemListener.onMouseOver(itemView, item, true)
                    MotionEvent.ACTION_UP ->
                        clickItemListener.onMouseOver(itemView, item, false)
                    MotionEvent.ACTION_CANCEL ->
                        clickItemListener.onMouseOver(itemView, item, false)
                    else -> false
                }
            }
        }
    }

    fun bind(item: QuestDetailItem) {
        this.item = item
        infoView.text = item.toDescription()
    }
}