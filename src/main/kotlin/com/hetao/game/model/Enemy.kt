package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.AutoMovable
import com.hetao.game.business.Blockable
import com.hetao.game.business.Movable
import com.hetao.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.util.*

/**
 * 敌方坦克
 */
class Enemy(override var x: Int, override var y: Int) : Movable, AutoMovable,Blockable {

    override var currentDirection: Direction = Direction.DOWN

    override val speed: Int = 8

    override val width: Int = Config.block
    override val height: Int = Config.block

    //坦克不可以走的方向
    private var badDirection: Direction? = null

    override fun draw() {

        var imagePath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }


    override fun notifyCollision(direction: Direction?, block: Blockable?) {

        badDirection = direction
    }

    override fun autoMove() {

        if (currentDirection == badDirection) {
            //不允许错误方向
            //改变自己方向
            currentDirection = rdmDirection(badDirection)
            return
        }

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //规定坦克不能越界
        if (x < 0) x = 0
        if (x > (Config.gameWidth - Config.block)) x = Config.gameWidth - Config.block
        if (y < 0) y = 0
        if (y > (Config.gameHeight - Config.block)) y = Config.gameHeight - Config.block

    }

    private fun rdmDirection(bad: Direction?): Direction {

        var rdm = Random().nextInt(4)
        var direction = when (rdm) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }

        if (bad == direction) {
            return rdmDirection(bad)
        }
        return direction
    }

}