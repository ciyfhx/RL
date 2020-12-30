package com.ciyfhx

import com.ciyfhx.game.GridWorld
import com.ciyfhx.math.argmax
import com.ciyfhx.math.zerosf
import com.ciyfhx.rl.Policy
import org.ejml.data.FMatrixRMaj

class PolicyIteration {
    fun policyIteration(sizeOfWorld: Int = 5) {
        val environment = GridWorld()
        val sizeOfStates = environment.sizeOfWorld * environment.sizeOfWorld
        var policy = Policy(sizeOfWorld*sizeOfWorld, 4)
        val V = policyEvaluation(environment, policy, 1f)

        for (stateIndex in 0 until sizeOfStates) {
            val x = stateIndex / environment.sizeOfWorld
            val y = stateIndex % environment.sizeOfWorld

            val bestAction = GridWorld.Action.values()
                .map { environment.nextState(x, y, it) }
                .map { V[it.x, it.y] }.toFloatArray()
                .argmax()

            // Greedy policy
            val updatedActions = zerosf(policy.noOfActions).also { it[bestAction] = 1f }
            policy[stateIndex] = updatedActions
        }
        displayPolicy(environment, policy)
    }

    fun policyEvaluation(environment: GridWorld, policy: Policy, discountFactor: Float): FMatrixRMaj {
        val sizeOfStates = environment.sizeOfWorld * environment.sizeOfWorld

        val V = FMatrixRMaj(environment.sizeOfWorld, environment.sizeOfWorld, true, *zerosf(sizeOfStates))

        for (i in 0 until 5) {

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

    fun displayPolicy(environment: GridWorld, policy: Policy) {
        for (y in 0 until environment.sizeOfWorld) {
            for (x in 0 until environment.sizeOfWorld) {
                val stateIndex = x * environment.sizeOfWorld + y
                val action = GridWorld.Action.intToCell(policy[stateIndex].argmax())
                when(action){
                    GridWorld.Action.North -> print("North     ")
                    GridWorld.Action.South -> print("South     ")
                    GridWorld.Action.West ->  print("West      ")
                    GridWorld.Action.East ->  print("East      ")
                }
                print("  ")
            }
            println()
        }
    }

}

fun main(){

    val policyIteration = PolicyIteration()
    policyIteration.policyIteration()

//    val sizeOfWorld = 4
//
//    val gridWorld = GridWorld(sizeOfWorld)
//    val randomPolicy = Policy(sizeOfWorld*sizeOfWorld, 4)
//
//    val policyIteration = PolicyIteration()
//
//    policyIteration.policyEvaluation(gridWorld, randomPolicy, 1f)
//
//

}