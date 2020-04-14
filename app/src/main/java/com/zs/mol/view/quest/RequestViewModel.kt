package com.zs.mol.view.quest

import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.QuestManager

class RequestViewModel : ViewModel() {
    fun getRequests() {
        QuestManager.requests
    }
}
