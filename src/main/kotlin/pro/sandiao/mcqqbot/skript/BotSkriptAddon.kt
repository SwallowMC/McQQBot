package pro.sandiao.mcqqbot.skript

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import pro.sandiao.mcqqbot.McQQBotPlugin

class BotSkriptAddon {

    val skriptAddon: SkriptAddon = Skript.registerAddon(McQQBotPlugin.plugin)

    init {
        skriptAddon.loadClasses("pro.sandiao.mcqqbot.skript", "event", "effect", "expressions")
        McQQBotPlugin.logger.info { "Hook Skript" }
    }
}