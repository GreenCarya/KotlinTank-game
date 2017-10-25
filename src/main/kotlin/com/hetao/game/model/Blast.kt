package com.hetao.game.model

import com.hetao.game.Config
import com.hetao.game.business.Destoryable
import org.itheima.kotlin.game.core.Painter

/**
 * 爆炸物的动画
 */
class Blast(override val x: Int, override val y: Int) : Destoryable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    private val imagePaths = arrayListOf<String>()

    private var index: Int = 0

    init {
        var nums = 1..32
        nums.forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        val i: Int = index % imagePaths.size

        Painter.drawImage(imagePaths[i], x, y)

        index++
    }

    override fun isDestoryed(): Boolean {
        return index >= imagePaths.size
    }


}