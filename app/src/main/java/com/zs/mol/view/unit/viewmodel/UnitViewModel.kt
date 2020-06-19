package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.user.UserManager
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UnitViewModel @Inject constructor(
    private val userManager: UserManager,
    private val battleUnitFactory: BattleUnitFactory
) : ViewModel() {
    val onClickUnitSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()
    val onClickUnitActionSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()
    val onClickUnitReportSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()

    fun getUnitCount(): Int {
        return userManager.getUnits().size
    }

    fun getUnit(index: Int): BattleUnit {
        return userManager.getUnits().get(index)
    }

    fun getRandomFace(): String {
        return battleUnitFactory.getSmartRandomName()
    }
}