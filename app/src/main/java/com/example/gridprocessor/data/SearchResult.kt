package com.example.gridprocessor.data
data class PathData(val nodeIds: List<Int>, val score: Float)
data class SearchResult(
    val path: PathData,
    val startScore: Float, val middleScore: Float, val endScore: Float,
    val transitionScore: Float, val libraryScore: Float, val historyScore: Float,
    val reverseScore: Float, val modeScore: Float, val finalScore: Float
)