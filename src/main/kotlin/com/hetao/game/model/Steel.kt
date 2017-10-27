package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Attackable
import com.hetao.game.business.Blockable
import com.hetao.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Steel(override var x: Int, override var y: Int): Blockable ,Sufferable{

    override val blood: Int = 1

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }

}