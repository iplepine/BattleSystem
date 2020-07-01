package com.zs.mol.view.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.R
import com.zs.mol.view.base.MainTabFragment

class FieldTabFragment : MainTabFragment() {
    var viewModel: FieldViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_field, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        activity?.application?.also {
            val viewModelFactory = ViewModelProvider.AndroidViewModelFactory(it)
            viewModel = ViewModelProvider(this, viewModelFactory).get(FieldViewModel::class.java)
        }
    }

    private fun changeDungeon() {
    }
}