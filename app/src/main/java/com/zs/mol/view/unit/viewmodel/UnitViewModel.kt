package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.model.battle.unit.BaseUnit
import com.zs.mol.model.user.User
import io.reactivex.subjects.PublishSubject

class UnitViewModel : ViewModel() {
    val onClickUnitSubject: PublishSubject<BaseUnit> = PublishSubject.create<BaseUnit>()
    val onClickUnitActionSubject: PublishSubject<BaseUnit> = PublishSubject.create<BaseUnit>()

    fun getHeroCount(): Int {
        return User.units.size
    }

    fun getHero(index: Int): BaseUnit {
        return User.units[index]
    }
}