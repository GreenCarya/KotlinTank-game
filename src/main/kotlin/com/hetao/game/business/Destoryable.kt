package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 销毁的能力
 */
interface Destoryable : View {

    fun isDestoryed(): Boolean

    /**
     * 销毁
     * 谁要实现，自己实现
     */
    fun showDestroy(): Array<View>? {
        return null
    }
}