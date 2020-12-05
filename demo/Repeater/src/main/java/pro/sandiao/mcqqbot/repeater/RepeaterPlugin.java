package pro.sandiao.mcqqbot.repeater;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pro.sandiao.mcqqbot.event.bot.McBotOnlineEvent;

/**
 * Mirai 方式注册监听器 (各种事件更加全面)
 */
public class RepeaterPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("插件 Repeater 正在启动");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件 Repeater 正在停止");
    }

    @EventHandler
    public void onBotLogin(McBotOnlineEvent event){
        new BotListener(event.getBot());
    }
}
