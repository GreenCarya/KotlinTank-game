package com.hetao.game

import com.hetao.game.business.*
import com.hetao.game.enums.Direction
import com.hetao.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(title = "坦克1.0"
        , icon = "img/water.gif"
        , width = Config.gameWidth
        , height = Config.gameHeight) {

    //改为线程安全的集合
//    private val views = arrayListOf<View>()
    private val views = CopyOnWriteArrayList<View>()
    //晚点创建
    private lateinit var tank: Tank
    //游戏是否结束
    private var gameOver: Boolean = false

    //敌方数量
    private var enemyTotalSize = 3
    //敌方激活的数量
    private var enemyActiveSize = 1
    //敌方的出生点
    private val enemyBornLocation = arrayListOf<Pair<Int, Int>>()
    //敌方出生点的下标
    private var bornIndex = 0

    override fun onCreate() {

        //读取地图
        val file = File(javaClass.getResource("/map/2.map").path)
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
                    '敌' -> enemyBornLocation.add(Pair(columnNums * Config.block, lineNums * Config.block))
                }
                columnNums++
            }

            lineNums++
        }

        //添加坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        //添加大本营
        views.add(Camp(Config.gameWidth / 2 - Config.block, Config.gameHeight - 96))
    }

    override fun onDisplay() {

        views.forEach { view ->
            view.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {

        if (!gameOver) {
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
                KeyCode.ENTER -> {
                    val bullet = tank.shot()
                    views.add(bullet)
                }
                else -> {

                }
            }
        }

    }

    var startTime: Long = 0
    var endTime: Long = 0
    override fun onRefresh() {

        //移除销毁的物体
        views.filter { it is Destoryable }.forEach {
            //如果物体被销毁了，再执行移除
            if ((it as Destoryable).isDestoryed()) {
                views.remove(it)

                if (it is Enemy) {
                    enemyTotalSize--
                }

                val showDestroy = it.showDestroy()
                showDestroy?.let {
                    views.addAll(showDestroy)
                }
            }
        }
        //游戏结束
        if (gameOver == true) return

        startTime = System.currentTimeMillis()
        //做一些逻辑判断的操作
        //找到移动的物体
        views.filter { it is Movable }.forEach { move ->
            //找到阻挡物体
            move as Movable
            var badDirection: Direction? = null
            var badBlock: Blockable? = null

            //如果一个物体同时具有阻挡属性和移动属性。
            // 自己不要和自己比较
            views.filter { (it is Blockable) and (move != it) }.forEach blackTag@ { block ->
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

        //检测自动移动能力的物体，让他们自动动起来
        views.filter { it is AutoMovable }.forEach {
            (it as AutoMovable).autoMove()
        }


        //检测具备攻击能力的和遭受攻击的是否发生碰撞
        //1.过滤具备攻击能力的
        views.filter { it is Attackable }.forEach attackTag@ { attack ->

            attack as Attackable
            //2. 过滤 遭受攻击能力的物体
            // 攻击方的源头不可以是发射方
            // 攻击方不可以打受攻击方
            views.filter { (it is Sufferable) and (attack.owner != it) and (attack != it) }.forEach sufferTag@ { suffer ->

                suffer as Sufferable
                //3. 判断是否发生碰撞
                if (attack.isCollision(suffer)) {
                    //产生碰撞,找到碰撞者
                    //通知攻击者产生碰撞
                    attack.notifyAttack(suffer)

                    //通知被攻击者 产生碰撞
                    val sufferView = suffer.notifySuffer(attack)
                    sufferView?.let {
                        //显示挨打的效果
                        views.addAll(sufferView)
                    }

                    return@sufferTag
                }
            }
        }

        //自动射击
        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val autoShot = it.autoShot()
            autoShot?.let {

                views.add(autoShot)
            }
        }

        endTime = System.currentTimeMillis()
        var time1 = endTime - startTime
        if (time1 > 200) {

            println("刷新一次耗时" + time1)
        }

        //如果大本营为空。或者
        if ((views.filter { it is Camp }.isEmpty()) or (enemyTotalSize <= 0)) {
            gameOver = true
        }

        //动态添加敌方坦克
        if ((enemyTotalSize > 0) and (views.filter { it is Enemy }.size < enemyActiveSize)) {
            var index = bornIndex % enemyBornLocation.size
            var pair = enemyBornLocation[index]
            views.add(Enemy(pair.first, pair.second))

            bornIndex++
        }
    }

}