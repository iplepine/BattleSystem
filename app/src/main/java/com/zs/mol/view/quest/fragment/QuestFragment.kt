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
import com.zs.mol.databinding.FragmentQuestBinding
import com.zs.mol.model.common.Logger
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
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
    val viewModel: QuestViewModel by lazy {
        ViewModelProvider(requireActivity()).get(QuestViewModel::class.java)
    }

    lateinit var userStatusViewModel: UserStatusViewModel
    var emptyQuestView: TextView? = null

    lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        initViewModels()
        updateUserStatusView()
        initRecyclerView()
        refreshButton.setOnClickListener { onClickRefresh() }
        newQuestButton.setOnClickListener { onClickQuestItem() }
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
            //refresh()
        })

        viewModel.selectedQuest.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                closeQuestPopup()
            } else {
                openQuestPopup(it)
            }
        })

        viewModel.getAcceptedQuests().observe(viewLifecycleOwner, Observer {
            // TODO 어댑터 처리 해줘야함
        })

        viewModel.getRequests().observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) {
                newQuestButton.visibility = View.GONE
            } else {
                newQuestButton.visibility = View.VISIBLE
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

    private fun onClickQuestItem() {
        // TODO 테스트용 코드
        val testQuest = QuestManager.requests.elements().nextElement()
        viewModel.selectQuest(testQuest)
    }

    private fun onClickRefresh() {
        viewModel.refresh()
    }

    private fun closeQuestPopup() {
        findNavController().navigateUp()
    }

    private fun openQuestPopup(quest: Quest) {
        if (!quest.isValid()) {
            showToast(R.string.error_message_quest_not_valid)
            return
        }

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