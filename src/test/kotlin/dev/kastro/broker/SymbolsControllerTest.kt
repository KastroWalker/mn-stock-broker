package dev.kastro.broker

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.tree.JsonNode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class SymbolsControllerTest {

    @Inject
    @field:Client("/symbols")
    lateinit var client: HttpClient

    @Test
    fun symbolsEndpointReturnsListOfSymbol() {
        val response = client.toBlocking().exchange("/", JsonNode::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(10, response.body.get().size())
    }

}
