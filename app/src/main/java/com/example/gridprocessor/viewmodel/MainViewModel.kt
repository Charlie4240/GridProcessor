package com.example.gridprocessor.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.gridprocessor.engine.GridEngine
import com.example.gridprocessor.data.NodeData
import com.example.gridprocessor.data.PathData
import com.example.gridprocessor.data.SearchResult

class MainViewModel : ViewModel() {
    private val engine = GridEngine()
    private val _nodes = MutableStateFlow<List<NodeData>>(emptyList())
    val nodes = _nodes.asStateFlow()
    private val _activePaths = MutableStateFlow<List<PathData>>(emptyList())
    val activePaths = _activePaths.asStateFlow()
    private val _gateStatus = MutableStateFlow<Map<Int,Boolean>>(emptyMap())
    val gateStatus = _gateStatus.asStateFlow()
    private val _benchmarkResult = MutableStateFlow<String?>(null)
    val benchmarkResult = _benchmarkResult.asStateFlow()
    private var updateJob: kotlinx.coroutines.Job? = null

    init {
        engine.loadBuiltinLibraries()
        startPeriodicUpdate()
    }
    private fun startPeriodicUpdate() {
        updateJob = viewModelScope.launch {
            while (true) {
                val nodeList = engine.getAllNodes().toList()
                _nodes.value = nodeList
                _gateStatus.value = nodeList.associate { it.id to it.gateOpen }
                delay(100)
            }
        }
    }
    fun updateGates() { engine.updateGates() }
    fun forwardSearch(input: String): SearchResult = engine.forwardSearch(input)
    fun trainSequence(seq: List<String>) { engine.trainSequence(seq) }
    fun runBenchmark() { viewModelScope.launch { _benchmarkResult.value = engine.runBenchmarks() } }
    fun getNode(id: Int): NodeData = engine.getNode(id)
    override fun onCleared() { updateJob?.cancel(); engine.destroy(); super.onCleared() }
}