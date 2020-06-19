package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.common.Logger

class UserStatusViewModel : ViewModel() {

    val goldAmount = MutableLiveData<Long>()

    init {
        Logger.d("init UserStatusViewModel")
    }

}