package com.zs.mol.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zs.mol.R
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.RewardKey
import com.zs.mol.model.user.UserManager
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_quest.*
import kotlinx.android.synthetic.main.view_user_status.view.*

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
        initUserStatusView()
        eventButton.setOnClickListener { onClickNewQuest() }
        refreshButton.setOnClickListener { onClickRefresh() }
    }

    private fun initUserStatusView() {
        UserManager.user?.apply {
            userStatusView.level.text = "Lv. ${userStatus.level}"
            userStatusView.nickname.text = id
            userStatusView.gold.text = "${Inventory.getAmount(RewardKey.GOLD)} G"
        }
    }

    private fun onClickNewQuest() {
        addDisposable(
            viewModel.onClickEventButton()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { quest ->
                    openQuestPopup(quest)
                }
        )
    }

    private fun onClickRefresh() {
        if (Math.random() < 0.5) {
            eventButton.visibility = View.VISIBLE
        } else {
            eventButton.visibility = View.GONE
        }
    }

    private fun openQuestPopup(quest: Quest) {
        findNavController().navigate(
            R.id.action_questFragment_to_newQuestFragment,
            bundleOf("questId" to quest.id)
        )
    }
}