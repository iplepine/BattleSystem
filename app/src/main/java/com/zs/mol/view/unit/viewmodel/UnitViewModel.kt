package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserRepository
import io.reactivex.subjects.PublishSubject

class UnitViewModel : ViewModel() {
    val onClickUnitSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()
    val onClickUnitActionSubject: PublishSubject<BattleUnit> = PublishSubject.create<BattleUnit>()

    fun getUnitCount(): Int {
        return UserRepository.getUnits().size
    }

    fun getUnit(index: Int): BattleUnit {
        return UserRepository.getUnits().get(index)
    }
}