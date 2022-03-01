package dev.kastro.broker.data

import dev.kastro.broker.Symbol
import io.github.serpro69.kfaker.Faker
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class InMemoryStore(
    private val faker: Faker = Faker(),
    val symbols: HashMap<String, Symbol> = HashMap()
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        for (i in 1..10) {
            addNewSymbol()
        }
    }

    private fun addNewSymbol() {
        val symbol = Symbol(faker.currency.code())
        symbols[symbol.value] = symbol
        logger.debug("Added Symbol $symbol")
    }
}