package ru.skillbranch.devintensive.utils

import android.annotation.SuppressLint
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstItem = firstName?.trim()?.toUpperCase(Locale.getDefault())?.getOrNull(0)
        val secondItem = lastName?.trim()?.toUpperCase(Locale.getDefault())?.getOrNull(0)
        return when{
            firstItem == null && secondItem == null -> null
            firstItem == null -> secondItem.toString()
            secondItem == null -> firstItem.toString()
            else -> "$firstItem$secondItem"
        }
    }

    @SuppressLint("DefaultLocale")
    fun transliteration(payload: String, divider: String = " "): String{

        val replaceTemplate = mapOf("а" to "a", "б" to "b", "в" to "v", "г" to "g",
                                                    "д" to "d", "е" to "e", "ё" to "e", "ж" to "zh",
                                                    "з" to "z", "и" to "i", "й" to "i", "к" to "k",
                                                    "л" to "l", "м" to "m", "н" to "n", "о" to "o",
                                                    "п" to "p", "р" to "r", "с" to "s", "т" to "t",
                                                    "у" to "u", "ф" to "f", "х" to "h", "ц" to "c",
                                                    "ч" to "ch", "ш" to "sh", "щ" to "sh'", "ъ" to "",
                                                    "ы" to "i", "ь" to "", "э" to "e", "ю" to "yu",
                                                    "я" to "ya")
        var resultValue = ""
        payload.replace(" ", divider).run {
            for(el in this) {
                val elString = el.toString().toLowerCase(Locale.getDefault())
                val isUpper = elString != el.toString()
                val resultEl = replaceTemplate[elString] ?: elString
                resultValue += if(isUpper) resultEl.capitalize() else resultEl
            }
        }
        return resultValue
    }

}