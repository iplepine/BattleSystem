package com.zs.mol.model.unit

import com.zs.mol.model.consts.ManNamePool
import com.zs.mol.model.consts.WomanNamePool
import com.zs.mol.model.stat.StatFactory
import com.zs.mol.model.user.UserManager
import kotlin.random.Random

object BattleUnitFactory {

    private const val MAN_FACE_SIZE = 71
    private const val WOMAN_FACE_SIZE = 59

    fun createUnit(owner: String, name: String?): BattleUnit {
        return BattleUnit(owner).apply {
            val isMan = Random.nextBoolean()
            val rename = name ?: getRandomName(isMan)
            setName(rename)
            status.faceImage = getRandomFace(isMan)
            originalStat = StatFactory.randomStat()
            updateStat()
        }
    }

    fun createMyUnit(name: String = getRandomName()): BattleUnit {
        return createUnit(UserManager.getUserId(), name)
    }

    fun createEnemy(name: String = getRandomName()): BattleUnit {
        return createUnit(UserManager.getEnemyId(), name)
    }

    fun getRandomName(isMale: Boolean = Random.nextBoolean()): String {
        return if (isMale) {
            // man
            val index = (Math.random() * ManNamePool.NAME_LIST.size).toInt()
            ManNamePool.NAME_LIST[index]
        } else {
            // woman
            val index = (Math.random() * WomanNamePool.NAME_LIST.size).toInt()
            WomanNamePool.NAME_LIST[index]
        }
    }

    fun getRandomFace(isMale: Boolean = Random.nextBoolean()): String {
        return if (isMale) {
            val index = (Math.random() * MAN_FACE_SIZE + 1).toInt()
            "char_face_m_$index"
        } else {
            val index = (Math.random() * WOMAN_FACE_SIZE + 1).toInt()
            "char_face_f_$index"
        }
    }
}
