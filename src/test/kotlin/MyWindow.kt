import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class MyWindow : Window() {

    override fun onCreate() {
    }

    override fun onDisplay() {
        //窗体渲染时候的回调

    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.ENTER -> println("按下回车")
        }
    }

    override fun onRefresh() {
    }
}

fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}