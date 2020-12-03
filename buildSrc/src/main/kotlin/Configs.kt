/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
@file:Suppress("SpellCheckingInspection")

import org.gradle.api.Project
import java.io.File
import java.util.concurrent.TimeUnit

object Versions {
    const val kotlin = "1.4.20"
    const val androidPlugin = "7.0.0-alpha02"
    const val firebaseCore = "18.0.0"
    const val firebaseCrashlytics = "17.3.0"
    const val anrWatchDog = "1.4.0"
    const val kermit = "0.1.8"
}

object Dependencies {
    object Android {
        const val firebaseCrashlytics =
            "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}"
        const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
        const val anrWatchDog = "com.github.anrwatchdog:anrwatchdog:${Versions.anrWatchDog}"
    }

    object Common {
        const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    }
}

object Classpaths {
    const val androidBuildTools = "com.android.tools.build:gradle:${Versions.androidPlugin}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Plugins {
    const val androidLibrary = "com.android.library"
    const val multiplatform = "multiplatform"
    const val kapt = "kapt"
}

object ProjectSettings {
    private const val mayorVersion = 2
    private const val minorVersion = 0

    const val projectCompileSdkVersion = 29
    const val projectMinSdkVersion = 21
    const val projectTargetSdkVersion = 29

    fun getVersionCode(project: Project) = gitCommitCount(project).let {
        if (it.isEmpty()) 1 else it.toInt()
    }

    fun getVersionName(project: Project) =
        "$mayorVersion.$minorVersion.${gitCommitCount(project)}"

    private fun gitCommitCount(project: Project) =
        "git rev-list --first-parent --count origin/master"
            .executeCommand(project.rootDir)?.trim()
            ?: ""

    @Suppress("SpreadOperator", "MagicNumber")
    private fun String.executeCommand(workingDir: File): String? = try {
        val parts = this.split("\\s".toRegex())
        ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().run {
                waitFor(10, TimeUnit.SECONDS)
                inputStream.bufferedReader().readText()
            }
    } catch (e: java.io.IOException) {
        e.printStackTrace()
        null
    }
}