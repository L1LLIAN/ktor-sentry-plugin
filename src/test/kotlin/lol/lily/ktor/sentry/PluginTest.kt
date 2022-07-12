package lol.lily.ktor.sentry

import io.ktor.client.request.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertFails

class PluginTest {
    @Test
    fun `without rethrow`() = testApplication {
        install(SentryPlugin) {
            dsn = System.getenv("SENTRY_TEST_DSN") ?: ""
            isDebug = true
            setRethrow(false)
        }

        routing {
            get("/") {
                throw RuntimeException("nya")
            }
        }

        client.get("/")
    }

    @Test
    fun `with rethrow`() {
        assertFails {
            testApplication {
                install(SentryPlugin) {
                    dsn = System.getenv("SENTRY_TEST_DSN") ?: ""
                    isDebug = true
                    setRethrow()
                }

                routing {
                    get("/") {
                        throw RuntimeException("nya")
                    }
                }

                client.get("/")
            }
        }
    }
}
