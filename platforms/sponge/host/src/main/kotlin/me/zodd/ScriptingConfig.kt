package me.zodd

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class ScriptingConfig(
    @field:Comment("Set true for a placebo")
    val placeholderSetting: Boolean = false,
    @field:Comment("Enables debug logging")
    val extraLogging: Boolean = false,
    @field:Comment("Default imports to use for scripts")
    val defaultImports : List<String> = listOf(
        "org.spongepowered.api.*",
        "org.spongepowered.api.block.*",
        "org.spongepowered.api.command.*",
        "org.spongepowered.api.command.parameter.*",
        "org.spongepowered.api.command.parameter.managed.*",
        "org.spongepowered.api.data.*",
        "org.spongepowered.api.effect.particle.*",
        "org.spongepowered.api.effect.potion.*",
        "org.spongepowered.api.effect.sound.*",
        "org.spongepowered.api.effect.sound.music.*",
        "org.spongepowered.api.entity.*",
        "org.spongepowered.api.scheduler.*",
        "org.spongepowered.api.util.*",
        "org.apache.logging.log4j.Logger"
    )
)