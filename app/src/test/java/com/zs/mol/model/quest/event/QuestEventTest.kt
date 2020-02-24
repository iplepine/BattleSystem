package com.zs.mol.model.quest.event

import org.junit.Test

class QuestEventTest {
    @Test
    fun questBuilderTest() {
        val event = QuestEvent.Builder(UnitHireEvent::class.java)
            .setTitle("새로운 지원자가 있습니다")
            .create()

        println(event.toString())
    }
}