package com.zs.mol.view.unit.viewholder

import android.graphics.drawable.Animatable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.SecondStat.Companion.MP
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.action.UnitAction
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_unit.view.*

class UnitManageViewHolder(parent: ViewGroup, private val viewModel: UnitViewModel) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_unit, parent, false
        )
    ) {

    var disposable: Disposable? = null

    private val face = itemView.face
    private val level = itemView.level
    private val name = itemView.name
    private val hpBar = itemView.hpBar
    private val hpBarText = itemView.hpBarText
    private val mpBar = itemView.mpBar
    private val mpBarText = itemView.mpBarText
    private val actionIcon = itemView.actionIcon
    private val actionText = itemView.actionText
    private val timeView = itemView.time
    private val rewardLayout = itemView.rewardLayout
    private val rewardIcon: ImageView = itemView.rewardIcon
    private val rewardText: TextView = itemView.rewardText

    var unit: BattleUnit? = null

    init {
        itemView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitSubject.onNext(it)
            }
        }

        actionIcon.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitActionSubject.onNext(it)
            }
        }

        rewardLayout.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitReportSubject.onNext(it)
            }
        }
    }

    fun onAttached() {
        //TODO
        /*disposable = GameEngine.timeSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateView()
            }*/

        startAnimation()
    }

    fun onDetached() {
        disposable?.dispose()
        disposable = null
    }

    fun bind(unit: BattleUnit) {
        this.unit = unit

        level.text = String.format("Lv.%d", unit.getLevel())
        name.text = unit.getName()

        // hp
        val maxHp = unit.totalStat.secondStat.get(HP).toInt()
        val currentHp = unit.currentStat.secondStat.get(HP).toInt()

        if (maxHp == 0) {
            hpBar.progress = 0
        } else {
            hpBar.progress = currentHp * 100 / maxHp
        }
        hpBarText.text = "$currentHp / $maxHp"

        // mp
        val maxMp = unit.totalStat.secondStat.get(MP).toInt()
        val currentMp = unit.currentStat.secondStat.get(MP).toInt()

        if (maxMp == 0) {
            mpBar.progress = 0
        } else {
            mpBar.progress = currentMp * 100 / maxMp
        }
        mpBarText.text = "$currentMp / $maxMp"

        // face
        face.context?.apply {
            if (TextUtils.isEmpty(unit.status.faceImage)) {
                unit.status.faceImage = viewModel.getRandomFace()
            }

            val id = resources.getIdentifier(unit.status.faceImage, "drawable", packageName)
            if (id == 0) {// error
                face.setImageResource(R.drawable.char_face_f_1)
            } else {
                face.setImageResource(id)
            }
        }

        // action
        updateView()
    }

    private fun updateView() {
        unit?.status?.action?.apply {
            when (actionType) {
                UnitAction.ActionType.IDLE -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_14)
                    actionText.text = "대기"
                }
                UnitAction.ActionType.WAITING -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_4)
                    actionText.text = "준비 중..."
                }
                UnitAction.ActionType.EXPEDITION -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_2)
                    actionText.text = "모험 중..."
                }
                UnitAction.ActionType.BATTLE -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_6)
                    actionText.text = "전투 중..."
                }
                UnitAction.ActionType.REST -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_16)
                    actionText.text = "휴식 중"
                }
                UnitAction.ActionType.DIE -> {
                    actionIcon.setImageResource(R.drawable.icon_gray_3)
                    actionText.text = "사망"
                }
            }

            val timeToSecond = ((time - 1) / 1000).toInt() + 1
            timeView.text = "$timeToSecond 초 남음"
        }

        (unit?.reports?.size ?: 0).also {
            if (it == 0) {
                rewardText.text = "0"
            } else {
                rewardText.text = "$it"
            }
        }
    }

    private fun startAnimation() {
        rewardIcon.setBackgroundResource(R.drawable.animation_chest)
        rewardIcon.postDelayed({
            val chestAnimation = rewardIcon.background
            if (chestAnimation is Animatable) {
                chestAnimation.start()
            }
        }, 0)
    }
}