package com.zs.mol.game.common

import com.zs.mol.model.common.MonoBehaviour
import kotlin.math.abs
import kotlin.math.min

abstract class GameObject : MonoBehaviour {
    var position = PositionF()
        set(value) {
            field = value
            moveTo = PositionF(position)
        }
    var moveTo: PositionF = PositionF(position)

    var speed = 0.1f
    var hasChanged = true

    override fun updateTime(time: Long) {
        if (position != moveTo) {
            move(time)
        }
    }

    open fun move(time: Long) {
        val maxMove = speed * time

        if (position.x < moveTo.x) {
            position.x += min(maxMove, abs(moveTo.x - position.x))
        } else if (moveTo.x < position.x) {
            position.x -= min(maxMove, abs(position.x - moveTo.x))
        }

        if (position.y < moveTo.y) {
            position.y += min(maxMove, abs(moveTo.y - position.y))
        } else if (moveTo.y < position.y) {
            position.y -= min(maxMove, abs(position.y - moveTo.y))
        }

        hasChanged = true
    }
}