package com.ciyfhx

import com.ciyfhx.game.GridWorld
import com.ciyfhx.math.sumByFloat
import com.ciyfhx.math.zerosf
import com.ciyfhx.rl.Policy
import org.ejml.data.FMatrixRMaj

class PolicyIteration {

    fun policyEval(environment: GridWorld, policy: Policy, discountFactor: Float) {
        val sizeOfStates = environment.sizeOfWorld * environment.sizeOfWorld

        val V = FMatrixRMaj(environment.sizeOfWorld, environment.sizeOfWorld, true, *zerosf(sizeOfStates))

        for (i in 0 until 3) {

            val newV = V.copy()

            for (stateIndex in 0 until sizeOfStates) {
                val x = stateIndex / environment.sizeOfWorld
                val y = stateIndex % environment.sizeOfWorld

                var newVValue = 0f

                val actionsProb = policy[stateIndex]
                val validStates = environment.nextPossibleStates(x, y)
                val reward = environment.reward(x, y).toFloat()
                newVValue = actionsProb[0] * (validStates.sumByFloat { reward + discountFactor * V[it.x, it.y] })

//                for ((actionIndex, action) in GridWorld.Action.values().withIndex()) {
//                    val reward = environment.reward(x, y, action).toFloat()
//                    newVValue = actionsProb[actionIndex] * (validStates.sumByFloat { reward + discountFactor * V[it.x, it.y] })
//                }

                newV[x, y] = newVValue

            }
            V.set(newV)
            V.print()
        }


    }
}

fun main(){

    val sizeOfWorld = 4

    val gridWorld = GridWorld(sizeOfWorld)
    val randomPolicy = Policy(sizeOfWorld*sizeOfWorld, 4)

    val policyIteration = PolicyIteration()

    policyIteration.policyEval(gridWorld, randomPolicy, 1f)



}