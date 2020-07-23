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
        val place = getDungeonPlace(mapPosition)

        if (place == null) {
            holder.clear()
        } else {
            holder.bind(place)
        }
    }

    private fun get2DPosition(position: Int): Position {
        val x = position % 5
        val y = position / 5
        
        return Position(x, y)
    }

    private fun getDungeonPlace(position: Position): TileAndGraphBasedMaker.TiledPlace? {
        val current = viewModel.currentPlace.value?.position

        if (current != null && 2 < abs(current.x - position.x) + abs(current.y - position.y)) {
            return null
        }

        return dungeon?.getPlace(position)
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
        var place: TileAndGraphBasedMaker.TiledPlace? = null

        init {
            itemView.setOnClickListener {
                viewModel.changePlace(place)
            }
        }

        fun clear() {
            typeIcon.visibility = View.INVISIBLE
            text.visibility = View.INVISIBLE
        }

        fun bind(place: TileAndGraphBasedMaker.TiledPlace) {
            this.place = place
            typeIcon.visibility = View.VISIBLE
            text.visibility = View.VISIBLE

            typeIcon.setImageResource(getTypeImage(place))
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
