package pro.sandiao.mcqqbot

import kotlinx.coroutines.async
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.TempMessageEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.BotConfiguration
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pro.sandiao.mcqqbot.event.McBotEvent
import pro.sandiao.mcqqbot.event.McMemberJoinRequestEvent
import pro.sandiao.mcqqbot.event.McNewFriendRequestEvent
import pro.sandiao.mcqqbot.event.bot.McBotNickChangedEvent
import pro.sandiao.mcqqbot.event.bot.McBotOnlineEvent
import pro.sandiao.mcqqbot.event.message.McFriendMessageEvent
import pro.sandiao.mcqqbot.event.message.McGroupMessageEvent
import pro.sandiao.mcqqbot.event.message.McMessageEvent
import pro.sandiao.mcqqbot.event.message.McTempMessageEvent

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
        this.subscribeAlways<BotOnlineEvent> {
            callEvent(McBotOnlineEvent(this))
        }
        this.subscribeAlways<BotNickChangedEvent> {
            callEvent(McBotNickChangedEvent(this))
        }
        this.subscribeAlways<MemberJoinRequestEvent> {
            callEvent(McMemberJoinRequestEvent(this))
        }
        this.subscribeAlways<NewFriendRequestEvent> {
            callEvent(McNewFriendRequestEvent(this))
        }
        this.subscribeAlways<FriendMessageEvent> {
            callEvent(McFriendMessageEvent(this))
        }
        this.subscribeAlways<GroupMessageEvent> {
            callEvent(McGroupMessageEvent(this))
        }
        this.subscribeAlways<TempMessageEvent> {
            callEvent(McTempMessageEvent(this))
        }
        this.subscribeAlways<MessageEvent> {
            callEvent(McMessageEvent(this))
        }
    }

    fun callEvent(event: McBotEvent){
        object: BukkitRunnable(){
            override fun run() {
                Bukkit.getPluginManager().callEvent(event);
            }
        }.runTask(McQQBotPlugin.plugin)
    }

    /**
     * 发送消息给好友
     *
     * @param friend QQ号
     * @param message 信息
     */
    fun sendMessageToFriend(friend: Long, message: String) {
        sendMessageToFriend(friend, PlainText(message))
    }

    /**
     * 发送消息给好友
     *
     * @param friend QQ号
     * @param message 信息
     */
    fun sendMessageToFriend(friend: Long, message: Message) {
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