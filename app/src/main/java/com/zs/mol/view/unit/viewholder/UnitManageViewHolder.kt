package com.zs.mol.view.unit.viewholder

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.SecondStat.Companion.MP
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.unit.UnitState
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_unit.view.*

class UnitManageViewHolder(parent: ViewGroup, private val viewModel: UnitViewModel) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_unit, parent, false
        )
    ) {

    var disposable: Disposable? = null

    val face = itemView.face
    val level = itemView.level
    val name = itemView.name
    val hpBar = itemView.hpBar
    val hpBarText = itemView.hpBarText
    val mpBar = itemView.mpBar
    val mpBarText = itemView.mpBarText
    val actionView = itemView.action
    val timeView = itemView.time
    val reportView = itemView.report

    var unit: BattleUnit? = null

    init {
        itemView.cardView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitSubject.onNext(it)
            }
        }

        actionView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitActionSubject.onNext(it)
            }
        }

        reportView.setOnClickListener {
            unit?.also {
                viewModel.onClickUnitReportSubject.onNext(it)
            }
        }
    }

    fun onAttached() {
        disposable = GameEngine.timeSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateView()
            }
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
                unit.status.faceImage = BattleUnitFactory.getRandomFace()
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
            when (state) {
                UnitState.IDLE -> actionView.text = "대기"
                UnitState.WAITING -> actionView.text = "준비 중..."
                UnitState.EXPEDITION -> actionView.text = "모험 중..."
                UnitState.BATTLE -> actionView.text = "전투 중..."
                UnitState.REST -> actionView.text = "휴식 중"
                UnitState.DIE -> actionView.text = "사망"
            }

            val timeToSecond = ((time - 1) / 1000).toInt() + 1
            timeView.text = "$timeToSecond 초 남음"
        }

        (unit?.reports?.size ?: 0).also {
            if (it == 0) {
                reportView.text = "보상 없음"
            } else {
                reportView.text = "얻은 보상 : $it"
            }
        }
    }
}