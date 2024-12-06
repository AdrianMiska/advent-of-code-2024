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