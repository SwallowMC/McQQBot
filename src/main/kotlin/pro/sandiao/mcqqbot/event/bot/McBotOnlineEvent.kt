package pro.sandiao.mcqqbot.event.bot

import net.mamoe.mirai.event.events.BotOnlineEvent
import org.bukkit.event.HandlerList
import pro.sandiao.mcqqbot.event.McBotEvent

class McBotOnlineEvent(botOnlineEvent: BotOnlineEvent) : McBotEvent(botOnlineEvent) {
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