package com.ciyfhx.math

import org.ejml.data.FMatrixRMaj

/**
 * Create an zero [Float] initialized array
 * @param arraySize size of the array to be initialized
 */
fun zerosf(arraySize: Int) = Array(arraySize) { 0f }.toFloatArray()

/**
 * Get the column data of a matrix.
 * This function assume the matrix is formatted as row-major and hence only extends
 * [FMatrixRMaj] class
 */
fun FMatrixRMaj.getColumnData(columnIndex: Int)
    = this.data.slice(columnIndex * numCols until (columnIndex + 1) * numCols).toFloatArray()


/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
public inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0.0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}