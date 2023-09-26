package me.zodd

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "plugin.kts",
    compilationConfiguration = ScriptConfiguration::class
)
abstract class PluginScript

object ScriptConfiguration : ScriptCompilationConfiguration({
    ide.acceptedLocations(ScriptAcceptedLocation.Everywhere)
    compilerOptions("-jvm-target", "17")
    jvm {
        dependenciesFromCurrentContext(
            wholeClasspath = true
        )
    }
})

