package pro.sandiao.mcqqbot.skript.effect

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.event.Event
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
        e as McMessageEvent
        e.reply(greetings.getSingle(e)!!)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "bot reply"
    }
}