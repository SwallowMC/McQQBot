package pro.sandiao.mcqqbot.event

import kotlinx.coroutines.async
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import org.bukkit.event.HandlerList

class McNewFriendRequestEvent(val newFriendRequestEvent: NewFriendRequestEvent) : McBotEvent(newFriendRequestEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val eventId = newFriendRequestEvent.eventId
    val message = newFriendRequestEvent.message
    val userCode = newFriendRequestEvent.fromId
    val userNick = newFriendRequestEvent.fromNick

    fun reject(blackList: Boolean = false){
        bot.async {
            newFriendRequestEvent.reject(blackList)
        }
    }

    fun accept(){
        bot.async {
            newFriendRequestEvent.accept()
        }
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