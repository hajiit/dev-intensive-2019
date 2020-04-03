package ru.skillbranch.devintensive.extensions

fun String.truncate(maxLength: Int = 16):String {

    // сперва убираем пробелы в конце строки
    var newString = this.trimEnd()
    //1. Если строка меньше чем ограничитель то сразу возвращаем результат
    if(newString.length <= maxLength) return newString

    newString = newString.dropLast(newString.length - maxLength).trim().plus("...")

    return newString
}

fun String.stripHtml(): String {

    return this.replace(Regex("<[/!]*?[^<>]*?>"), "")
                .replace(Regex("([\\r\\n])[\\s]+"), "")
                .replace(Regex("[\\s]{2,}"), " ")
}