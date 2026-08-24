package com.example.gridprocessor.engine
import com.example.gridprocessor.data.NodeData
import com.example.gridprocessor.data.SearchResult

class GridEngine {
    private external fun nativeCreate(): Long
    private external fun nativeDestroy(ptr: Long)
    private external fun nativeGetNodeCount(ptr: Long): Int
    private external fun nativeGetNode(ptr: Long, id: Int): NodeData
    private external fun nativeGetAllNodes(ptr: Long): Array<NodeData>
    private external fun nativeUpdateGates(ptr: Long)
    private external fun nativeForwardSearch(ptr: Long, input: String): SearchResult
    private external fun nativeReverseSearch(ptr: Long, target: String): SearchResult
    private external fun nativeRandomWalk(ptr: Long, steps: Int): SearchResult
    private external fun nativeTrainSequence(ptr: Long, sequence: Array<String>)
    private external fun nativeLoadBuiltinLibraries(ptr: Long)
    private external fun nativeRunBenchmarks(ptr: Long): String

    private var nativePtr: Long = 0
    init { System.loadLibrary("gridengine"); nativePtr = nativeCreate() }
    fun getNodeCount() = nativeGetNodeCount(nativePtr)
    fun getNode(id: Int) = nativeGetNode(nativePtr, id)
    fun getAllNodes() = nativeGetAllNodes(nativePtr)
    fun updateGates() = nativeUpdateGates(nativePtr)
    fun forwardSearch(input: String) = nativeForwardSearch(nativePtr, input)
    fun reverseSearch(target: String) = nativeReverseSearch(nativePtr, target)
    fun randomWalk(steps: Int) = nativeRandomWalk(nativePtr, steps)
    fun trainSequence(seq: List<String>) = nativeTrainSequence(nativePtr, seq.toTypedArray())
    fun loadBuiltinLibraries() = nativeLoadBuiltinLibraries(nativePtr)
    fun runBenchmarks(): String = nativeRunBenchmarks(nativePtr)
    fun destroy() { if(nativePtr!=0L) { nativeDestroy(nativePtr); nativePtr=0 } }
    protected fun finalize() { destroy() }
}