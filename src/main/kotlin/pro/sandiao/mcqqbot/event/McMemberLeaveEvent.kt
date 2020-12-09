package pro.sandiao.mcqqbot.event

import kotlinx.coroutines.async
import net.mamoe.mirai.event.events.MemberLeaveEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import org.bukkit.event.HandlerList

class McMemberLeaveEvent(botEvent: MemberLeaveEvent) : McBotEvent(botEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val group = botEvent.group
    val groupCode = group.id
    val groupName = group.name
    val member = botEvent.member
    val memberCode = member.id
    val memberName = member.nick

    fun reply(message: String) = reply(PlainText(message))
    fun reply(message: Message){
        bot.async {
            group.sendMessage(message)
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