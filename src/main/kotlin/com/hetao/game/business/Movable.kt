package com.hetao.game.business

import com.hetao.game.enums.Direction
import com.hetao.game.model.View

/**
 * 可移动的物体
 */
interface Movable:View {

    /**
     * 可移动的物体具备方向
     */
    var currentDirection:Direction

    /**
     * 可移动的物体具备速度
     */
    val speed:Int

    /**
     * 物体将要发生碰撞
     *
     * @return 返回null 说明没有碰撞
     */
    fun willCollision(block: Blockable):Direction?

    /**
     * 通知将要和谁在哪个方向发生碰撞
     */
    fun notifyCollision(direction: Direction?,block: Blockable?)
}