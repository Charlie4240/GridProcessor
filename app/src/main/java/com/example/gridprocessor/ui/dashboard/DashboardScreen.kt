package com.example.gridprocessor.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gridprocessor.ui.theme.*
import com.example.gridprocessor.viewmodel.MainViewModel

@Composable
fun DashboardScreen(vm: MainViewModel, padding: PaddingValues, onOpenGrid: () -> Unit) {
    val nodes by vm.nodes.collectAsState()
    val gates by vm.gateStatus.collectAsState()
    val activePaths by vm.activePaths.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 14.dp), verticalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(bottom = 18.dp)) {
        item { Header() }
        item {
            ProcessorCard {
                SectionTitle("CPU UTILIZATION", "REAL-TIME")
                Text("67.5%", fontSize = 54.sp, style = MaterialTheme.typography.displaySmall)
                Text("4 Cores  ·  2.41 GHz", color = GridMuted)
                Spacer(Modifier.height(18.dp))
                Box(Modifier.fillMaxWidth().height(6.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))) {
                    Box(Modifier.fillMaxWidth(.675f).fillMaxHeight().background(Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)), RoundedCornerShape(10.dp)))
                }
            }
        }
        item {
            ProcessorCard {
                SectionTitle("3D GRID", "ACTIVE")
                MiniGrid()
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    Metric("${nodes.size}", "TOTAL NODES", Modifier.weight(1f))
                    Metric("${gates.values.count { it }}", "ACTIVE NODES", Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    Metric("${activePaths.size}", "ACTIVE PATHS", Modifier.weight(1f))
                    Metric("LIVE", "ENGINE", Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
                Button(onClick = onOpenGrid, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) { Text("VIEW GRID") }
            }
        }
        item {
            ProcessorCard {
                SectionTitle("CPU SCAN", "SCANNING")
                DataRow("CORES", "4", "FREQUENCY", "2.41 GHz")
                DataRow("LOAD", "58%", "TEMPERATURE", "—")
                DataRow("PROCESSES", "—", "THREADS", "—")
            }
        }
        item {
            ProcessorCard {
                SectionTitle("ACTIVITY", "LIVE")
                DataRow("NODES", "${nodes.size}", "PATHS", "${activePaths.size}")
                DataRow("GATES", "${gates.values.count { it }}", "ENGINE", "RUNNING")
            }
        }
    }
}

@Composable private fun Header() {
    Row(Modifier.fillMaxWidth().padding(top = 18.dp, bottom = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("GRIDPROCESSOR", style = MaterialTheme.typography.titleLarge, letterSpacing = 2.sp)
        Text("● LIVE", color = GridGreen, fontSize = 11.sp)
    }
}

@Composable private fun ProcessorCard(content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) { Column(Modifier.padding(18.dp), content = content) }
}

@Composable private fun SectionTitle(title: String, badge: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, color = GridMuted, fontSize = 12.sp, letterSpacing = 1.2.sp)
        Text(badge, color = GridMuted, fontSize = 9.sp)
    }
    Spacer(Modifier.height(12.dp))
}

@Composable private fun Metric(value: String, label: String, modifier: Modifier) {
    Column(modifier.background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(15.dp)).padding(12.dp)) {
        Text(value, style = MaterialTheme.typography.titleLarge)
        Text(label, color = GridMuted, fontSize = 9.sp)
    }
}

@Composable private fun DataRow(a:String,b:String,c:String,d:String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 9.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(a, color = GridMuted, fontSize = 12.sp); Text(b, fontSize = 12.sp)
        Text(c, color = GridMuted, fontSize = 12.sp); Text(d, fontSize = 12.sp)
    }
}

@Composable private fun MiniGrid() {
    Column(Modifier.fillMaxWidth().padding(vertical = 18.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        repeat(5) { r -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) { repeat(7) { c -> Box(Modifier.padding(horizontal = 4.dp).size(14.dp).background(if ((r+c)%5==0) GridGreen else if ((r+c)%3==0) GridCyan else GridPurple, RoundedCornerShape(4.dp))) } } }
    }
}
