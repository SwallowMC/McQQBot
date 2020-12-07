package pro.sandiao.mcqqbot.command

import io.izzel.taboolib.module.command.base.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import pro.sandiao.mcqqbot.McQQBotPlugin
import pro.sandiao.mcqqbot.util.MinecraftLoginSolver

@BaseCommand(name = "mcqqbot", aliases = ["qqbot"], permission = "mcqqbot.command")
class Commands : BaseMainCommand() {

    @SubCommand(description = "重新登录机器人", permission = "mcqqbot.command.relogin")
    fun relogin(sender: CommandSender, args: Array<out String>){
        McQQBotPlugin.loadPlugin(sender)
    }

    @SubCommand(description = "机器人新设备登录时完成验证", arguments = ["captcha?"], permission = "mcqqbot.command.verify")
    fun verify(sender: CommandSender, args: Array<out String>) {
        if (MinecraftLoginSolver.setCommandValue(if (args.isNotEmpty()) args[0] else ""))
            sender.sendMessage("验证中...")
        else
            sender.sendMessage("现在不需要验证哦!")
    }

    @SubCommand(description = "机器人发送消息", permission = "mcqqbot.command.send")
    val send = object : BaseSubCommand(){
        override fun getArguments(): Array<Argument> {
            return arrayOf(
                    Argument("group/friend") { listOf("group", "friend") },
                    Argument("code") {McQQBotPlugin.mcQQBot.getGroups().map { it.id.toString() }},
                    Argument("message...")
            )
        }
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) {
            val code: Long
            try {
                code = args[1].toLong()
            } catch (e: NumberFormatException) {
                sender.sendMessage("请输入正确的code(群号/QQ号)")
                return
            }
            val stringBuilder = StringBuilder()
            args.forEachIndexed {i, s ->
                if (i > 1) {
                    stringBuilder.append(s)
                    stringBuilder.append(' ')
                }
            }
            when (args[0].toLowerCase()){
                "group" -> McQQBotPlugin.mcQQBot.sendMessageToGroup(code, stringBuilder.toString())
                "friend" -> McQQBotPlugin.mcQQBot.sendMessageToFriend(code, stringBuilder.toString())
                else -> sender.sendMessage("发送类型只能为group(发送给群)和friend(发送给好友)")
            }
        }
    }
}