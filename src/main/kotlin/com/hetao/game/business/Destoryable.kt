package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 销毁的能力
 */
interface Destoryable : View {

    fun isDestoryed(): Boolean
}