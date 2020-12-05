package pro.sandiao.mcqqbot.logger

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase
import pro.sandiao.mcqqbot.McQQBotPlugin
import java.util.logging.Logger
import java.util.logging.Level

/**
 * 控制台日志
 */
class ConsoleLogger(override val identity: String?) : MiraiLoggerPlatformBase() {

    private val logger : Logger = McQQBotPlugin.logger

    override fun debug0(message: String?, e: Throwable?) = logger.log(Level.INFO, "$identity: $message", e)
    override fun error0(message: String?, e: Throwable?) = logger.log(Level.SEVERE, "$identity: $message", e)
    override fun info0(message: String?, e: Throwable?) = logger.log(Level.INFO, "$identity: $message", e)
    override fun verbose0(message: String?, e: Throwable?) = logger.log(Level.INFO, "$identity: $message", e)
    override fun warning0(message: String?, e: Throwable?) = logger.log(Level.WARNING, "$identity: $message", e)
}