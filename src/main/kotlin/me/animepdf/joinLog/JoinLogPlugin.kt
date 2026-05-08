package me.animepdf.joinLog

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.animepdf.joinLog.command.ReloadCommand
import me.animepdf.joinLog.config.GeneralConfig
import me.animepdf.joinLog.listener.PlayerListener
import me.animepdf.joinLog.util.ConfigManager
import me.animepdf.joinLog.util.DiscordHelper
import org.bukkit.plugin.java.JavaPlugin
import java.net.http.HttpClient

class JoinLogPlugin : JavaPlugin() {

    lateinit var config: GeneralConfig
    val httpClient: HttpClient = HttpClient.newHttpClient()
    var hasSkinsRestorer: Boolean = false
    val discordHelper: DiscordHelper = DiscordHelper(this)

    override fun onEnable() {
        try {
            loadConfig()
            hookSkinsRestorer()
            registerCommands()
            registerListeners()

            logger.info("JoinLog enabled successfully")
        } catch (e: Exception) {
            logger.severe("Failed to enable plugin: ${e.message}")
            e.printStackTrace()
            server.pluginManager.disablePlugin(this)
        }
    }

    override fun onDisable() {
        logger.info("JoinLog disabled successfully")
    }

    private fun loadConfig() {
        try {
            config = ConfigManager.load(dataFolder.toPath(), "config.conf")
            logger.info("Config loaded successfully")
        } catch (e: Exception) {
            logger.severe("Failed to load configuration")
            throw e
        }
    }

    private fun hookSkinsRestorer() {
        try {
            if (server.pluginManager.getPlugin("SkinsRestorer") != null) {
                hasSkinsRestorer = true
                logger.info("Hooked into SkinsRestorer! Custom skins will be shown in Discord.")
            }
        } catch (e: Exception) {
            logger.severe("Failed to hook SkinsRestorer")
            throw e
        }
    }

    private fun registerListeners() {
        try {
            server.pluginManager.registerEvents(PlayerListener(this), this)
        } catch (e: Exception) {
            logger.severe("Failed to register listeners")
            throw e
        }
    }

    private fun registerCommands() {
        try {
            lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
                val root = Commands.literal("joinlog")

                val reload = ReloadCommand(this).createCommand()
                root.then(reload)

                it.registrar().register(root.build())
            }
        } catch (e: Exception) {
            logger.severe("Failed to register commands")
            throw e
        }
    }
}
