package dev.kastro.broker.data

import dev.kastro.broker.Symbol
import io.github.serpro69.kfaker.Faker
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.Int

@Singleton
class InMemoryStore(
    private val faker: Faker = Faker(),
    val symbols: HashMap<String, Symbol> = HashMap()
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        initializeWith(10)
    }

    fun initializeWith(numberOfEntries: Int) {
        symbols.clear()
        for (i in 1..numberOfEntries) {
            addNewSymbol()
        }
    }

    private fun addNewSymbol() {
        val symbol = Symbol(faker.currency.code())
        symbols[symbol.value] = symbol
        logger.debug("Added Symbol $symbol")
    }
}