package com.zs.mol.view.quest.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.MainActivity
import com.zs.mol.R
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.text.TextDB
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.view.base.BaseDialogFragment
import com.zs.mol.view.base.OnClickItemListener
import com.zs.mol.view.quest.QuestDetailItemAdapter
import com.zs.mol.view.quest.viewmodel.NewQuestViewModel
import com.zs.mol.view.quest.viewmodel.UserStatusViewModel
import kotlinx.android.synthetic.main.fragment_new_quest.*
import javax.inject.Inject


open class NewQuestFragment : BaseDialogFragment() {

    @Inject
    lateinit var viewModel: NewQuestViewModel

    @Inject
    lateinit var questRepository: QuestRepository

    lateinit var userStatusViewModel: UserStatusViewModel

    private var detailItemView: View? = null

    var quest: Quest? = null
        get() = viewModel.questData?.value

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.inject(this)
    }

    private fun handleArguments() {
        arguments?.apply {
            val questId = get("questId")?.toString()
            if (questId == null) {
                showToast(TextDB.getText(TextDB.Key.ERROR_QUEST_NOT_FOUND))
                dismiss()
                return
            }

            val quest = questRepository.getQuest(questId)
            if (quest == null) {
                showToast(TextDB.getText(TextDB.Key.ERROR_QUEST_NOT_FOUND))
                dismiss()
            } else {
                viewModel.questData.value = quest
            }
        }
    }

    open fun init() {
        initViewModels()
        handleArguments()

        view?.findViewById<View>(R.id.acceptButton)?.setOnClickListener { onClickAccept() }
        view?.findViewById<View>(R.id.rejectButton)?.setOnClickListener { onClickReject() }
        view?.findViewById<View>(R.id.detailButton)?.setOnClickListener { onClickDetail() }
    }

    private fun initViewModels() {
        userStatusViewModel =
            ViewModelProvider(requireActivity()).get(UserStatusViewModel::class.java)

        viewModel.questData.observe(viewLifecycleOwner, Observer {
            initQuestData(it)
            initRecyclerView(requireRecyclerView, it.requires)
            initRecyclerView(rewardRecyclerView, it.rewards)
        })
    }

    private fun initQuestData(quest: Quest) {
        questTitle.text = quest.title
        questDescription.text = quest.description
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        items: ArrayList<out QuestDetailItem>
    ) {
        activity?.also { activity ->
            rewardRecyclerView?.apply {
                recyclerView.adapter =
                    QuestDetailItemAdapter(items, object : OnClickItemListener<QuestDetailItem>() {
                        override fun onClickItem(item: QuestDetailItem?) {
                            item?.apply {
                                showToast(item.toDescription())
                            }
                        }

                        override fun onMouseOver(
                            view: View,
                            item: QuestDetailItem?,
                            isOn: Boolean
                        ): Boolean {
                            if (isOn && item != null) {
                                showDetailItemView(view, item)
                            } else {
                                hideDetailItemView()
                            }
                            return true
                        }
                    })
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

    open fun onClickDetail() {
    }

    private fun showDetailItemView(view: View, item: QuestDetailItem) {
        Logger.d("" + view.x + "," + view.y)
        val rootLayout = rootLayout
        detailItemView?.apply {
            rootLayout.removeView(this)
        }

        detailItemView = TextView(context).apply {
            text = item.toDescription()
        }
        rootLayout.addView(
            detailItemView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        var viewPosition = IntArray(2)
        view.getLocationInWindow(viewPosition)

        val params = detailItemView!!.layoutParams as ConstraintLayout.LayoutParams
        params.topToTop = rootLayout.id
        params.leftToLeft = rootLayout.id

        params.topMargin = viewPosition[1] - rootLayout.paddingTop - view.height * 2
        params.leftMargin = viewPosition[0]

        view.requestLayout()
    }

    private fun hideDetailItemView() {
        detailItemView?.visibility = View.GONE
    }
}