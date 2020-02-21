package com.zs.mol.model.db

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.zs.mol.model.pojo.SkillData

abstract class SkillSource {
    val cache: Cache<Int, SkillData> = CacheBuilder.newBuilder()
        .maximumSize(100)
        .build()

    abstract fun getSkillData(id: Int): SkillData

    /*class ServerSource : SkillSource() {
        override fun getSkillData(id: Int): SkillData {
            return SkillData()
        }
    }

    class LocalSource() : SkillSource() {
        override fun getSkillData(id: Int): SkillData {
            return SkillData()
        }
    }*/
}