package pro.sandiao.mcqqbot.autoaddgroup

import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.data.At
import org.bukkit.plugin.java.JavaPlugin
import pro.sandiao.mcqqbot.McQQBotPlugin

class AutoAddGroupPlugin : JavaPlugin() {
    override fun onEnable() {
        logger.info { "插件 $name 正在启动" }
        val bot = McQQBotPlugin.mcQQBot!!.bot

        // 入群申请事件
        bot.subscribeAlways<MemberJoinRequestEvent> {
            // 同意
            accept()
            group.sendMessage(At(group[fromId]) + "欢迎加入该群")
        }
    }

    override fun onDisable() {
        logger.info { "插件 $name 正在停止" }
    }
}