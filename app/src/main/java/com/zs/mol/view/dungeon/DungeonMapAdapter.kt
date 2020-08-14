package com.zs.mol.view.dungeon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.databinding.ItemDungeonMapBinding
import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.DungeonPlace
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker
import kotlin.math.abs

class DungeonMapAdapter(var viewModel: DungeonViewModel) :
    RecyclerView.Adapter<DungeonMapAdapter.DungeonMapItemViewHolder>() {

    val dungeon = viewModel.dungeon.value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DungeonMapItemViewHolder {
        return DungeonMapItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: DungeonMapItemViewHolder, position: Int) {
        if (dungeon?.map == null) {
            holder.clear()
        }

        val mapPosition = get2DPosition(position)
        // current 가 무조건 중앙에 와야함 (2,2)에 올 수 있게 처리
        val current = viewModel.currentPosition.value ?: Position(0, 0)
        mapPosition.apply {
            x = x - 2 + current.x
            y = y - 2 + current.y
        }

        val place = getDungeonPlace(mapPosition)
        holder.bind(place, mapPosition)
    }

    private fun get2DPosition(position: Int): Position {
        val x = position % 5
        val y = position / 5

        return Position(x, y)
    }

    private fun getDungeonPlace(mapPosition: Position): TileAndGraphBasedMaker.TiledPlace? {
        // 시야 처리
        val eyeSight = viewModel.eyesight
        val current = viewModel.currentPosition.value ?: Position(0, 0)
        if (eyeSight < abs(mapPosition.x - current.x) + abs(mapPosition.y - current.y)) {
            return null
        }

        return dungeon?.getPlace(mapPosition)
    }

    inner class DungeonMapItemViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            ItemDungeonMapBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        ) {

        val typeIcon = itemView.findViewById<ImageView>(R.id.typeIcon)
        val text = itemView.findViewById<TextView>(R.id.text)
        var position = Position(0, 0)

        init {
            itemView.setOnClickListener {
                viewModel.changePosition(position)
            }
        }

        fun clear() {
            typeIcon.visibility = View.INVISIBLE
            text.visibility = View.INVISIBLE
        }

        fun bind(place: TileAndGraphBasedMaker.TiledPlace?, position: Position) {
            this.position = position
            if (place == null) {
                clear()
            } else {
                typeIcon.visibility = View.VISIBLE
                text.visibility = View.VISIBLE
                typeIcon.setImageResource(getTypeImage(place))
            }
        }

        private fun getTypeImage(place: DungeonPlace): Int {
            return when (place.type) {
                DungeonPlace.PlaceType.BOSS -> R.drawable.icon_gray_3
                DungeonPlace.PlaceType.MONSTER -> R.drawable.icon_gray_6
                DungeonPlace.PlaceType.TRAP -> R.drawable.icon_gray_9
                DungeonPlace.PlaceType.GROUND -> R.drawable.icon_gray_13
                DungeonPlace.PlaceType.ITEM -> R.drawable.icon_gray_4
                DungeonPlace.PlaceType.ENTRANCE -> R.drawable.icon_gray_7
                else -> R.drawable.icon_gray_9
            }
        }
    }
}
