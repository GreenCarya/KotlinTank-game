package com.hetao.game.model

interface View {
    //kotlin的接口可以定义属性
    //位置
    val x: Int
    val y: Int
    //宽高
    val width: Int
    val height: Int
    //控制显示
    fun draw()

    fun checkCollision(x1: Int, y1: Int, width1: Int, height1: Int
                       , x2: Int, y2: Int, width2: Int, height2: Int): Boolean {

        return when {
            (y2 + height2) <= y1 -> //如果阻挡物在运动物的上方，不碰撞
                false
            y2 >= (y1 + height1) -> //如果阻挡物在运动物的下方，不碰撞
                false
            (x2 + width2) <= x1 -> //如果阻挡物在运动物的左侧，不碰撞
                false
            else -> (x1 + width1) > x2 //如果阻挡物在运动物的右侧，不碰撞
        }
    }

//    fun checkCollision(view:View) :Boolean{
//        return false
//    }

}