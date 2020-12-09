package pro.sandiao.mcqqbot.skript.event

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Name
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.event.McMemberJoinEvent

@Name("Bot Join Group Event")
@Description("机器人接收到新成员加入群")
class EvtBotJoinGroup : SkriptEvent() {
    companion object {
        init {
            Skript.registerEvent("Bot Join Group", EvtBotJoinGroup::class.java, McMemberJoinEvent::class.java, "bot join group")
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "bot join group"
    }

    override fun init(args: Array<out Literal<*>>?, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(e: Event?): Boolean {
        return e is McMemberJoinEvent
    }
}