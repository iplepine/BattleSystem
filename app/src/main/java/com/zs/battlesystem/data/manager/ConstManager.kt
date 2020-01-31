package com.zs.battlesystem.data.manager

import com.zs.battlesystem.data.pojo.Stat
import com.zs.battlesystem.data.pojo.stat.*

/**
 * 스탯 밸런스 관련 문서
 * https://docs.google.com/spreadsheets/d/1F_PV4X_6aQ_WyHP0N5boh4cwJhzMbEKSzsJ-C_UZJCI/edit#gid=733204273
 */

object ConstManager {
    val base = let {
        val array = arrayOf(Strength, Dextery, Intelligence, Constitution, Wisdom, Charisma, Luck)
        HashMap<String, Stat>().apply {
            array.forEach { put(it.key, it) }
        }
    }
}