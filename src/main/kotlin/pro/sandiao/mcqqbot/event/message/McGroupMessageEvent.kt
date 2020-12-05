package pro.sandiao.mcqqbot.event.message

import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.GroupMessageEvent
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
    fun at() = reply(At(group[sender.id]))
    fun atReply(message: String) = reply(At(group[sender.id]) + message)
    fun atReply(message: Message) = reply(At(group[sender.id]) + message)

    companion object{
        @JvmStatic
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlers
        }
    }
}