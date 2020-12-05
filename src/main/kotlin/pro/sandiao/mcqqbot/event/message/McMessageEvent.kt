package pro.sandiao.mcqqbot.event.message

import kotlinx.coroutines.async
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import org.bukkit.event.HandlerList
import pro.sandiao.mcqqbot.event.McBotEvent

open class McMessageEvent(val messageEvent: MessageEvent) : McBotEvent(messageEvent) {
    override fun getHandlers(): HandlerList {
        return getHandlerList()!!
    }

    val sender = messageEvent.sender
    val senderCode = sender.id
    val message = messageEvent.message
    val messageContent = messageEvent.message.contentToString()
    val senderName = messageEvent.senderName

    fun reply(message: String) = reply(PlainText(message))
    fun reply(message: Message){
        bot.async {
            messageEvent.reply(message)
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