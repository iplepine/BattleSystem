package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.item.Item
import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.User
import javax.inject.Inject

class UserStatusViewModel @Inject constructor(
    @GameScope val user: User
) : ViewModel() {

    fun getLevel(): String {
        return user.userData.level.toString()
    }

    fun getGem(): DefaultLiveData<Item> {
        return user.getItemLiveData(ItemKey.GEM)
    }

    fun getGold(): DefaultLiveData<Item> {
        return user.getItemLiveData(ItemKey.GOLD)
    }

    init {
        Logger.d("init UserStatusViewModel")
    }

}
