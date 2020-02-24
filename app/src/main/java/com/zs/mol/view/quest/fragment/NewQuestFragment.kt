package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.mol.R
import com.zs.mol.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_quest.*

class NewQuestFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_quest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        handleArguments()
    }

    fun handleArguments() {
        arguments?.apply {
            questTitle.text = get("questId")?.toString()
        }
    }
}