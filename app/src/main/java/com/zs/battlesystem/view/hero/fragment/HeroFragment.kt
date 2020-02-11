package com.zs.battlesystem.view.hero.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.unit.BaseUnitFactory
import com.zs.battlesystem.model.user.User
import com.zs.battlesystem.view.hero.adapter.HeroAdapter
import com.zs.battlesystem.view.hero.viewmodel.HeroViewModel
import kotlinx.android.synthetic.main.fragment_hero.*

class HeroFragment : Fragment() {

    val viewModel = HeroViewModel()
    var adapter: HeroAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        beforeTest()

        addSubscribers()
        initRecyclerView()
    }

    private fun beforeTest() {
        User.units.apply {
            add(BaseUnitFactory.create("Iplepine"))
            add(BaseUnitFactory.create("Seoty"))
            add(BaseUnitFactory.create("PleaseReleaseMe"))
        }
    }

    private fun addSubscribers() {
    }

    private fun initRecyclerView() {
        context?.also {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = HeroAdapter(viewModel)
            recyclerView.adapter = adapter
        }
    }
}