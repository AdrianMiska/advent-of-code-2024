enum class GridElement {
    FREE,
    OBSTRUCTION
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun fromDirectionChar(char: Char): Direction {
            return when (char) {
                '^' -> UP
                'v' -> DOWN
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw IllegalArgumentException("Invalid direction: $char")
            }
        }
    }
}

class Guard(var position: Pair<Int, Int>, directionChar: Char) {

    private var direction: Direction = Direction.fromDirectionChar(directionChar)

    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
        }
    }

    fun nextPosition(): Pair<Int, Int> {
        return when (direction) {
            Direction.UP -> Pair(position.first - 1, position.second)
            Direction.DOWN -> Pair(position.first + 1, position.second)
            Direction.LEFT -> Pair(position.first, position.second - 1)
            Direction.RIGHT -> Pair(position.first, position.second + 1)
        }
    }

    fun move() {
        position = nextPosition()
    }

}

class World(initialState: List<String>) {

    private lateinit var guard: Guard

    private var height: Int
    private var width: Int

    private var grid: List<List<GridElement>>

    init {
        val rawGrid = initialState.map { it.toCharArray() }

        height = rawGrid.size
        width = rawGrid.first().size

        grid = List(height) { x ->
            List(width) { y ->
                when (rawGrid[x][y]) {
                    '.' -> GridElement.FREE
                    '#' -> GridElement.OBSTRUCTION
                    else -> {
                        guard = Guard(Pair(x, y), rawGrid[x][y])
                        GridElement.FREE
                    }
                }
            }
        }
    }

    private fun Pair<Int, Int>.isInBounds(maxX: Int, maxY: Int): Boolean {
        return first in 0..maxX && second in 0..maxY
    }

    fun <T> List<List<T>>.get(coordinates: Pair<Int, Int>): T {
        return this[coordinates.first][coordinates.second]
    }

    fun simulate(): Int {
        val visited = mutableSetOf(guard.position)

        while (guard.nextPosition().isInBounds(height - 1, width - 1)) {
            val gridElement = grid.get(guard.nextPosition())
            when (gridElement) {
                GridElement.FREE -> guard.move()
                GridElement.OBSTRUCTION -> guard.turnRight()
            }
            visited.add(guard.position)
        }

        return visited.size
    }


}

fun main() {


    fun part1(input: List<String>): Int {
        val world = World(input)
        return world.simulate()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }


    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    //check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    //part2(input).println()
}
