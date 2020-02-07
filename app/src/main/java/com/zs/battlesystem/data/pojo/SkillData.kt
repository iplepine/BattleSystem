package com.zs.battlesystem.data.pojo

data class SkillData(
    val name: String,
    val description: String,
    val type: String,
    val castingTime: Long,
    val effectTime: Long,
    val afterDelay: Long,
    val coolTime: Long,
    var targetType: Long
)