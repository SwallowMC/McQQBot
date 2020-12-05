package pro.sandiao.mcqqbot.skript.event

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Name
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.event.message.McMessageEvent


@Name("Bot Message Event")
@Description("机器人接收到消息的事件")
class EvtBotMessage : SkriptEvent() {

    companion object {
        init {
            Skript.registerEvent("Bot Message", EvtBotMessage::class.java, McMessageEvent::class.java, "bot message")
        }
    }

    override fun init(p0: Array<out Literal<*>>?, p1: Int, p2: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(event: Event?): Boolean {
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "bot message"
    }
}