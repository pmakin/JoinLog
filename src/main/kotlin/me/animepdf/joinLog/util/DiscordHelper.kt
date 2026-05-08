package me.animepdf.joinLog.util

import me.animepdf.joinLog.JoinLogPlugin
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class DiscordHelper(
    val plugin: JoinLogPlugin
) {
    fun sendEmbedAsync(actionText: String, headUrl: String, colorDecimal: Int) {
        plugin.server.asyncScheduler.runNow(plugin) { _ ->
            try {
                val jsonPayload = """
                {
                  "embeds": [
                    {
                      "author": {
                        "name": "$actionText",
                        "icon_url": "$headUrl"
                      },
                      "color": $colorDecimal
                    }
                  ]
                }
                """.trimIndent()

                val request = HttpRequest.newBuilder()
                    .uri(URI.create(plugin.config.webhookUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build()

                val response = plugin.httpClient.send(request, HttpResponse.BodyHandlers.ofString())

                if (response.statusCode() !in 200..299) {
                    plugin.logger.warning("Failed to send webhook. Discord HTTP ${response.statusCode()}: ${response.body()}")
                }
            } catch (e: Exception) {
                plugin.logger.warning("Error sending webhook: ${e.message}")
            }
        }
    }
}