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
 * Set the column data of a matrix from the given [newData] array.
 * This function assume the matrix is formatted as row-major and hence only extends
 * [FMatrixRMaj] class
 */
fun FMatrixRMaj.setColumnData(columnIndex: Int, newData: FloatArray) {
    newData.copyInto(this.data, columnIndex * numCols)
}

fun FloatArray.argmax() = this.indexOfFirst { it == this.maxOrNull()!! }