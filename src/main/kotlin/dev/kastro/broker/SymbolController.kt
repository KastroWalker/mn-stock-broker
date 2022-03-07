package dev.kastro.broker

import dev.kastro.broker.data.InMemoryStore
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import java.util.Optional

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

    @Get("/filter{?max,offset}")
    fun getSymbols(@QueryValue max: Optional<Int>, @QueryValue offset: Optional<Int>): List<Symbol> {
        return inMemoryStore.symbols.values.stream()
            .skip(offset.orElse(0).toLong())
            .limit(max.orElse(10).toLong())
            .toList()
    }
}