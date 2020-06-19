package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.common.Logger
import com.zs.mol.model.user.UserStatus

class UserStatusViewModel : ViewModel() {

    val goldAmount = MutableLiveData<Long>()

    init {
        Logger.d("init UserStatusViewModel")
    }

    fun getUserStatus(): LiveData<UserStatus> {

    }
}