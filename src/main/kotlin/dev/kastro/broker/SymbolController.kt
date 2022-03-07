package dev.kastro.broker

import dev.kastro.broker.data.InMemoryStore
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/symbols")
class SymbolController(
    private val inMemoryStore: InMemoryStore
) {
    @Get
    fun getAll(): HashMap<String, Symbol> {
        return inMemoryStore.symbols
    }

    @Get("{value}")
    fun getSymbolByValue(@PathVariable value: String): Symbol? {
        return inMemoryStore.symbols[value]
    }
}