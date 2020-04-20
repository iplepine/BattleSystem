package com.zs.mol.view.unit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.mol.R
import com.zs.mol.model.common.Logger
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.unit.adapter.UnitAdapter
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_unit_manage.*

class UnitManageFragment : MainFragment() {

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

    override fun addSubscribers() {
        addDisposable(viewModel.onClickUnitSubject
            .subscribe { unit ->
                showUnitDetails(unit)
            })

        addDisposable(viewModel.onClickUnitActionSubject
            .subscribe { unit ->
                showActionFragment(unit)
            })

        addDisposable(GameEngine.timeSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //adapter?.notifyDataSetChanged()
            })

        Logger.d("add Subscribers, ${compositeDisposable.size()}")
    }

    private fun initRecyclerView() {
        context?.also {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = UnitAdapter(viewModel)
            recyclerView.adapter = adapter
        }

        if (viewModel.getUnitCount() == 0) {
            emptyText.visibility = View.VISIBLE
        } else {
            emptyText.visibility = View.GONE
        }
    }

    private fun showUnitDetails(unit: BattleUnit) {
        findNavController().navigate(
            R.id.action_unitManageFragment_to_unitDetailFragment,
            bundleOf("unitId" to unit.id)
        )
    }

    private fun showActionFragment(unit: BattleUnit) {
    }
}