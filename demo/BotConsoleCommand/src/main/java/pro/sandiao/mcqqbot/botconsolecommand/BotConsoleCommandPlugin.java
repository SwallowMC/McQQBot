package pro.sandiao.mcqqbot.botconsolecommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pro.sandiao.mcqqbot.event.message.McFriendMessageEvent;

public class BotConsoleCommandPlugin extends JavaPlugin implements Listener {

    private List<Long> adminQQList = new ArrayList<>();

    @Override
    public void onEnable() {
        getLogger().info("插件 BotConsoleCommand 正在启动");
        saveDefaultConfig();
        if (getConfig().isList("admin-qq")) {
            getConfig().getLongList("admin-qq").forEach(adminQQList::add);
        } else {
            adminQQList.add(getConfig().getLong("admin-qq"));
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("插件 BotConsoleCommand 正在停止");
    }

    @EventHandler
    public void onFriendMessage(McFriendMessageEvent event) {
        // 如果发信息的是管理员
        if (!adminQQList.contains(event.getSenderCode()))
            return;

        // 如果信息开头是 /
        String message = event.getMessageContent();
        if (!message.startsWith("/"))
            return;

        // 获得命令
        message = message.substring(1);

        // 创建一个新的流获取控制台内容
        PrintStream oldps = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newps = null;
        try {
            newps = new SystemPrintStream(baos, oldps);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.setOut(newps);

        // 执行命令
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);

        // 读取获取的内容
        String result = null;
        try {
            result = baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.setOut(oldps);
        newps.close();

        // 消除颜色
        result = result.replaceAll("\33\\[\\d+;\\d+;\\d+m", "").replaceAll("\33\\[m", "");
        
        // 机器人回复
        event.reply("命令 " + message + " 执行结果如下: \n" + result);
    }
}
