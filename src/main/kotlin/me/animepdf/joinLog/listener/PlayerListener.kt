package me.animepdf.joinLog.listener

import me.animepdf.joinLog.JoinLogPlugin
import me.animepdf.joinLog.util.HeadsHelper.getPlayerHeadUrl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(
    val plugin: JoinLogPlugin
) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!plugin.config.sendJoinMessage)
            return

        val headUrl = getPlayerHeadUrl(event.player, plugin.hasSkinsRestorer)
        val playerName = event.player.name
        val message = plugin.config.joinMessage.replace("%username%", playerName)

        plugin.discordHelper.sendEmbedAsync(message, headUrl, plugin.config.joinMessageColor)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (!plugin.config.sendLeaveMessage)
            return

        val headUrl = getPlayerHeadUrl(event.player, plugin.hasSkinsRestorer)
        val playerName = event.player.name
        val message = plugin.config.leaveMessage.replace("%username%", playerName)

        plugin.discordHelper.sendEmbedAsync(message, headUrl, plugin.config.leaveMessageColor)
    }
}