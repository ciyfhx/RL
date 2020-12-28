package com.ciyfhx.game

class GridWorld(
    val sizeOfWorld: Int = 5
) {

    enum class Cell(val reward: Int) {
        Empty(-1), Terminal(0)
    }

    enum class Action {
        North, South, East, West
    }

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
    fun init() {
        cells = Array(sizeOfWorld) { Array(sizeOfWorld) { Cell.Empty }  }

        // First cell will be the terminal state
        cells[0][0] = Cell.Terminal
        cells[sizeOfWorld-1][sizeOfWorld-1] = Cell.Terminal

    }

    /**
     * Get next [Cell] from [Point]
     */
    private fun get(point: Point) = cells[point.x][point.y]

    /**
     * Get the reward from performing the given [action]
     */
    fun reward(x: Int, y: Int): Int {
        return get(x to y).reward
//        val nextState = nextState(x, y, action)
//        return if (nextState != null){
//            get(nextState).reward
//        }else {
//            -1
//        }
    }

    fun nextPossibleStates(x: Int, y: Int): List<Point> {
        val validStates = mutableListOf<Point>()
        for (action in Action.values()){
            val state = nextState(x, y, action)
            validStates += state
        }
        return validStates
    }

    fun possibleActions(x: Int, y: Int): List<Action> {
        val validAction = mutableListOf<Action>()
        for (action in Action.values()){
            val state = nextState(x, y, action)
            validAction += action
        }
        return validAction
    }

    private fun nextState(x: Int, y: Int, action: Action): Point{
        return when(action){
            Action.North -> {
                // Check for out of bound
                if (y <= 0) x to y
                else x to (y - 1)
            }
            Action.South -> {
                // Check for out of bound
                if (y >= (sizeOfWorld - 1)) x to y
                else x to (y + 1)
            }
            Action.East -> {
                // Check for out of bound
                if (x >= (sizeOfWorld - 1)) x to y
                else (x + 1) to y
            }
            Action.West -> {
                // Check for out of bound
                if (x <= 0) x to y
                else (x - 1) to y
            }
        }
    }



}