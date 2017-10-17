package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Blockable
import org.itheima.kotlin.game.core.Painter

class Wall(override var x: Int, override var y: Int) : Blockable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }
}