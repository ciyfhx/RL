package com.ciyfhx.game

class GridWorld(
    val sizeOfWorld: Int = 5
) {

    enum class Cell(val reward: Int,
                    val transitionProbability: Int) {
        Empty(-1, 1), Terminal(0, 0)
    }

    enum class Action {
        North, South, East, West;
        companion object {
            fun intToCell(value: Int) = Action.values()[value]
        }
    }

    data class Result(val reward: Int,
                      val nextState: Point,
                      val transitionProbability: Int
                      )

    lateinit var cells: Array<Array<Cell>>
        private set

    init {
        init()
    }

    /**
     * Initialize the Game Environment, all [cells] will be set to [Cell.Empty] except the
     * top left, cells[0][0], will be set to [Cell.Terminal] for the game to end when the [Cell.Terminal]
     * is reached
     */
    private fun init() {
        cells = Array(sizeOfWorld) { Array(sizeOfWorld) { Cell.Empty } }

        // First cell will be the terminal state
        cells[0][0] = Cell.Terminal
//        cells[sizeOfWorld - 1][sizeOfWorld - 1] = Cell.Terminal

    }

    /**
     * Get next [Cell] from [Point]
     */
    private fun get(point: Point) = cells[point.x][point.y]

    /**
     * Get the 'reward', 'next state' and 'transition probability'
     * from performing the given [action] at position [x], [y]
     */
    fun perform(x: Int, y: Int, action: Action): Result {
        val currentState = get(x by y)
        val nextState = nextState(x, y, action)
        return Result(currentState.reward, nextState, currentState.transitionProbability)
    }

    fun nextState(x: Int, y: Int, action: Action): Point {
        return when (action) {
            Action.North -> {
                // Check for out of bound
                if (y <= 0) x by y
                else x by (y - 1)
            }
            Action.South -> {
                // Check for out of bound
                if (y >= (sizeOfWorld - 1)) x by y
                else x by (y + 1)
            }
            Action.East -> {
                // Check for out of bound
                if (x >= (sizeOfWorld - 1)) x by y
                else (x + 1) by y
            }
            Action.West -> {
                // Check for out of bound
                if (x <= 0) x by y
                else (x - 1) by y
            }
        }
    }


}