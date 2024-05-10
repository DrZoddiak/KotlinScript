# KotlinScript

KotlinScript is a Scripting Host for Kotlin scripts.

This means that Kotlin style scripts can make use of a platforms API (Paper/Sponge).

### [Wanting to get started? Read our wiki!](https://github.com/DrZoddiak/KotlinScript/wiki)

## What does a script look like?

A script simply needs the file extension of `.plugin.kts`
You can find many example scripts below

[Sponge](https://github.com/DrZoddiak/KotlinScript/tree/master/host/run/config/scripting-host/scripts)

[Paper]()

A simple example
```kotlin 
import me.zodd.*
import net.kyori.adventure.text.Component

//Without context scripts will execute during construction
Logger.info("Hello Plugin Construction Event")

//Wrapper functions are provided to register events
onServerStarted {
    Logger.info("Hello Server Started Event")
}
```

## How could I possibly script without any auto-completion!?
No need fear! While you can script with a simple text editor
You may find that text completion and code suggestions is much easier!

All you need to do is [copy the KotlinScriptingExampleRepo](https://github.com/DrZoddiak/KotlinScriptingExampleRepo), 
create files with the proper file extension, and start scripting!
