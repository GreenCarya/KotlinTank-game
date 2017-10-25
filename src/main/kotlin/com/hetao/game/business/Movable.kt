package com.hetao.game.business

import com.hetao.game.Config
import com.hetao.game.enums.Direction
import com.hetao.game.model.View

/**
 * 可移动的物体
 */
interface Movable : View {

    /**
     * 可移动的物体具备方向
     */
    var currentDirection: Direction

    /**
     * 可移动的物体具备速度
     */
    val speed: Int

    /**
     * 物体将要发生碰撞
     *
     * @return 返回null 说明没有碰撞
     */
    fun willCollision(block: Blockable): Direction? {
        //未来的坐标
        var x = this.x
        var y = this.y
        //将要碰撞的判断
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //碰撞的判断，检测下一步是否碰撞
//        val collision = when {
//            (block.y + block.height) <= y -> //如果阻挡物在运动物的上方，不碰撞
//                false
//            block.y >= (y + height) -> //如果阻挡物在运动物的下方，不碰撞
//                false
//            (block.x + block.width) <= x -> //如果阻挡物在运动物的左侧，不碰撞
//                false
//            else -> (x + width) > block.x //如果阻挡物在运动物的右侧，不碰撞
//        }

        //和边界进行判断
        //规定坦克不能越界
        if (x < 0) return Direction.LEFT
        if (x > (Config.gameWidth - Config.block)) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > (Config.gameHeight - Config.block)) return Direction.DOWN

        val collision = checkCollision(x, y, width, height
                , block.x, block.y, block.width, block.height)

        return if (collision) currentDirection else null
    }

    /**
     * 通知将要和谁在哪个方向发生碰撞
     */
    fun notifyCollision(direction: Direction?, block: Blockable?)
}