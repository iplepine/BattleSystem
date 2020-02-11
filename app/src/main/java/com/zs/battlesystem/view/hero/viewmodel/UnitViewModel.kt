package com.zs.battlesystem.view.hero.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.battlesystem.model.battle.unit.BaseUnit
import com.zs.battlesystem.model.common.Logger
import com.zs.battlesystem.model.user.User
import io.reactivex.subjects.PublishSubject

class UnitViewModel : ViewModel() {
    val onClickUnitSubject = PublishSubject.create<BaseUnit>()
    val onClickUnitActionSubject = PublishSubject.create<BaseUnit>()

    fun getHeroCount(): Int {
        return User.units.size
    }

    fun getHero(index: Int): BaseUnit {
        return User.units[index]
    }

    fun onClickHero(unit: BaseUnit) {
        Logger.d(unit.name + " detail clicked")
    }

    fun onClickHeroAction(unit: BaseUnit) {
        Logger.d(unit.name + " action clicked")
    }
}