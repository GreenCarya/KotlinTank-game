package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Blockable
import com.hetao.game.business.Movable
import com.hetao.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) : Movable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    //坦克需要有方向 默认方向朝上
    override var currentDirection = Direction.UP
    //坦克移动的速度
    override val speed: Int = 8

    //坦克不可以走的方向
    private var badDirection: Direction? = null

    override fun draw() {

        //判断坦克的方向 写法1
//        when (direction){
//            Direction.UP -> Painter.drawImage("img/tank_u.gif",x,y)
//            Direction.DOWN -> Painter.drawImage("img/tank_d.gif",x,y)
//            Direction.LEFT -> Painter.drawImage("img/tank_l.gif",x,y)
//            Direction.RIGHT -> Painter.drawImage("img/tank_r.gif",x,y)
//        }
        //写法2
        var imagePath = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    //坦克移动的操作
    fun move(direction: Direction) {

        //判断是否要走向碰撞的方向
        if (direction == badDirection) {
            //如果接下来要走的放下是不让走的方向
            return
        }
        //坦克方向移动的逻辑优化
        if (this.currentDirection != direction) {

            this.currentDirection = direction;
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

    override fun willCollision(block: Blockable): Direction? {
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
        val collision = when {
            (block.y + block.height) <= y -> //如果阻挡物在运动物的上方，不碰撞
                false
            block.y >= (y + height) -> //如果阻挡物在运动物的下方，不碰撞
                false
            (block.x + block.width) <= x -> //如果阻挡物在运动物的左侧，不碰撞
                false
            else -> (x + width) > block.x //如果阻挡物在运动物的右侧，不碰撞
        }
        return if (collision) currentDirection else null
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {

        this.badDirection = direction
    }
}