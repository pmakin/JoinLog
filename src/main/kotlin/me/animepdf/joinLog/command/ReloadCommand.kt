package me.animepdf.joinLog.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.animepdf.joinLog.JoinLogPlugin
import me.animepdf.joinLog.util.ConfigManager

class ReloadCommand(val plugin: JoinLogPlugin) {
    fun createCommand(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("reload")
            .requires { it.sender.hasPermission("joinlog.admin") }
            .executes { execute(it) }
    }

    private fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        plugin.config = ConfigManager.load(plugin.dataFolder.toPath(), "config.conf")
        ctx.source.sender.sendMessage("Config reloaded!")
        return 1
    }
}