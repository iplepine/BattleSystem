package com.zs.battlesystem.model.quest

class Quest(
    val title: String,
    reward: QuestReward?,
    penalty: QuestReward?,
    hints: Array<QuestHint>?
) {
    var state = QuestState.REQUEST
    var due = 1000L
}

enum class QuestState {
    REQUEST, ACCEPTED, SUCCESSED, TIMEOVER, FAILED
}