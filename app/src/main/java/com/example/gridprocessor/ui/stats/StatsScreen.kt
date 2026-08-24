package com.example.gridprocessor.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gridprocessor.ui.theme.GridMuted
import com.example.gridprocessor.viewmodel.MainViewModel

@Composable
fun StatsScreen(vm: MainViewModel, padding: PaddingValues) {
    val nodes by vm.nodes.collectAsState()
    val gates by vm.gateStatus.collectAsState()
    val active = gates.values.count { it }

    Column(
        modifier = Modifier.fillMaxSize().padding(padding).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("STATS", style = MaterialTheme.typography.titleLarge)
        Text("Live GridProcessor engine metrics", color = GridMuted)
        StatCard("TOTAL NODES", nodes.size.toString())
        StatCard("ACTIVE GATES", active.toString())
        StatCard("GATE RATIO", if (nodes.isEmpty()) "0%" else "${(active * 100 / nodes.size)}%")
        StatCard("GRID", "12 × 12 × 12")
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = GridMuted)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
