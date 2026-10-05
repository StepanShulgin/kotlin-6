package ru.otus.homework.homework

/**
 * Базовый интерфейс кофейного напитка
 */
interface Coffee {
    /**
     * Цена кофейного напитка в копейках
     */
    fun cost(): Int

    /**
     * Описание кофейного напитка
     */
    fun description(): String
}

class SimpleCoffee : Coffee {
    override fun cost() = 200
    override fun description() = "Простой кофе"
}

abstract class IngredientDecorator(
    private val coffee: Coffee,
    private val ingredient: String,
    private val ingredientCost: Int
) : Coffee {
    override fun cost(): Int = coffee.cost() + ingredientCost

    override fun description(): String = "${coffee.description()}, $ingredient"
}

class MilkDecorator(coffee: Coffee) : IngredientDecorator(coffee, "молоко", 50)

class SugarDecorator(coffee: Coffee) : IngredientDecorator(coffee, "сахар", 20)

class VanillaDecorator(coffee: Coffee) : IngredientDecorator(coffee, "ваниль", 70)
