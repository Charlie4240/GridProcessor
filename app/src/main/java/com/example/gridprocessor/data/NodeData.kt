package com.example.gridprocessor.data
data class NodeData(
    val id: Int, val x: Int, val y: Int, val z: Int,
    val binaryState: Byte, val activation: Float, val threshold: Float,
    val gateOpen: Boolean, val neighbors: IntArray, val weight: Float,
    val patternId: Int, val libraryId: Int, val visitCount: Int,
    val previousState: Byte, val nextState: Byte, val candidateScore: Float
)