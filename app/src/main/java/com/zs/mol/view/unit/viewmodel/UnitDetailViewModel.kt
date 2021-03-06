package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.UnitRepository
import com.zs.mol.model.user.User
import javax.inject.Inject

class UnitDetailViewModel @Inject constructor(
    private val unitRepository: UnitRepository,
    @GameScope private val user: User
) : ViewModel() {
    val unit: MutableLiveData<BattleUnit> = MutableLiveData()

    fun rename(name: String) {
        unit.value?.setName(name)
        unit.value = unit.value
    }

    fun findUnit(id: String): BattleUnit? {
        return unitRepository.getUnit(user, id)
    }
}
