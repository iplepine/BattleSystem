package com.zs.battlesystem.view.hero.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zs.battlesystem.R
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.user.User
import com.zs.battlesystem.view.base.BaseFragment
import com.zs.battlesystem.view.hero.viewmodel.UnitDetailViewModel
import kotlinx.android.synthetic.main.fragment_unit_detail.*

class UnitDetailFragment : BaseFragment() {
    companion object {
        fun newInstance(unit: BaseUnit): UnitDetailFragment {
            return UnitDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("unit id", unit.id)
                }
            }
        }
    }

    private val viewModel = UnitDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unit_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        handleArguments()

        viewModel.unit.observe(this, Observer { updateUnitInfo(it) })
    }

    private fun handleArguments() {
        arguments?.apply {
            getString("unit id")?.also { id ->
                viewModel.unit.value = User.units.find { unit -> unit.id == id }
            }
        }
    }

    private fun updateUnitInfo(unit: BaseUnit) {
        name.text = unit.name
    }
}
