package com.hetao.game.business

import com.hetao.game.model.View

/**
 * 具备攻击能力
 */
interface Attackable:View {

    /**
     * 攻击力
     */
    val attackPower:Int

    fun isCollision(sufferable: Sufferable):Boolean

    //通知发生了碰撞
    fun notifyAttack(sufferable: Sufferable)
}