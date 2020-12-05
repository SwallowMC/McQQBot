package pro.sandiao.mcqqbot.logger

import net.mamoe.mirai.utils.DirectoryLogger
import net.mamoe.mirai.utils.MiraiLogger
import net.mamoe.mirai.utils.SilentLogger
import net.mamoe.mirai.utils.monthsToMillis
import pro.sandiao.mcqqbot.McQQBotPlugin
import java.io.File

enum class LogType {
    CONSOLE,
    FILE,
    NONE;

    fun getLogger(identity : String) : MiraiLogger{
        return when (this) {
            CONSOLE -> ConsoleLogger(identity)
            FILE -> DirectoryLogger(identity, File(McQQBotPlugin.dataFolder, "logs"), 1.monthsToMillis)
            NONE -> SilentLogger
        }
    }
}