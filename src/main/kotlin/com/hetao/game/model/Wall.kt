package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Attackable
import com.hetao.game.business.Blockable
import com.hetao.game.business.Destoryable
import com.hetao.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 具备阻塞能力
 * 具备挨打能力
 * 具备销毁能力
 */
class Wall(override var x: Int, override var y: Int) : Blockable ,Sufferable ,Destoryable{

    override var blood: Int = 3

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notifySuffer(attackable: Attackable) {
        //砖墙被打到，生命值要降1
//        println("砖墙被打到，生命值要降1")
        blood -= attackable.attackPower
    }


    override fun isDestoryed(): Boolean = blood <= 0

}