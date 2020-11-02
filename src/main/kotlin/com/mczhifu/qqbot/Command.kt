package com.mczhifu.qqbot

import com.mczhifu.mczf.command.SubCommand
import com.mczhifu.mczf.message.Message
import com.mczhifu.mczf.pack.PlayerPack
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

/**
 * Sub Command
 * command: mczf qqbot
 */
class Command : SubCommand("qqbot", "mczf.qqbot") {

    init {
        subCommands.add(ReloadSubCommand())
        subCommands.add(SendSubCommand())
    }

    /**
     * on Command
     */
    override fun execute(sender: Any?): Boolean {
        return true
    }
}

/**
 * Sub Sub Command
 * command: mczf qqbot reload
 * 重载机器人
 */
class ReloadSubCommand : SubCommand("reload", "mczf.qqbot.reload"){

    /**
     * on Command
     */
    override fun execute(sender: Any?): Boolean {
        CommonMain.reload()
        return true
    }
}

/**
 * Sub Sub Command
 * command: mczf qqbot send [message]
 * 让机器人逼逼一句
 */
class SendSubCommand : SubCommand("send", "mczf.qqbot.send"){
    override fun execute(sender: Any?): Boolean {
        return true
    }

    override fun onCommand(sender: Any?, vararg args: String?) {
        if (args.isNotEmpty()){
            var str = ""
            for (s in args){
                str += "$s "
            }
            GlobalScope.async {
                mczfBot.sendMessageToGroup(qqGroup, str)
            }
        }else{
            Message.set("使用方法: /mczf qqbot send [信息]").sendMessage(sender)
        }
    }
}