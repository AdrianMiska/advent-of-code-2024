import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("resources/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


fun Iterable<Int>.allIncreasing(): Boolean {
    return zipWithNext { a, b -> a < b }.all { it }
}

fun Iterable<Int>.allDecreasing(): Boolean {
    return zipWithNext { a, b -> a > b }.all { it }
}

fun <T> List<T>.dropIndex(index: Int): List<T> {
    return this.filterIndexed { candidateIndex, _ -> index != candidateIndex }
}

fun <T> List<List<T>>.get(coordinates: Pair<Int, Int>): T {
    return this[coordinates.first][coordinates.second]
}

fun <T> List<MutableList<T>>.set(coordinates: Pair<Int, Int>, value: T) {
    this[coordinates.first][coordinates.second] = value
}

fun Pair<Int, Int>.isInBounds(maxX: Int, maxY: Int): Boolean {
    return first in 0..maxX && second in 0..maxY
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

fun <T> List<T>.pairwise(): List<Pair<T, T>> {
    return this.flatMap { first -> this.mapNotNull { second -> if (first != second) first to second else null } }
}