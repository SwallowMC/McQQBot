package pro.sandiao.mcqqbot.event.bot

import net.mamoe.mirai.event.events.BotNickChangedEvent
import org.bukkit.event.HandlerList
import pro.sandiao.mcqqbot.event.McBotEvent

class McBotNickChangedEvent(botNickChangedEvent: BotNickChangedEvent) : McBotEvent(botNickChangedEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val oldNick = botNickChangedEvent.from
    val newNick = botNickChangedEvent.to

    companion object{
        @JvmStatic
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlers
        }
    }
}