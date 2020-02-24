package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zs.mol.R
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import kotlinx.android.synthetic.main.fragment_quest.*

class QuestFragment : MainFragment() {
    val viewModel = QuestViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        viewModel.newQuest.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(
                R.id.action_questFragment_to_newQuestFragment,
                bundleOf("questId" to it.id)
            )
        })
        eventButton.setOnClickListener { onClickEvent() }
        refreshButton.setOnClickListener { onClickRefresh() }
    }

    fun onClickEvent() {
        viewModel.onClickEventButton()
    }

    fun onClickRefresh() {
        showToast("refresh")
        if (Math.random() < 0.5) {
            eventButton.visibility = View.VISIBLE
        } else {
            eventButton.visibility = View.GONE
        }
    }
}