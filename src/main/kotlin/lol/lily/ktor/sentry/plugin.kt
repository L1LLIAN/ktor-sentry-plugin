package lol.lily.ktor.sentry

import io.ktor.server.application.*
import io.ktor.util.*
import io.sentry.Sentry
import io.sentry.SentryOptions

/**
 * If this option is set the captured exception will be automatically rethrown
 * @param value The options new value
 */
@Suppress("unused")
fun SentryOptions.setRethrow(value: Boolean = true) {
    SentryPlugin.RETHROW = value
}

class SentryPlugin {
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, SentryOptions, SentryPlugin> {
        internal var RETHROW = false

        override val key = AttributeKey<SentryPlugin>("SentryPlugin")

        override fun install(pipeline: ApplicationCallPipeline, configure: SentryOptions.() -> Unit): SentryPlugin {
            Sentry.init(configure)

            pipeline.intercept(ApplicationCallPipeline.Monitoring) {
                try {
                    proceed()
                } catch (e: Exception) {
                    Sentry.captureException(e)

                    if (RETHROW) {
                        throw e
                    }
                }
            }

            return SentryPlugin()
        }
    }
}