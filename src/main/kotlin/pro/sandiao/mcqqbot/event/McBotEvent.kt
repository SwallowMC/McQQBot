package pro.sandiao.mcqqbot.event

import net.mamoe.mirai.event.events.BotEvent
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

open class McBotEvent(val botEvent: BotEvent) : Event() {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val bot = botEvent.bot
    val botCode = bot.id
    val botNick = bot.nick

    companion object{
        @JvmStatic
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlers
        }
    }
}