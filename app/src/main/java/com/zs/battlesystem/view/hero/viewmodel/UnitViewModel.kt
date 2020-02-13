package com.zs.battlesystem.view.hero.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.user.User
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
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