package com.zs.mol.model.unit

import com.zs.mol.model.consts.ManNamePool
import com.zs.mol.model.consts.WomanNamePool
import com.zs.mol.model.stat.StatFactory
import com.zs.mol.model.user.UserManager
import java.util.concurrent.ArrayBlockingQueue
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

class BattleUnitFactory @Inject constructor(private val userManager: UserManager) {

    companion object {
        private const val MAN_FACE_SIZE = 71
        private const val WOMAN_FACE_SIZE = 58

        private val usedNameCache = ArrayBlockingQueue<String>(7)
        private val usedFaceCache = ArrayBlockingQueue<String>(7)

        private const val RETRY_COUNT = 3
    }

    fun createUnit(owner: String, name: String?): BattleUnit {
        return BattleUnit(owner).apply {
            val isMan = Random.nextBoolean()
            val rename = name ?: getSmartRandomName(isMan)
            setName(rename)
            status.faceImage = getRandomFace(isMan)
            originalStat = StatFactory.randomStat()
            updateStat()
        }
    }

    fun createMyUnit(name: String = getRandomName()): BattleUnit {
        return createUnit(userManager.getUserId(), name)
    }

    fun createEnemy(name: String = getRandomName()): BattleUnit {
        return createUnit(userManager.getEnemyId(), name)
    }

    fun getSmartRandomName(isMale: Boolean = Random.nextBoolean(), retryCount: Int = 0): String {
        val name = getRandomName(isMale)
        return if (usedNameCache.contains(name) && RETRY_COUNT < retryCount) {
            getSmartRandomName(isMale, retryCount + 1)
        } else {
            usedNameCache.add(name)
            name
        }
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

    private fun getStartRandomFace(isMale: Boolean, retryCount: Int = 0): String {
        val name = getRandomFace(isMale)
        return if (usedFaceCache.contains(name) && RETRY_COUNT < retryCount) {
            getStartRandomFace(isMale, retryCount + 1)
        } else {
            usedFaceCache.add(name)
            name
        }
    }

    private fun getRandomFace(isMale: Boolean = Random.nextBoolean()): String {
        return if (isMale) {
            val index = (Math.random() * MAN_FACE_SIZE + 1).toInt()
            "char_face_m_$index"
        } else {
            val index = (Math.random() * WOMAN_FACE_SIZE + 1).toInt()
            "char_face_f_$index"
        }
    }
}
