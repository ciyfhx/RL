package com.ciyfhx

import com.ciyfhx.game.GridWorld
import com.ciyfhx.math.argmax
import com.ciyfhx.math.zerosf
import com.ciyfhx.rl.Policy
import org.ejml.data.FMatrixRMaj

class ValueIteration {

    fun valueIteration(environment: GridWorld, discountFactor: Float){
        val sizeOfStates = environment.sizeOfWorld * environment.sizeOfWorld
        val V = FMatrixRMaj(environment.sizeOfWorld, environment.sizeOfWorld, true, *zerosf(sizeOfStates))

        for(k in 0 until 10){
            val newV = V.copy()
            for (stateIndex in 0 until sizeOfStates) {
                val x = stateIndex / environment.sizeOfWorld
                val y = stateIndex % environment.sizeOfWorld

                newV[x, y] = GridWorld.Action.values().map { environment.perform(x, y, it) }
                    .map { it.reward + (discountFactor * it.transitionProbability * V[it.nextState.x, it.nextState.y]) }
                    .maxOrNull()!!
            }
            V.set(newV)
            V.print()
        }

    }

}

fun main(){
    val environment = GridWorld()
    val valueIteration = ValueIteration()
    valueIteration.valueIteration(environment, 1f)
}