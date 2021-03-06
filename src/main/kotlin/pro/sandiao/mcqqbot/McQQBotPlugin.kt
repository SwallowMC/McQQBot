package pro.sandiao.mcqqbot

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.dependency.Dependencies
import io.izzel.taboolib.module.dependency.Dependency
import io.izzel.taboolib.module.dependency.TDependency
import io.izzel.taboolib.module.inject.TInject
import net.mamoe.mirai.utils.BotConfiguration
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import pro.sandiao.mcqqbot.logger.LogType
import pro.sandiao.mcqqbot.skript.BotSkriptAddon
import pro.sandiao.mcqqbot.util.MinecraftLoginSolver
import java.io.File
import java.util.logging.Logger

private const val MIRAI_REPO = "https://dl.bintray.com/him188moe/mirai/"
private const val KOTLIN_REPO = "https://kotlin.bintray.com/kotlinx/"

@Dependencies(
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.0.1", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.0.1", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-io:0.1.16", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-io-jvm:0.1.16", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "org.jetbrains.kotlinx:kotlinx-serialization-protobuf-jvm:1.0.1", mavenRepo = KOTLIN_REPO),
        Dependency(maven = "io.ktor:ktor-client-cio-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-client-core-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-http-cio-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-utils-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-network-tls-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-io-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "io.ktor:ktor-network-jvm:1.4.1", mavenRepo = TDependency.MAVEN_REPO),
        Dependency(maven = "net.mamoe:mirai-core-api:2.1.1", mavenRepo = MIRAI_REPO),
        Dependency(maven = "net.mamoe:mirai-core:2.1.1", mavenRepo = MIRAI_REPO)
)

object McQQBotPlugin : Plugin() {

    val logger: Logger = plugin.logger
    val dataFolder: File = plugin.dataFolder
    lateinit var mcQQBot: McQQBot
    var botSkriptAddon: BotSkriptAddon? = null

    @TInject("config.yml")
    lateinit var config : TConfig

    override fun onEnable() {
        logger.info { "搭载超级硬核的 Mirai 2.1.1 的QQ机器人插件正在启动" }

        if (Bukkit.getPluginManager().getPlugin("Skript") != null){
            botSkriptAddon = BotSkriptAddon()
        }

        loadPlugin(Bukkit.getConsoleSender())
    }

    override fun onDisable() {
        logger.info { "搭载超级硬核的 Mirai 2.1.1 的QQ机器人插件正在停止" }
    }

    fun loadPlugin(sender: CommandSender){
        val loginType = BotConfiguration.MiraiProtocol.valueOf(config.getString("global.login-type")!!)
        val loggerType = LogType.valueOf(config.getString("global.logger-type")!!)
        val botConfig = BotConfiguration()

        botConfig.fileBasedDeviceInfo(File(dataFolder, "device.json").absolutePath)
        botConfig.botLoggerSupplier = { loggerType.getLogger("账号 (${it.id})") }
        botConfig.networkLoggerSupplier = { loggerType.getLogger("网络 (${it.id})") }
        botConfig.loginSolver = MinecraftLoginSolver
        botConfig.protocol = loginType

        mcQQBot = McQQBot(0L, "", botConfig)

        mcQQBot.close()

        val qqcode = config.getLong("bot.qq-code")
        val qqpassword = config.getString("bot.qq-password")

        if (qqcode == 0L || qqpassword.isNullOrBlank()){
            sender.sendMessage("[${plugin.name}] 请在配置文件中设置好机器人账号密码后使用/mcqqbot relogin 重新登录")
            return
        }

        mcQQBot = McQQBot(qqcode, qqpassword, botConfig)

        mcQQBot.login()
    }
}