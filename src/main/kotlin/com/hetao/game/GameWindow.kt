package com.hetao.game

import com.hetao.game.business.Blockable
import com.hetao.game.business.Movable
import com.hetao.game.enums.Direction
import com.hetao.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File

class GameWindow : Window(title = "坦克1.0"
        , icon = "img/water.gif"
        , width = Config.gameWidth
        , height = Config.gameHeight) {

    private val views = arrayListOf<View>()
    //晚点创建
    private lateinit var tank: Tank

    override fun onCreate() {

        //读取地图
        val file = File(javaClass.getResource("/map/1.map").path)
        var readline: List<String> = file.readLines()

        var lineNums = 0

        readline.forEach { line ->

            var columnNums = 0

            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> views.add(Wall(columnNums * Config.block, lineNums * Config.block))
                    '草' -> views.add(Grass(columnNums * Config.block, lineNums * Config.block))
                    '铁' -> views.add(Steel(columnNums * Config.block, lineNums * Config.block))
                    '水' -> views.add(Water(columnNums * Config.block, lineNums * Config.block))
                }
                columnNums++
            }

            lineNums++
        }

        //添加坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)
    }

    override fun onDisplay() {

        views.forEach { view ->
            view.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.W -> {
                tank.move(Direction.UP)
            }
            KeyCode.S -> {
                tank.move(Direction.DOWN)
            }
            KeyCode.A -> {
                tank.move(Direction.LEFT)
            }
            KeyCode.D -> {
                tank.move(Direction.RIGHT)
            }
            else -> {

            }
        }
    }

    override fun onRefresh() {

        //做一些逻辑判断的操作
        //找到移动的物体
        views.filter { it is Movable }.forEach { move ->
            //找到阻挡物体
            move as Movable
            var badDirection: Direction? = null
            var badBlock: Blockable? = null

            views.filter { it is Blockable }.forEach blackTag@ { block ->
                //循环判断
                block as Blockable//强制转换

                val direction = move.willCollision(block)

                direction?.let {
                    //.let 为空不走。不为空就走
                    //移动发现碰撞，跳出当前循环
                    badDirection = direction
                    badBlock = block

                    return@blackTag
                }
            }

            //通知可以为空。循环一遍没有找到可能会发生碰撞的物体
            move.notifyCollision(badDirection, badBlock)
        }
    }
}