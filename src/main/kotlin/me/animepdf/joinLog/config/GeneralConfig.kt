package me.animepdf.joinLog.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class GeneralConfig(
    val webhookUrl: String = "",

    val sendJoinMessage: Boolean = true,
    val sendLeaveMessage: Boolean = true,

    val joinMessage: String = "%username% подключился",
    val leaveMessage: String = "%username% отключился",

    @Comment("Use HEX to DECIMAL converter to get the number")
    val joinMessageColor: Int = 65280,
    val leaveMessageColor: Int = 16711680,
)
