package com.github.submob.logmob

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import co.touchlab.kermit.crashlytics.setupCrashlyticsExceptionHook

@Suppress("unused")
fun initIOSLogger(isCrashlyticsEnabled: Boolean = false): Logger {
    initLogger()
    // Setting CrashlyticsLogWriter doesn't work it has to be added later
    if (isCrashlyticsEnabled) {
        initCrashlytics()
    }

    return Logger
}

@Suppress("OPT_IN_USAGE", "unused")
fun initCrashlytics() {
    Logger.addLogWriter(
        CrashlyticsLogWriter(
            minSeverity = Severity.Verbose,
            minCrashSeverity = Severity.Warn,
            printTag = true
        )
    )
    setupCrashlyticsExceptionHook(Logger)
}
