package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.mol.R
import com.zs.mol.model.common.Logger
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestType
import com.zs.mol.model.user.UserManager
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.adapter.QuestAdapter
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import com.zs.mol.view.quest.viewmodel.UserStatusViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_quest.*
import kotlinx.android.synthetic.main.view_user_status.view.*

class QuestFragment : MainFragment() {
    val viewModel = QuestViewModel()
    lateinit var userStatusViewModel: UserStatusViewModel
    var emptyQuestView: TextView? = null

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
        initViewModels()
        updateUserStatusView()
        initRecyclerView()
        eventButton.setOnClickListener { onClickNewQuest() }
        refreshButton.setOnClickListener { onClickRefresh() }
    }

    private fun initViewModels() {
        userStatusViewModel =
            ViewModelProvider(requireActivity()).get(UserStatusViewModel::class.java)

        addDisposable(UserManager.updateSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateUserStatusView()
            })

        viewModel.listType.observe(viewLifecycleOwner, Observer {
            refresh()
        })

        viewModel.selectedQuest.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                closeQuestPopup()
            } else {
                openQuestPopup(it)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView?.apply {
            adapter = QuestAdapter(viewModel)
            layoutManager = LinearLayoutManager(context)
        }

        checkQuestEmpty()
    }

    private fun checkQuestEmpty() {
        if (emptyQuestView == null) {
            emptyQuestView = emptyText
        }

        emptyQuestView?.apply {
            if (viewModel.isQuestEmpty()) {
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }

    private fun updateUserStatusView() {
        UserManager.user?.apply {
            userStatusView.levelText.text = "${userStatus.level}"
            userStatusView.nickname.text = id
            //userStatusView.gemText.text = "${UserManager.getGem()}"
            userStatusView.goldText.text = "${UserManager.getGold()}"
        }

        Logger.d("update user status, gold : ${UserManager.getGold()} G")
    }

    private fun refresh() {

    }

    private fun onClickNewQuest() {
        addDisposable(
            viewModel.onClickEventButton()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun onClickRefresh() {
        if (Math.random() < 0.5) {
            eventButton.visibility = View.VISIBLE
        } else {
            eventButton.visibility = View.GONE
        }
    }

    private fun closeQuestPopup() {
        showToast("클로즈 팝업")
    }

    private fun openQuestPopup(quest: Quest) {
        val actionId = when (quest.type) {
            QuestType.HIRE -> R.id.action_questFragment_to_hireQuestFragment
            else -> R.id.action_questFragment_to_newQuestFragment
        }

        findNavController().navigate(
            actionId,
            bundleOf("questId" to quest.id)
        )
    }
}