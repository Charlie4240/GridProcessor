package com.example.gridprocessor.ui.inspector
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gridprocessor.viewmodel.MainViewModel

@Composable
fun NodeInspectorScreen(vm: MainViewModel, nodeId: Int) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("NodeInspector Screen", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Full implementation connects to engine.")
        Button(onClick = { /* action */ }) { Text("Refresh") }
    }
}