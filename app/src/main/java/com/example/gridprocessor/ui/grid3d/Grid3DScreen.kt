package com.example.gridprocessor.ui.grid3d

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gridprocessor.ui.dashboard.*
import com.example.gridprocessor.ui.theme.*
import com.example.gridprocessor.viewmodel.MainViewModel
import androidx.compose.ui.Alignment

@Composable
fun Grid3DScreen(vm: MainViewModel, padding: PaddingValues) {
    val nodes by vm.nodes.collectAsState()
    val gates by vm.gateStatus.collectAsState()
    Column(Modifier.fillMaxSize().padding(padding).padding(14.dp)) {
        Text("GRIDPROCESSOR", style = MaterialTheme.typography.titleLarge, letterSpacing = 2.sp)
        Spacer(Modifier.height(14.dp))
        Card(Modifier.fillMaxWidth().weight(1f), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.fillMaxSize().padding(18.dp)) {
                Text("3D GRID", color = GridMuted, fontSize = 12.sp, letterSpacing = 1.2.sp)
                Spacer(Modifier.height(12.dp))
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(10) { r -> Row { repeat(10) { c -> Box(Modifier.padding(4.dp).size(14.dp).background(if ((r+c)%7==0) GridGreen else if ((r+c)%3==0) GridCyan else GridPurple, RoundedCornerShape(4.dp))) } } }
                    }
                }
                Text("${nodes.size} nodes  ·  ${gates.values.count { it }} active", color = GridMuted, fontSize = 12.sp)
            }
        }
    }
}
