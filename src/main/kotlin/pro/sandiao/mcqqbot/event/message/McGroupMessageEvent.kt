package pro.sandiao.mcqqbot.event.message

import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.Message
import org.bukkit.event.HandlerList

class McGroupMessageEvent(groupMessageEvent: GroupMessageEvent) : McMessageEvent(groupMessageEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val group = groupMessageEvent.group
    val groupCode = group.id
    val groupName = group.name
    var muteAll = group.settings.isMuteAll

    fun atAll() = reply(AtAll)
    fun atAllReply(message: String) = reply(AtAll + message)
    fun atAllReply(message: Message) = reply(AtAll + message)
    fun at() = group[sender.id]?.let { At(it) }?.let { reply(it) }
    fun atReply(message: String) = group[sender.id]?.let { At(it) }?.plus(message)?.let { reply(it) }
    fun atReply(message: Message) = group[sender.id]?.let { At(it) }?.plus(message)?.let { reply(it) }

    companion object{
        @JvmStatic
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlers
        }
    }
}