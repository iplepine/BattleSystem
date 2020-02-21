package com.zs.mol.model.battle.skill

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.zs.mol.model.db.Saveable

class SkillStatus : Saveable {
    var level = 0
    var coolDown = 0L

    var skillId = 0
    var skill: Skill? = null

    override fun toSaveData(): String {
        val json = JsonObject()
        json.addProperty("level", level)
        json.addProperty("coolDown", coolDown)
        json.addProperty("skillId", skillId)

        return json.toString()
    }

    override fun fromLoadData(data: String) {
        val json = JsonParser().parse(data).asJsonObject
        level = json.get("level").asInt
        coolDown = json.get("coolDown").asLong
        skillId = json.get("skillId").asInt
    }
}