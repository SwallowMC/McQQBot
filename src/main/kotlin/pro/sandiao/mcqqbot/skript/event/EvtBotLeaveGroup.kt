package pro.sandiao.mcqqbot.skript.event

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Name
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.event.McMemberLeaveEvent

@Name("Bot Leave Group Event")
@Description("机器人接收到老成员离开群")
class EvtBotLeaveGroup : SkriptEvent() {
    companion object {
        init {
            Skript.registerEvent("Bot Leave Group", EvtBotLeaveGroup::class.java, McMemberLeaveEvent::class.java, "bot leave group")
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "bot leave group"
    }

    override fun init(args: Array<out Literal<*>>?, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(e: Event?): Boolean {
        return e is McMemberLeaveEvent
    }
}