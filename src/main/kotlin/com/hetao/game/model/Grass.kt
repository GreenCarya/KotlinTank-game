package com.hetao.game.model

import com.hetao.game.Config
import org.itheima.kotlin.game.core.Painter

class Grass(override var x: Int, override var y: Int) : View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/grass.gif", x, y)
    }
}