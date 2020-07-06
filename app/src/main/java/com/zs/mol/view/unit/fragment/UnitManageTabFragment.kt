package com.zs.mol.view.unit.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.mol.MainActivity
import com.zs.mol.R
import com.zs.mol.model.common.Logger
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.view.base.MainTabFragment
import com.zs.mol.view.quest.viewmodel.UserStatusViewModel
import com.zs.mol.view.unit.adapter.UnitAdapter
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_unit_manage.*

class UnitManageTabFragment : MainTabFragment() {

    val viewModel: UnitViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(UnitViewModel::class.java)
    }

    val userStatusViewModel: UserStatusViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(UserStatusViewModel::class.java)
    }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.unitTabComponent().create().inject(this)
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
                showFieldMap(unit)
            })

        addDisposable(gameEngine.timeSubject
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

    private fun showFieldMap(unit: BattleUnit) {
        findNavController().navigate(
            R.id.action_unitManageFragment_to_unitDetailFragment,
            bundleOf("unitId" to unit.id)
        )
    }
}