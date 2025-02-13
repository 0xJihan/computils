package com.jihan.composeutils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.text.StringBuilder

/**
 * Extension Methods & Widgets for the strings
 */
fun String.firstLetterUpperCase(): String {
    return if (length > 1) {
        this[0].uppercaseChar() + substring(1).lowercase()
    } else {
        this
    }
}

fun String.eliminateFirst(): String {
    return if (length > 1) substring(1) else ""
}

fun String.eliminateLast(): String {
    return if (length > 1) substring(0, length - 1) else ""
}

val String.isEmpty: Boolean
    get() = trimStart().isEmpty()

fun String.validateEmail(): Boolean {
    return Pattern.compile(
        "^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\\.[a-zA-Z]+"
    ).matcher(this).matches()
}

fun String.isRtlLanguage(): Boolean {
    val rtlLocaleRegex = Pattern.compile(
        "^(ar|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_]" +
                "(Arab|Hebr|Thaa|Nkoo|Tfng))(?!.*[-_](Latn|Cyrl)($|-|_))" +
                "($|-|_)",
        Pattern.CASE_INSENSITIVE
    )
    return rtlLocaleRegex.matcher(this).matches()
}

val String.orEmpty: String
    get() = this

fun String.ifEmpty(action: () -> String): String {
    return if (isEmpty()) action() else this
}

fun String.removeAllWhiteSpace(): String {
    return replace(Regex("\\s+\\b|\\b\\s"), "")
}

val String.isNotBlank: Boolean
    get() = trim().isNotEmpty()

fun String.hidePartial(begin: Int = 0, end: Int? = null, replace: Char = '*'): String? {
    if (length <= 1) return null
    val endIndex = end ?: (length / 2)
    val buffer = StringBuilder()
    for (i in indices) {
        when {
            i >= endIndex -> buffer.append(this[i])
            i >= begin -> buffer.append(replace)
            else -> buffer.append(this[i])
        }
    }
    return buffer.toString()
}

val String.numCurrency: String
    get() = NumberFormat.getCurrencyInstance().format(toDoubleOrNull()) ?: ""

fun String.numCurrencyWithLocale(locale: Locale = Locale.US): String {
    return NumberFormat.getCurrencyInstance(locale).format(toDoubleOrNull()) ?: ""
}

fun String.allWordsCapitalize(): String {
    return lowercase().split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercaseChar() }
    }
}

fun String.compareToIgnoringCase(other: String): Int {
    return lowercase().compareTo(other.lowercase())
}

fun String.insert(other: String, index: Int): String {
    return StringBuilder().apply {
        append(substring(0, index))
        append(other)
        append(substring(index))
    }.toString()
}

fun String.prepend(other: String): String {
    return other + this
}

fun String.reverse(): String {
    return StringBuilder().apply {
        for (i in length - 1 downTo 0) {
            append(this@reverse[i])
        }
    }.toString()
}

fun String.isCreditCardValid(): Boolean {
    var sum = 0
    var alternate = false
    for (i in length - 1 downTo 0) {
        var digit = this[i].toString().toInt()
        if (alternate) {
            digit *= 2
            if (digit > 9) {
                digit = (digit % 10) + 1
            }
        }
        sum += digit
        alternate = !alternate
    }
    return sum % 10 == 0
}

fun String.isNumber(): Boolean {
    return Pattern.compile("^-?\\d*\\.?\\d+$").matcher(this).matches()
}

fun String.isDigit(): Boolean {
    return Pattern.compile("\\d").matcher(this).matches() && length == 1
}

fun String.isLetter(): Boolean {
    return Pattern.compile("[A-Za-z]").matcher(this).matches()
}

fun String.isSymbol(): Boolean {
    val pattern = "`~!@#\$%^&*()_-+=<>?:\"{}|,.///;'\\[]·~！@#￥%……&*（）——-+={}|《》？：“”【】、；‘’，。、"
    return any { pattern.contains(it) }
}


fun String.filterChars(): String {
    return replace(Regex("[^\\w\\s]+"), "")
}

fun String.toDate(format:String="dd-MM-yyyy"): Date? {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        null
    }
}

fun String.toDateString(locale: Locale = Locale.getDefault()): String {
    return SimpleDateFormat("MMMMEEEEd", locale).format(toDate()!!)
}

val String.lowerCamelCase: String
    get() = split("_").joinToString("") { it.replaceFirstChar { it.uppercaseChar() } }

val String.upperCamelCase: String
    get() = split("_").joinToString("") { it.replaceFirstChar { it.uppercaseChar() } }

val String.capitalized: String
    get() = replaceFirstChar { it.uppercaseChar() }

val String.snakeCase: String
    get() = replace(Regex("([A-Z])"), "_$1").lowercase()

val String.toEncodedBase64: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = Base64.getEncoder().encodeToString(toByteArray())

val String.toDecodedBase64: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = String(Base64.getDecoder().decode(this))

val String.utf8ToList: List<Int>
    get() = toByteArray().map { it.toInt() }

val String.utf8Encode: ByteArray
    get() = toByteArray()

fun String.formatDigitPattern(digit: Int = 4, pattern: String = " "): String {
    return replace(Regex("(.{$digit})"), "\$1$pattern").removeSuffix(pattern)
}

fun String.formatDigitPatternEnd(digit: Int = 4, pattern: String = " "): String {
    return reversed().formatDigitPattern(digit, pattern).reversed()
}
