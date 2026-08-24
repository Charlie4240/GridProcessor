package com.example.gridprocessor.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gridprocessor.ui.theme.GridMuted
import com.example.gridprocessor.viewmodel.MainViewModel

@Composable
fun HistoryScreen(vm: MainViewModel, padding: PaddingValues) {
    val nodes by vm.nodes.collectAsState()
    val gates by vm.gateStatus.collectAsState()
    LazyColumn(Modifier.fillMaxSize().padding(padding).padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("ACTIVITY", style = MaterialTheme.typography.titleLarge, letterSpacing = 2.sp) }
        item { Text("Live engine activity", color = GridMuted, fontSize = 12.sp) }
        item { ActivityCard("Nodes", nodes.size.toString(), "Engine") }
        item { ActivityCard("Active gates", gates.values.count { it }.toString(), "Gate state") }
    }
}

@Composable private fun ActivityCard(title:String,value:String,subtitle:String){
    Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween) { Column { Text(title); Text(subtitle,color=GridMuted,fontSize=11.sp) }; Text(value, style=MaterialTheme.typography.titleLarge) } }
}
