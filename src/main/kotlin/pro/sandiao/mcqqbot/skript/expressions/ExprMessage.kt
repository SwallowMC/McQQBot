package pro.sandiao.mcqqbot.skript.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Events
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.event.message.McMessageEvent

@Events("bot message")
class ExprMessage : SimpleExpression<String>() {

    companion object {
        init {
            Skript.registerExpression(ExprMessage::class.java, String::class.java, ExpressionType.SIMPLE, "[the] bot( |-)message")
        }
    }

    override fun init(p0: Array<out Expression<*>>?, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun get(e: Event?): Array<String>? {
        if (e is McMessageEvent)
            return arrayOf(e.messageContent)
        return arrayOf()
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(p0: Event?, p1: Boolean): String {
        return "the bot message";
    }
}