package com.zs.mol.model.quest

class Quest(
    val title: String,
    var reward: QuestReward?,
    var penalty: QuestReward?,
    var hints: ArrayList<QuestHint>?
) {
    var state = QuestState.REQUEST
    var due = 1000L
}

enum class QuestState {
    REQUEST, ACCEPTED, SUCCESSED, TIMEOVER, FAILED
}