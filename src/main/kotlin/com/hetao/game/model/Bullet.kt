package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Attackable
import com.hetao.game.business.AutoMovable
import com.hetao.game.business.Destoryable
import com.hetao.game.business.Sufferable
import com.hetao.game.enums.Direction
import com.hetao.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹的模块
 *
 * 构造方法传入一个函数 。 函数返回一个存储两个值的对象Pair
 */
class Bullet(override val owner: View, override val currentDirection: Direction
             , create: (width: Int, height: Int) -> Pair<Int, Int>)
    : AutoMovable, Destoryable, Attackable {

    override val speed: Int = 10

    override val width: Int
    override val height: Int

    override var x: Int = 0
    override var y: Int = 0

    private var isDestroyed = false

    override val attackPower: Int = 1

    private val imagePath = when (currentDirection) {
        Direction.UP -> "img/shot_top.gif"
        Direction.DOWN -> "img/shot_bottom.gif"
        Direction.RIGHT -> "img/shot_right.gif"
        Direction.LEFT -> "img/shot_left.gif"
    }

    //初始化方法
    init {

        val size = Painter.size(imagePath)
        width = size[0]
        height = size[1]

        var pair = create.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun draw() {

        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestoryed(): Boolean {

        if (isDestroyed) return true
        //子弹在脱离了屏幕后，需要被销毁
        if (x < -width) return true
        if (x > Config.gameWidth) return true
        if (y < -height) return true
        if (y > Config.gameHeight) return true

        return false
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        //view的扩展方法

        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
//        println("子弹接受到了碰撞")
        //子弹接受到了碰撞
        isDestroyed = true
    }

}
