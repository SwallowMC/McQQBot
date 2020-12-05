package pro.sandiao.mcqqbot.repeater;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.MessageEvent;

public class BotListener extends SimpleListenerHost {

    public BotListener(Bot bot) {
        Events.registerEvents(bot, this);
    }

    @EventHandler
    public ListeningStatus onMessage(MessageEvent event) {
        event.getSender().sendMessage(event.getMessage());
        return ListeningStatus.LISTENING;
    }
}
