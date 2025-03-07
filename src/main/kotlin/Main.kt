@file:JvmName("Main")

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.dxmwl.newbee.BuildConfig
import com.dxmwl.newbee.log.AppLogger
import com.dxmwl.newbee.log.CrashHandler
import com.dxmwl.newbee.page.AppNavigation
import com.dxmwl.newbee.widget.ConfirmDialog
import com.dxmwl.newbee.widget.RootWindow

fun main() {
    CrashHandler.install()
    AppLogger.info("main", "App启动")
    BuildConfig.print()
    application {
        var exitDialog by remember { mutableStateOf(false) }
        Window(
            title = BuildConfig.appName,
            icon = painterResource(BuildConfig.ICON),
            resizable = false,
            transparent = true,
            undecorated = true,
            state = rememberWindowState(
                width = 1280.dp, height = 960.dp,
                position = WindowPosition(Alignment.Center)
            ),
            onCloseRequest = {
                exitDialog = true
            }
        ) {
            RootWindow(closeClick = { exitDialog = true }) {
                AppNavigation()
                if (exitDialog) {
                    ConfirmDialog("确定退出软件吗？",
                        onConfirm = {
                            exitDialog = false
                            AppLogger.info("main", "App关闭")
                            exitApplication()
                        }, onDismiss = {
                            exitDialog = false
                        })
                }
            }
        }
    }
}
