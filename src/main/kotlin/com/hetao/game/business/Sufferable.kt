package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 遭受攻击的接口
 */
interface Sufferable:View {

    /**
     * 能挨打的物体具有生命值
     */
    val blood:Int

    fun notifySuffer(attackable: Attackable)
}