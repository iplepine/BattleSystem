package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.db.text.TextDB
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.view.base.BaseDialogFragment
import com.zs.mol.view.quest.QuestRewardAdapter
import com.zs.mol.view.quest.viewmodel.NewQuestViewModel
import kotlinx.android.synthetic.main.fragment_new_quest.*

open class NewQuestFragment : BaseDialogFragment() {

    val viewModel = NewQuestViewModel()

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

    private fun handleArguments() {
        arguments?.apply {
            val questId = get("questId").toString()
            QuestManager.getQuest(questId)?.also {
                viewModel.questData.value = it
            }
        }
    }

    fun init() {
        handleArguments()

        if (viewModel.questData.value == null) {
            showToast(TextDB.getText(TextDB.Key.ERROR_QUEST_NOT_FOUND))
            dismiss()
            return
        }

        viewModel.questData.observe(viewLifecycleOwner, Observer {
            initQuestData(it)
            initRecyclerView(requireRecyclerView, it.requires)
            initRecyclerView(rewardRecyclerView, it.rewards)
        })

        view?.findViewById<View>(R.id.acceptButton)?.setOnClickListener { onClickAccept() }
        view?.findViewById<View>(R.id.rejectButton)?.setOnClickListener { onClickReject() }
    }

    private fun initQuestData(quest: Quest) {
        questTitle.text = quest.title
        questDescription.text = quest.description
    }

    private fun initRecyclerView(recyclerView: RecyclerView, items: ArrayList<out QuestDetailItem>) {
        activity?.also { activity ->
            rewardRecyclerView?.apply {
                recyclerView.adapter = QuestRewardAdapter(items)
                recyclerView.layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    private fun onClickAccept() {
        viewModel.accept()
        dismiss()
    }

    private fun onClickReject() {
        viewModel.reject()
        dismiss()
    }
}