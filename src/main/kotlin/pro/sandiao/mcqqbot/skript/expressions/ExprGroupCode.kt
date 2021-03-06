package pro.sandiao.mcqqbot.skript.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import net.mamoe.mirai.event.events.GroupEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import org.bukkit.event.Event
import pro.sandiao.mcqqbot.McQQBot
import pro.sandiao.mcqqbot.event.McBotEvent
import pro.sandiao.mcqqbot.event.McMemberJoinEvent
import pro.sandiao.mcqqbot.event.McMemberLeaveEvent
import pro.sandiao.mcqqbot.event.message.McMessageEvent

class ExprGroupCode : SimpleExpression<String>() {
    companion object {
        init {
            Skript.registerExpression(ExprGroupCode::class.java, String::class.java, ExpressionType.SIMPLE, "[the] group( |-)code")
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "this group code"
    }

    override fun init(exprs: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun get(e: Event?): Array<String>? {
        if (e is McBotEvent)
            if (e.botEvent is GroupEvent)
                return arrayOf(e.botEvent.group.id.toString())
        return arrayOf()
    }
}