package pro.sandiao.mcqqbot.demo;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pro.sandiao.mcqqbot.event.bot.McBotOnlineEvent;
import pro.sandiao.mcqqbot.event.message.McMessageEvent;

public class HelloWorldPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("插件 HelloWorld 正在启动");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("插件 HelloWorld 正在停止");
    }

    @EventHandler
    public void onBotLogin(McBotOnlineEvent event){
        getLogger().info("机器人账号[" + event.getBotNick() + ":" + event.getBotCode() + "]已登录");
    }

    @EventHandler
    public void onGroupMessage(McMessageEvent event){
        if (event.getMessageContent().equals("hello"))
            event.reply("world");
    }
}
