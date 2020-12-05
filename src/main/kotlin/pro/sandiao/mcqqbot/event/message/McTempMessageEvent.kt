package pro.sandiao.mcqqbot.event.message

import net.mamoe.mirai.message.TempMessageEvent
import org.bukkit.event.HandlerList

class McTempMessageEvent(tempMessageEvent: TempMessageEvent) : McMessageEvent(tempMessageEvent) {
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