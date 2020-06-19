package com.zs.mol.view.quest.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
import com.zs.mol.databinding.FragmentHireBinding
import com.zs.mol.model.quest.HireQuest
import com.zs.mol.view.base.BaseDialogFragment
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import com.zs.mol.view.unit.viewmodel.UnitStatusViewModel
import javax.inject.Inject

class UnitHireFragment : BaseDialogFragment(), QuestView {
    lateinit var binding: FragmentHireBinding

    @Inject
    lateinit var questViewModel: QuestViewModel

    private val unitStatusViewModel: UnitStatusViewModel by lazy {
        ViewModelProvider(requireParentFragment()).get(UnitStatusViewModel::class.java)
        /*ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(UnitStatusViewModel::class.java)*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = FragmentHireBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        context?.apply {
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
            params?.width = (size.x * 0.9).toInt()
            dialog?.window?.attributes = params as WindowManager.LayoutParams

            isCancelable = false
        }
    }

    private fun init() {
        handleArgument()
        binding.closeButton.setOnClickListener { onClickClose() }
        binding.acceptButton.setOnClickListener { onClickAccept() }
        binding.rejectButton.setOnClickListener { onClickReject() }
        binding.unitStatusView.renameButton.setOnClickListener { onClickRename() }
    }

    private fun handleArgument() {
        arguments?.apply {
            val quest = questViewModel.selectedQuest.value as? HireQuest
            if (quest == null) {
                onError(IllegalArgumentException("invalid quest"))
            } else {
                val unit = quest.getUnit()
                if (unit == null) {
                    onError(IllegalArgumentException("can't find unit"))
                } else {
                    unitStatusViewModel.unit = unit
                    binding.unitStatusViewModel = unitStatusViewModel
                }
            }
        }
    }

    override fun onClickAccept() {
        questViewModel.acceptQuest()
    }

    override fun onClickReject() {
        questViewModel.rejectQuest()
    }

    override fun onClickClose() {
        questViewModel.unSelectQuest()
    }

    override fun onClickDetail() {
    }

    private fun onClickRename() {
        context?.also {
            val input = EditText(it)
            input.inputType = InputType.TYPE_CLASS_TEXT

            AlertDialog.Builder(it)
                .setTitle("이름을 입력 해 주세요.")
                .setView(input)
                .setPositiveButton("변경") { _, _ ->
                    unitStatusViewModel.unit.setName(input.text.toString())
                }
                .setNegativeButton("취소") { dialog, _ -> onClickClose() }
                .create()
                .show()

            input.requestFocus()
        }
    }
}