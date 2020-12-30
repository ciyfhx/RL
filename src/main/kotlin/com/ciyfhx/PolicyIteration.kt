package com.ciyfhx

import com.ciyfhx.game.GridWorld
import com.ciyfhx.game.by
import com.ciyfhx.math.sumByFloat
import com.ciyfhx.math.zerosf
import com.ciyfhx.rl.Policy
import org.ejml.data.FMatrixRMaj

class PolicyIteration {


    fun policyIteration(sizeOfWorld: Int = 5) {

        val environment = GridWorld()
        var policy = Policy(sizeOfWorld, 4)
        val V = policyEvaluation(environment, policy, 1f)



    }

    fun policyEvaluation(environment: GridWorld, policy: Policy, discountFactor: Float): FMatrixRMaj {
        val sizeOfStates = environment.sizeOfWorld * environment.sizeOfWorld

        val V = FMatrixRMaj(environment.sizeOfWorld, environment.sizeOfWorld, true, *zerosf(sizeOfStates))

        for (i in 0 until 3) {

            val newV = V.copy()

            for (stateIndex in 0 until sizeOfStates) {
                val x = stateIndex / environment.sizeOfWorld
                val y = stateIndex % environment.sizeOfWorld

                var newVValue = 0f

                val actionsProb = policy[stateIndex]

                for ((actionIndex, action) in GridWorld.Action.values().withIndex()) {
                    val (reward, nextState, transitionProbability) = environment.perform(x, y, action)
                    newVValue += actionsProb[actionIndex] * transitionProbability * (reward + discountFactor * V[nextState.x, nextState.y])
                }

                newV[x, y] = newVValue

            }
            V.set(newV)
            V.print()
        }
        return V
    }
}

fun main(){

    val sizeOfWorld = 4

    val gridWorld = GridWorld(sizeOfWorld)
    val randomPolicy = Policy(sizeOfWorld*sizeOfWorld, 4)

    val policyIteration = PolicyIteration()

    policyIteration.policyEvaluation(gridWorld, randomPolicy, 1f)



}