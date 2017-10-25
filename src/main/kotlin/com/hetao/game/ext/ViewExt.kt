package com.hetao.game.ext

import com.hetao.game.model.View

/**
 * 扩展方法
 */
fun View.checkCollision(view: View): Boolean {

    return checkCollision(x, y, width, height
            , view.x, view.y, view.width, view.height)
}