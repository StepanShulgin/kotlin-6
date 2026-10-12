package ru.otus.homework.homework

inline fun processList(list: List<Int>, action: (Int) -> Unit) {
    for (item in list) {
        action(item)
    }
}

fun skipThreeAndPrint(list: List<Int>) {
    var index = 0
    processList(list) {
        if (index++ == 2) return@processList
        println("Processing $it")
    }
}
