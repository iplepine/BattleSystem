package com.zs.mol.model.quest.event

import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.HireQuest
import org.junit.Test

class QuestEventTest {
    @Test
    fun questBuilderTest() {
        val event = Quest.Builder(HireQuest::class.java)
            .setTitle("새로운 지원자가 있습니다")
            .create()

        println(event.toString())
    }
}