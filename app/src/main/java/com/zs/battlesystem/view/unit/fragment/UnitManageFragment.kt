package com.zs.battlesystem.view.unit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.common.Logger
import com.zs.battlesystem.view.base.BaseFragment
import com.zs.battlesystem.view.unit.adapter.UnitAdapter
import com.zs.battlesystem.view.unit.viewmodel.UnitViewModel
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
        initRecyclerView()
    }

    var count = 0

    override fun addSubscribers() {
        count++
        addDisposable(viewModel.onClickUnitSubject.subscribe { unit ->
            showUnitDetails(unit)
            Logger.d("subscriber $count")
        })

        addDisposable(viewModel.onClickUnitActionSubject.subscribe { unit ->
            showActionFragment(unit)
        })

        Logger.d("add Subscribers, ${compositeDisposable.size()}")
    }

    private fun initRecyclerView() {
        context?.also {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = UnitAdapter(viewModel)
            recyclerView.adapter = adapter
        }
    }

    private fun showUnitDetails(unit: BaseUnit) {
        findNavController().navigate(
            R.id.action_unitManageFragment_to_unitDetailFragment,
            bundleOf("unitId" to unit.id)
        )
    }

    private fun showActionFragment(unit: BaseUnit) {

    }
}