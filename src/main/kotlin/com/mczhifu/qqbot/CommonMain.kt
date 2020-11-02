package com.mczhifu.qqbot

import com.mczhifu.mczf.command.CommandBuilder
import com.mczhifu.mczf.config.ConfigManager
import com.mczhifu.mczf.enumerate.WebAPIType
import com.mczhifu.mczf.json.JSONObject
import com.mczhifu.mczf.message.Message
import com.typesafe.config.ConfigException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

lateinit var configManager: ConfigManager
lateinit var mczfBot: MczfBot
lateinit var kryptonGoldConfig: KryptonGoldConfig
var qqGroup: Long = 0L

object CommonMain {

    /**
     * 插件启动
     */
    fun onStart(){
        loadConfig()

        //添加子命令
        CommandBuilder.getRootCommand().addSubCommand(Command())

        var json = WebAPIType.GET_QQUN.getWebAPI()
        if (json == null){
            Message.set("获取QQ群号失败").consoleMessage()
            Message.set("请前往www.mczhifu.com设置QQ群号").consoleMessage()
            Message.set("如果以设置群号仍然无法获取 请检测网络").consoleMessage()
        }

        qqGroup = json.getJSONObject("result").getLong("qqun")
        Message.set("你的QQ群号为: $qqGroup").consoleMessage()

        //登录机器人
        loginBot()
    }

    fun reload(){
        loadConfig()

        var json = WebAPIType.GET_QQUN.getWebAPI()
        if (json == null){
            Message.set("获取QQ群号失败").consoleMessage()
            Message.set("请前往www.mczhifu.com设置QQ群号").consoleMessage()
            Message.set("如果以设置群号仍然无法获取 请检测网络").consoleMessage()
        }

        qqGroup = json.getJSONObject("result").getLong("qqun")
        Message.set("你的QQ群号为: $qqGroup").consoleMessage()

        mczfBot.bot.close()

        loginBot()
    }

    /**
     * 登录机器人
     */
    fun loginBot(){
        var qqconfig = configManager.allConfig["qqconfig"]
        var qqCode = qqconfig?.getString("qq-code")
        var qqPassword = qqconfig?.getString("qq-password")

        if (qqCode.isNullOrBlank() || qqPassword.isNullOrBlank()){
            Message.set("机器人QQ账号与密码未设置").consoleMessage()
            Message.set("请前往配置文件 qqconfig.yml 进行设置").consoleMessage()
            Message.set("设置完成后 可使用 /mczf qqbot reload 进行重载").consoleMessage()
            return
        }

        mczfBot = MczfBot(qqCode.toLong(), qqPassword, File(configManager.configFolder, "device.json"))

        var config = configManager.allConfig["config"]

        //读取自定义回复
        mczfBot.replyMap.clear()
        var customizeReply = config?.getConfiguration("qq-group")?.getConfiguration("customize-reply")
        for (key in customizeReply?.keys!!){
            var reply = customizeReply.getString("$key.message")
            customizeReply.getStringList("$key.identifier").forEach {
                mczfBot.replyMap[it] = reply
            }
        }

        GlobalScope.async {
            mczfBot.login()
        }
    }

    /**
     * 加载配置文件
     */
    private fun loadConfig(){
        var configFile = File(configManager.configFolder,"config.yml")
        var qqConfigFile = File(configManager.configFolder,"qqconfig.yml")

        createFile(configFile)
        createFile(qqConfigFile)

        configManager.loadConfig(configFile)
        configManager.loadConfig(qqConfigFile)

        kryptonGoldConfig = KryptonGoldConfig()
    }

    /**
     * 创建配置文件
     */
    private fun createFile(file: File) {
        if (!file.exists()) {
            try {
                file.createNewFile()
                var ins = javaClass.classLoader.getResourceAsStream(file.name)
                var out = FileOutputStream(file);
                var buf = ByteArray(1024);
                var len: Int = 0;
                while (ins.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                ins.close();
            } catch (e:IOException) {
                e.printStackTrace();
            }
        }
    }
}
