package com.hetao.game.model

interface View {
    //kotlin的接口可以定义属性
    //位置
    val x:Int
    val y:Int
    //宽高
    val width:Int
    val height:Int
    //控制显示
    fun draw()
}