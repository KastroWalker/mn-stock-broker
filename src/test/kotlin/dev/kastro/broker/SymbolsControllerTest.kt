package dev.kastro.broker

import dev.kastro.broker.data.InMemoryStore
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.tree.JsonNode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory

@MicronautTest
class SymbolsControllerTest {
    private val LOG = LoggerFactory.getLogger(SymbolController::class.java)

    @Inject
    @field:Client("/symbols")
    lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        inMemoryStore.initializeWith(10)
    }

    @Inject
    lateinit var inMemoryStore: InMemoryStore

    @Test
    fun symbolsEndpointReturnsListOfSymbol() {
        val response = client.toBlocking().exchange("/", JsonNode::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(10, response.body.get().size())
    }

    @Test
    fun symbolsEndpointReturnsTheCorrectSymbol() {
        val testSymbol = Symbol("TEST")
        inMemoryStore.symbols[testSymbol.value] = testSymbol

        val response = client.toBlocking().exchange("/${testSymbol.value}", Symbol::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(testSymbol, response.body.get())
    }

    @Test
    fun symbolsEndpointReturnsListOfSymbolTakingQueryParametersIntoAccount() {
        val max10 = client.toBlocking().exchange("/filter?max=10", JsonNode::class.java)
        Assertions.assertEquals(HttpStatus.OK, max10.status)
        LOG.debug { "Max 10: ${max10.body.get()}" }
        Assertions.assertEquals(10, max10.body.get().size())

        val offset7 = client.toBlocking().exchange("/filter?offset=7", JsonNode::class.java)
        Assertions.assertEquals(HttpStatus.OK, offset7.status)
        LOG.debug { "Offset 7: ${offset7.body.get()}" }
        Assertions.assertEquals(3, offset7.body.get().size())

        val max2Offset7 = client.toBlocking().exchange("/filter?max=2&offset=7", JsonNode::class.java)
        Assertions.assertEquals(HttpStatus.OK, max2Offset7.status)
        LOG.debug { "Max2Offset: ${max2Offset7.body.get()}" }
        Assertions.assertEquals(2, max2Offset7.body.get().size())
    }
}
