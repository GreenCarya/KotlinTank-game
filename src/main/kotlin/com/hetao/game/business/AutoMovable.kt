package com.hetao.game.business

import com.hetao.game.enums.Direction
import com.hetao.game.model.View

/**
 * 自动移动的能力
 * 都有速度和方向
 */
interface AutoMovable : View {

    val speed: Int
    val currentDirection: Direction

    fun autoMove()
}