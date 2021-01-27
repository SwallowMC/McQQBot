package pro.sandiao.mcqqbot.event.message

import net.mamoe.mirai.event.events.GroupTempMessageEvent
import org.bukkit.event.HandlerList

class McTempMessageEvent(tempMessageEvent: GroupTempMessageEvent) : McMessageEvent(tempMessageEvent) {
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