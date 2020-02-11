package com.zs.battlesystem.view.hero.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.battle.unit.BaseUnitFactory
import com.zs.battlesystem.model.user.User
import com.zs.battlesystem.view.base.BaseFragment
import com.zs.battlesystem.view.hero.adapter.UnitAdapter
import com.zs.battlesystem.view.hero.viewmodel.UnitViewModel
import kotlinx.android.synthetic.main.fragment_unit_manage.*

class UnitManageFragment : BaseFragment() {

    val viewModel = UnitViewModel()
    var adapter: UnitAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unit_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        beforeTest()
        initRecyclerView()
    }

    private fun beforeTest() {
        User.units.apply {
            add(BaseUnitFactory.create("Iplepine"))
            add(BaseUnitFactory.create("Seoty"))
            add(BaseUnitFactory.create("PleaseReleaseMe"))
        }
    }

    override fun addSubscribers() {
        addDisposable(viewModel.onClickUnitSubject.subscribe { unit ->
            showUnitDetails(unit)
        })

        addDisposable(viewModel.onClickUnitActionSubject.subscribe { unit ->
            showActionFragment(unit)
        })
    }

    private fun initRecyclerView() {
        context?.also {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = UnitAdapter(viewModel)
            recyclerView.adapter = adapter
        }
    }

    private fun showUnitDetails(unit: BaseUnit) {
        addFragment(UnitDetailFragment.newInstance(unit))
    }

    private fun showActionFragment(unit: BaseUnit) {

    }
}