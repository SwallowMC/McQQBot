package com.mczhifu.qqbot

import com.mczhifu.mczf.enumerate.PaywayType
import com.mczhifu.mczf.kit.Kit
import com.mczhifu.mczf.kit.KitManager
import com.mczhifu.mczf.pack.PlayerPack
import com.mczhifu.mczf.util.NetworkUtil
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.DefaultLogger
import net.mamoe.mirai.utils.SilentLogger
import net.mamoe.mirai.utils.SingleFileLogger
import java.awt.image.BufferedImage
import java.io.File

class MczfBot(var qqId: Long, var qqPassword: String, deviceFile: File) {

    lateinit var bot: Bot
    val replyMap = HashMap<String, String>()

    init {
        bot = Bot(qqId, qqPassword){
            fileBasedDeviceInfo(deviceFile.absolutePath)
            botLoggerSupplier = { bot ->
                var loggerType = configManager.allConfig["config"]?.getString("global.logger-type")
                if ("file".equals(loggerType, true)){
                    SingleFileLogger("QQ: " + bot.id, File(configManager.configFolder, "log.txt"))
                }else if ("console".equals(loggerType, true)){
                    DefaultLogger("QQ: " + bot.id)
                }else{
                    SilentLogger
                }
            }
            networkLoggerSupplier = { bot ->
                var loggerType = configManager.allConfig["config"]?.getString("global.logger-type")
                if ("file".equals(loggerType, true)){
                    SingleFileLogger("网络: " + bot.id, File(configManager.configFolder, "log.txt"))
                }else if ("console".equals(loggerType, true)){
                    DefaultLogger("网络: " + bot.id)
                }else{
                    SilentLogger
                }
            }
            protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH //手表登录 (到时候还可以用其它的机器人登录相同账号)
        }
        bot.messageListener();
    }

    /**
     * 消息监听器
     */
    private fun Bot.messageListener() {
        this.subscribeMessages {
            always {
                var msg = replyMap[message.contentToString()]
                if (!msg.isNullOrEmpty()){
                    reply(com.mczhifu.mczf.message.Message.set(msg)
                        .parse("%sender%", senderName)
                        .parse("%sender_qq%", sender.id.toString())
                        .parse("%message%", message.contentToString())
                        .parsePlaceholder(PlayerPack("这是一个不存在的玩家"))
                        .toString())
                }
            }
        }

        this.subscribeMessages {
            always {
                if (kryptonGoldConfig.enable!!){
                    var args = message.contentToString().split(' ')
                    if (args.isNotEmpty() && kryptonGoldConfig.identifier?.contains(args[0])!!){
                        if (args.size == 4){
                            var kit = KitManager.getKit(args[3])
                            if (kit == null) try {
                                var money = args[3].toInt()
                                var re = if (kryptonGoldConfig.qqidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.tencent_all, money.toDouble(), this)
                                }else if (kryptonGoldConfig.wxidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.wechat_all, money.toDouble(), this)
                                }else if (kryptonGoldConfig.zfbidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.alipay_all, money.toDouble(), this)
                                }else{
                                    reply(kryptonGoldConfig.usage!!)
                                    return@always
                                }
                                reply(re)
                                return@always
                            } catch (e: NumberFormatException) {

                            } else {
                                var re = if (kryptonGoldConfig.qqidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.tencent_all, kit, this)
                                }else if (kryptonGoldConfig.wxidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.wechat_all, kit, this)
                                }else if (kryptonGoldConfig.zfbidentifier?.contains(args[2])!!) {
                                    createOrder(args[1], PaywayType.alipay_all, kit, this)
                                }else{
                                    reply(kryptonGoldConfig.usage!!)
                                    return@always
                                }
                                reply(re)
                                return@always
                            }
                        }
                        reply(kryptonGoldConfig.usage!!)
                    }
                }
            }
        }
    }

    suspend fun createOrder(player:String, paywayType: PaywayType, money: Double, event: MessageEvent) : Message{
        var order: String? = NetworkUtil.createOrder(player, paywayType, money) ?: return PlainText("订单创建失败")
        var image: BufferedImage? = NetworkUtil.getQRCode(paywayType, order) ?: return PlainText("无法生成二维码")

        var imageMessage = event.uploadImage(image!!)

        var msg = PlainText("充值\n" +
                "玩家名: ${player}\n" +
                "充值金额: ${money}\n") +
                imageMessage + PlainText("\n" +
                "扫描二维码付款")

        return msg
    }

    suspend fun createOrder(player:String, paywayType: PaywayType, kit: Kit, event: MessageEvent) : Message{
        var order: String? = NetworkUtil.createOrder(player, paywayType, kit) ?: return PlainText("订单创建失败")
        var image: BufferedImage? = NetworkUtil.getQRCode(paywayType, order) ?: return PlainText("无法生成二维码")

        var imageMessage = event.uploadImage(image!!)

        var msg = PlainText("充值\n" +
                "玩家名: ${player}\n" +
                "充值金额: ${kit.money}\n" +
                "购买商品: ${kit.name}\n") +
                imageMessage + PlainText("\n" +
                "扫描二维码付款")

        return msg
    }

    /**
     * 发送消息给好友
     */
    suspend fun sendMessageToFriend(friend: Long, message: Message){
        bot.getFriend(friend).sendMessage(message)
    }

    /**
     * 发送消息给好友
     */
    suspend fun sendMessageToFriend(friend: Long, message: String){
        bot.getFriend(friend).sendMessage(message)
    }

    /**
     * 发送消息给群
     */
    suspend fun sendMessageToGroup(group: Long, message: Message){
        bot.getGroup(group).sendMessage(message)
    }

    /**
     * 发送消息给群
     */
    suspend fun sendMessageToGroup(group: Long, message: String){
        bot.getGroup(group).sendMessage(message)
    }

    /**
     * 登录账号
     *
     * @throws LoginFailedException 登录失败异常
     */
    suspend fun login(){
        bot.login()
    }
}