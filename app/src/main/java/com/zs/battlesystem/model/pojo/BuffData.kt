package com.zs.battlesystem.model.pojo

data class BuffData(
    val name: String,
    val duration: Long,
    val effectDelay: Long,
    val cancelable: Boolean
)