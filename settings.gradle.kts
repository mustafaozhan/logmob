/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":logmob")

rootProject.name = "LogMob"
rootProject.updateBuildFileNames()

fun ProjectDescriptor.updateBuildFileNames() {
    buildFileName = path
        .drop(1)
        .replace(":", "-")
        .dropLastWhile { it != '-' }
        .plus(name)
        .plus(".gradle.kts")

    if (children.isNotEmpty()) {
        children.forEach { it.updateBuildFileNames() }
    }
}
