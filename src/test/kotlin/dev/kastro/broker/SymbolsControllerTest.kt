package dev.kastro.broker

import dev.kastro.broker.data.InMemoryStore
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.tree.JsonNode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach

@MicronautTest
class SymbolsControllerTest {
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
}
