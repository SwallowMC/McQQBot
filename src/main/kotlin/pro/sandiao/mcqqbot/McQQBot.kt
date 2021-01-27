package pro.sandiao.mcqqbot

import kotlinx.coroutines.async
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.GroupTempMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.BotConfiguration
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pro.sandiao.mcqqbot.event.*
import pro.sandiao.mcqqbot.event.bot.McBotNickChangedEvent
import pro.sandiao.mcqqbot.event.bot.McBotOnlineEvent
import pro.sandiao.mcqqbot.event.message.McFriendMessageEvent
import pro.sandiao.mcqqbot.event.message.McGroupMessageEvent
import pro.sandiao.mcqqbot.event.message.McMessageEvent
import pro.sandiao.mcqqbot.event.message.McTempMessageEvent
import net.mamoe.mirai.message.data.Message
import kotlin.coroutines.EmptyCoroutineContext

class McQQBot(val qqcode: Long, val password: String, val botConfig: BotConfiguration) {
    var bot = BotFactory.newBot(qqcode, password, botConfig)

    fun isOnline() = bot.isOnline
    fun getGroups() = bot.groups
    fun getFriends() = bot.friends

    fun close() = bot.close()

    init {
        bot.messageListener()
    }

    private fun Bot.messageListener() {
        globalEventChannel().subscribeAlways(BotOnlineEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McBotOnlineEvent(this))
        }
        globalEventChannel().subscribeAlways(BotNickChangedEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McBotNickChangedEvent(this))
        }
        globalEventChannel().subscribeAlways(MemberJoinRequestEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McMemberJoinRequestEvent(this))
        }
        globalEventChannel().subscribeAlways(NewFriendRequestEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McNewFriendRequestEvent(this))
        }
        globalEventChannel().subscribeAlways(FriendMessageEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McFriendMessageEvent(this))
        }
        globalEventChannel().subscribeAlways(GroupMessageEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McGroupMessageEvent(this))
        }
        globalEventChannel().subscribeAlways(GroupTempMessageEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McTempMessageEvent(this))
        }
        globalEventChannel().subscribeAlways(MessageEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McMessageEvent(this))
        }
        globalEventChannel().subscribeAlways(MemberJoinEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McMemberJoinEvent(this))
        }
        globalEventChannel().subscribeAlways(MemberLeaveEvent::class, EmptyCoroutineContext, ConcurrencyKind.CONCURRENT) {
            callEvent(McMemberLeaveEvent(this))
        }

    }

    private fun callEvent(event: McBotEvent){
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
            if (bot.isOnline) {
                bot.getFriendOrFail(friend).sendMessage(message)
            }
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
            if (bot.isOnline) bot.getGroupOrFail(group).sendMessage(message)
        }
    }

    fun login(){
        bot.async {
            bot.login()
        }
    }
}