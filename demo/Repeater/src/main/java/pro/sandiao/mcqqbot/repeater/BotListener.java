package pro.sandiao.mcqqbot.repeater;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.MessageEvent;
import pro.sandiao.mcqqbot.McQQBotPlugin;

public class BotListener extends SimpleListenerHost {

    public BotListener() {
        Events.registerEvents(McQQBotPlugin.INSTANCE.getMcQQBot().getBot(), this);
    }

    @EventHandler
    public ListeningStatus onMessage(MessageEvent event) {
        event.getSender().sendMessage(event.getMessage());
        return ListeningStatus.LISTENING;
    }
}
