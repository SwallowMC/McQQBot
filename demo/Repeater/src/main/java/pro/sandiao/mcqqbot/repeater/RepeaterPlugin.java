package pro.sandiao.mcqqbot.repeater;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Mirai 方式注册监听器 (各种事件更加全面)
 */
public class RepeaterPlugin extends JavaPlugin{

    @Override
    public void onEnable() {
        getLogger().info("插件 Repeater 正在启动");
        new BotListener();
    }

    @Override
    public void onDisable() {
        getLogger().info("插件 Repeater 正在停止");
    }
}
