package me.animepdf.joinLog.util

import org.bukkit.entity.Player

object HeadsHelper {
    fun getPlayerHeadUrl(player: Player, hasSkinsRestorer: Boolean): String {
        var skinName = player.name

        if (hasSkinsRestorer) {
            try {
                val identifier = fetchSkinsRestorerName(player)
                if (!identifier.startsWith("http")) {
                    skinName = identifier
                }
            } catch (e: Throwable) {
            }
        }

        return "https://mc-heads.net/avatar/$skinName/128"
    }

    private fun fetchSkinsRestorerName(player: Player): String {
        val srApi = net.skinsrestorer.api.SkinsRestorerProvider.get()
        val skinId = srApi.playerStorage.getSkinIdOfPlayer(player.uniqueId).orElse(null)
        return skinId?.identifier ?: player.name
    }
}