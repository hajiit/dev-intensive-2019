package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {

    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val difference = abs(date.time - this.time)
    val isBefore = date.before(this)
    var before = ""
    var after = ""

    if (isBefore) before = "через " else after = " назад"

    return when (difference) {
        in (0..1 * SECOND) -> if (isBefore) "вот вот" else "только что"
        in (1 * SECOND..45 * SECOND) -> "${before}несколько секунд${after}"
        in (45 * SECOND..75 * SECOND) -> "${before}минуту${after}"
        in (75 * SECOND..45 * MINUTE) -> kotlin.run {
            val value = (difference / MINUTE).toInt()
            "${before}$value ${getTimeUnitPeriodName(
                value, "минуту", "минуты", "минут"
            )}${after}"
        }
        in (45 * MINUTE..75 * MINUTE) -> "${before}час${after}"
        in (75 * MINUTE..22 * HOUR) -> kotlin.run {
            val value = (difference / HOUR).toInt()
            "${before}$value ${getTimeUnitPeriodName(
                value, "час", "часа", "часов"
            )}${after}"
        }
        in (22 * HOUR..26 * HOUR) -> "${before}день${after}"
        in (26 * HOUR..360 * DAY) -> kotlin.run {
            val value = (difference / DAY).toInt()
            "${before}$value ${getTimeUnitPeriodName(
                value, "день", "дня", "дней"
            )}${after}"
        }
        else -> if (isBefore) "более чем через год" else "более года назад"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        return when (this) {
            SECOND -> "$value ${getTimeUnitPeriodName(
                value,
                "секунду",
                "секунды",
                "секунд"
            )}"
            MINUTE -> "$value ${getTimeUnitPeriodName(
                value,
                "минуту",
                "минуты",
                "минут"
            )}"
            HOUR -> "$value ${getTimeUnitPeriodName(
                value,
                "час",
                "часа",
                "часов"
            )}"
            DAY -> "$value ${getTimeUnitPeriodName(
                value,
                "день",
                "дня",
                "дней"
            )}"
        }
    }
}

fun getTimeUnitPeriodName(
    value: Int,
    timeUnitName1: String,
    timeUnitName2: String,
    timeUnitName3: String
): String {
    val time = abs(value) // для отрицательных чисел
    val small = (time % 10)
    val middle = (time % 100)
    // если заканчивается на 11, то всегда оформляется словом timeUnitName3
    return if (small == 1 && middle != 11) timeUnitName1
    // если оканчиваются на 2, 3, 4, за исключением 12, 13 и 14
    else if (small in 2..4 && (middle < 12 || middle > 14)) timeUnitName2
    else timeUnitName3
}