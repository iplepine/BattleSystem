package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.R
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager
import io.reactivex.subjects.PublishSubject

class UnitViewModel : ViewModel() {
    val onClickUnitSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()
    val onClickUnitActionSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()
    val onClickUnitReportSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()

    fun getUnitCount(): Int {
        return UserManager.getUnits().size
    }

    fun getUnit(index: Int): BattleUnit {
        return UserManager.getUnits().get(index)
    }
}