package pro.sandiao.mcqqbot.command

import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.SubCommand
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
}