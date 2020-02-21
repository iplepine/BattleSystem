package com.zs.mol.model.db

interface Saveable {
    fun toSaveData(): String
    fun fromLoadData(data: String)
}