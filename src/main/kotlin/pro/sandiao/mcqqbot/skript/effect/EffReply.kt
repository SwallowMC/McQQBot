package pro.sandiao.mcqqbot.skript.effect

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.event.McMemberJoinEvent
import pro.sandiao.mcqqbot.event.McMemberLeaveEvent
import pro.sandiao.mcqqbot.event.message.McMessageEvent

class EffReply : Effect() {

    lateinit var greetings: Expression<String>;

    companion object {
        init {
            Skript.registerEffect(EffReply::class.java, "[bot] reply %string%")
        }
    }

    override fun init(args: Array<out Expression<*>>?, matchedPattern: Int, kleenean: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        greetings = args!![0] as Expression<String>
        return true
    }

    override fun execute(e: Event?) {
        if (e is McMessageEvent || e is McMemberJoinEvent || e is McMemberLeaveEvent)
            e.javaClass.getMethod("reply", String::class.java).invoke(e, greetings.getSingle(e)!!)
        else
            Skript.error("bot reply 不能在此使用")
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "bot reply"
    }
}