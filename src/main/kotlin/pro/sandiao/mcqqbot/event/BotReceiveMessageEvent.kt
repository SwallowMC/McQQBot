package pro.sandiao.mcqqbot.event

import net.mamoe.mirai.message.MessageEvent
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pro.sandiao.mcqqbot.McQQBot

class BotReceiveMessageEvent(val bot: McQQBot, val messageEvent: MessageEvent) : Event() {

    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    companion object{
        @JvmStatic
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlers
        }
    }
}