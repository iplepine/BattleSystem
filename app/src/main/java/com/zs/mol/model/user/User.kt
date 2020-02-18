package com.zs.mol.model.user

import com.google.firebase.database.FirebaseDatabase
import com.zs.mol.model.battle.unit.BaseUnit

object User {
    val id = "mine"

    var level = 1
    var gold: Long = 0

    val units = ArrayList<BaseUnit>()

    fun isMine(id: String): Boolean {
        return this.id == id
    }

    fun loadUserData() {

    }

    fun saveUserData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
}
