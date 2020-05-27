package com.zs.mol.view.quest.dungeon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.dungeon.event.SelectEvent

class SelectEventViewModel : ViewModel() {
    var event = MutableLiveData<SelectEvent>()
}
