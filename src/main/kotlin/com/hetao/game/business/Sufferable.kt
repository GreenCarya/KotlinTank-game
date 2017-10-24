package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 遭受攻击的接口
 */
interface Sufferable:View {

    fun notifySuffer(attackable: Attackable)
}