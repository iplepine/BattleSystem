package com.zs.mol.model.common

open class QuestNode {
    val preNode = ArrayList<QuestNode>()
    val nextNode = ArrayList<QuestNode>()
}