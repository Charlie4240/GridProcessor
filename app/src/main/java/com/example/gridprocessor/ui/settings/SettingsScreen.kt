package com.example.gridprocessor.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gridprocessor.ui.theme.GridMuted
import com.example.gridprocessor.viewmodel.MainViewModel

@Composable
fun SettingsScreen(vm: MainViewModel, padding: PaddingValues, darkMode: Boolean, onThemeChange: (Boolean) -> Unit) {
    var animation by remember { mutableStateOf(true) }
    LazyColumn(Modifier.fillMaxSize().padding(padding).padding(14.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { Text("SETTINGS", style = MaterialTheme.typography.titleLarge, letterSpacing = 2.sp) }
        item { SettingCard("Appearance") {
            SettingRow("Black / White theme", if (darkMode) "BLACK" else "WHITE") { Switch(darkMode, onThemeChange) }
        } }
        item { SettingCard("Grid") {
            SettingRow("Animation", if(animation) "Normal" else "Off") { Switch(animation, { animation = it }) }
            SettingRow("Grid size", "16 × 16 × 16") { }
            SettingRow("Refresh", "500 ms") { }
        } }
    }
}

@Composable private fun SettingCard(title:String, content:@Composable ColumnScope.()->Unit){ Card(Modifier.fillMaxWidth()){Column(Modifier.padding(18.dp)){Text(title, color=GridMuted,fontSize=12.sp,letterSpacing=1.2.sp);Spacer(Modifier.height(8.dp));content()}} }
@Composable private fun SettingRow(label:String,value:String,control:@Composable()->Unit){Row(Modifier.fillMaxWidth().padding(vertical=8.dp),horizontalArrangement=Arrangement.SpaceBetween){Column{Text(label);Text(value,color=GridMuted,fontSize=11.sp)};control()}}
