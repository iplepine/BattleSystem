package com.zs.mol.model.user

import androidx.lifecycle.MutableLiveData
import com.zs.mol.model.battle.unit.BaseUnit

object UserManager {
    val user = MutableLiveData<User>()

    fun newUser(id: String) {
        user.value = User(id)
    }

    fun getUserId(): String {
        return user.value?.id ?: "guest"
    }

    fun getUser(): User? {
        return user.value
    }

    fun getUnits(): List<BaseUnit> {
        return user.value?.units ?: emptyList()
    }

    fun addUnit(unit: BaseUnit) {
        user.value?.units?.add(unit)
    }

    fun isMyUnit(id: String): Boolean {
        return user.value?.id === id
    }
}
