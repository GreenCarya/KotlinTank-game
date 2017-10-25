package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 遭受攻击的接口
 */
interface Sufferable : View {

    /**
     * 能挨打的物体具有生命值
     */
    val blood: Int

    /**
     * 挨打返回视图。挨打返回效果。
     */
    fun notifySuffer(attackable: Attackable): Array<View>?
}