package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.model.unit.BaseUnit
import com.zs.mol.model.user.UserManager
import io.reactivex.subjects.PublishSubject

class UnitViewModel : ViewModel() {
    val onClickUnitSubject: PublishSubject<BaseUnit> = PublishSubject.create<BaseUnit>()
    val onClickUnitActionSubject: PublishSubject<BaseUnit> = PublishSubject.create<BaseUnit>()

    fun getHeroCount(): Int {
        return UserManager.getUnits().size
    }

    fun getHero(index: Int): BaseUnit {
        return UserManager.getUnits().get(index)
    }
}