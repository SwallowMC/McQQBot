package pro.sandiao.mcqqbot.event.message

import net.mamoe.mirai.event.events.FriendMessageEvent
import org.bukkit.event.HandlerList

class McFriendMessageEvent(friendMessageEvent: FriendMessageEvent) : McMessageEvent(friendMessageEvent) {
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