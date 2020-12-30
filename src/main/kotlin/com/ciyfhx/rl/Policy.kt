package com.ciyfhx.rl

import com.ciyfhx.math.getColumnData
import com.ciyfhx.math.setColumnData
import com.ciyfhx.math.zerosf
import org.ejml.data.FMatrixRMaj

/**
 * A Policy define the behaviour of the agent
 */
class Policy(
    val noOfStates: Int,
    val noOfActions: Int
) {
    private var stateActionsDistribution = FMatrixRMaj(noOfStates, noOfActions, true, *zerosf(noOfStates * noOfActions))

    init {
        //Create an random policy
        randomPolicy()
    }

    private fun randomPolicy(){
        val probability = 1.0f / noOfActions
        stateActionsDistribution.fill(probability)
    }

    operator fun get(stateIndex: Int) = stateActionsDistribution.getColumnData(stateIndex)

    operator fun set(stateIndex: Int, updatedActions: FloatArray){
        stateActionsDistribution.setColumnData(stateIndex, updatedActions)
    }

}