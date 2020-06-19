package com.zs.mol.view.quest.fragment

import android.content.Context
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
import com.zs.mol.MainActivity
import com.zs.mol.R
import com.zs.mol.databinding.FragmentQuestBinding
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.QuestType
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.adapter.QuestAdapter
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import com.zs.mol.view.quest.viewmodel.UserStatusViewModel
import kotlinx.android.synthetic.main.fragment_quest.*
import javax.inject.Inject

class QuestFragment : MainFragment() {

    @Inject
    lateinit var questManager: QuestManager

    @Inject
    lateinit var viewModel: QuestViewModel

    val userStatusViewModel: UserStatusViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UserStatusViewModel::class.java)
    }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.inject(this)
    }

    fun init() {
        initViewModels()
        initRecyclerView()
        refreshButton.setOnClickListener { onClickRefresh() }
        newQuestButton.setOnClickListener { onClickQuestItem() }
    }

    private fun initViewModels() {
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

    private fun onClickQuestItem() {
        // TODO 테스트용 코드
        val testQuest = questManager.requests.elements().nextElement()
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