package pro.sandiao.mcqqbot.util

import io.izzel.taboolib.util.Files
import net.mamoe.mirai.Bot
import net.mamoe.mirai.utils.LoginSolver
import pro.sandiao.mcqqbot.McQQBotPlugin
import java.io.File

object MinecraftLoginSolver : LoginSolver() {

    private var isWaitVerify = false
    private var value : String? = null

    fun setCommandValue(value: String?) : Boolean {
        return if (isWaitVerify) {
            this.value = value
            isWaitVerify = false
            true
        } else false
    }

    private fun getCommandValue() : String? {
        isWaitVerify = true
        while (value == null){
            Thread.sleep(5L)
        }
        val v = value
        value = null
        return v
    }

    override suspend fun onSolvePicCaptcha(bot: Bot, data: ByteArray): String? {
        val file = File(McQQBotPlugin.dataFolder, "captcha.png")
        Files.toFile(data, file)
        bot.logger.info("已将二维码图片保存至 ${file.absolutePath}")
        bot.logger.info("账号需要进行验证码验证 请自行查看验证码")
        bot.logger.info("请使用命令/mcqqbot verify <captcha>进行验证")
        val value = getCommandValue()
        file.delete()
        bot.logger.info("你输入的验证码为 $value, 已为你删除二维码图片")
        return value
    }

    override suspend fun onSolveSliderCaptcha(bot: Bot, url: String): String? {
        bot.logger.info("账号需要进行验证码验证 请打开链接 $url 进行验证")
        bot.logger.info("验证完成后使用命令/mcqqbot verify 进行登录")
        return getCommandValue()
    }

    override suspend fun onSolveUnsafeDeviceLoginVerify(bot: Bot, url: String): String? {
        bot.logger.info("账号需要进行设备验证 请打开链接 $url 进行验证")
        bot.logger.info("验证完成后使用命令/mcqqbot verify 进行登录")
        return getCommandValue()
    }
}