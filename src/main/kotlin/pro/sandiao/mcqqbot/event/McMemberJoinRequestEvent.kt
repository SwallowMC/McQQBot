package pro.sandiao.mcqqbot.event

import kotlinx.coroutines.async
import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import org.bukkit.event.HandlerList

class McMemberJoinRequestEvent (val memberJoinRequestEvent : MemberJoinRequestEvent) : McBotEvent(memberJoinRequestEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val eventId = memberJoinRequestEvent.eventId
    val message = memberJoinRequestEvent.message
    val userCode = memberJoinRequestEvent.fromId
    val userNick = memberJoinRequestEvent.fromNick
    val groupCode = memberJoinRequestEvent.groupId
    val groupName = memberJoinRequestEvent.groupName

    fun reject(blackList: Boolean = false){
        bot.async {
            memberJoinRequestEvent.reject(blackList)
        }
    }

    fun accept(){
        bot.async {
            memberJoinRequestEvent.accept()
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