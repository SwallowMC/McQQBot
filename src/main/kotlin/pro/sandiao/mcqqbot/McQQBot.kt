package pro.sandiao.mcqqbot

import kotlinx.coroutines.async
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.BotConfiguration
import org.bukkit.Bukkit
import pro.sandiao.mcqqbot.event.BotReceiveMessageEvent

class McQQBot(val qqcode: Long, val password: String, val botConfig: BotConfiguration) {
    var bot = Bot(qqcode, password, botConfig)

    fun isOnline() = bot.isOnline
    fun getGroups() = bot.groups
    fun getFriends() = bot.friends

    fun close() = bot.close()

    init {
        bot.messageListener()
    }

    private fun Bot.messageListener() {
        this.subscribeMessages {
            always {
                var event = BotReceiveMessageEvent(this@McQQBot, this)
                Bukkit.getPluginManager().callEvent(event)
            }
        }
    }

    /**
     * 发送消息给好友
     *
     * @param friend QQ号
     * @param message 信息
     */
    fun sendMessageToFriend(friend: Long, message: String){
        sendMessageToFriend(friend, PlainText(message))
    }

    /**
     * 发送消息给好友
     *
     * @param friend QQ号
     * @param message 信息
     */
    fun sendMessageToFriend(friend: Long, message: Message){
        bot.async {
            if (bot.isOnline) bot.getFriend(friend).sendMessage(message)
        }
    }

    /**
     * 发送信息给群
     *
     * @param group 群号
     * @param message 信息
     */
    fun sendMessageToGroup(group: Long, message: String){
        sendMessageToGroup(group, PlainText(message))
    }

    /**
     * 发送信息给群
     *
     * @param group 群号
     * @param message 信息
     */
    fun sendMessageToGroup(group: Long, message: Message){
        bot.async {
            if (bot.isOnline) bot.getGroup(group).sendMessage(message)
        }
    }

    fun login(){
        bot.async {
            bot.login()
        }
    }
}