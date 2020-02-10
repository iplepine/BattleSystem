package com.zs.battlesystem.view.hero.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.battlesystem.view.hero.viewholder.HeroViewHolder
import com.zs.battlesystem.view.hero.viewmodel.HeroViewModel

class HeroAdapter(private val viewModel: HeroViewModel) : RecyclerView.Adapter<HeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(parent, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.getHeroCount()
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(viewModel.getHero(position))
    }
}