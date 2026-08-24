package com.example.gridprocessor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gridprocessor.ui.dashboard.DashboardScreen
import com.example.gridprocessor.ui.grid3d.Grid3DScreen
import com.example.gridprocessor.ui.history.HistoryScreen
import com.example.gridprocessor.ui.settings.SettingsScreen
import com.example.gridprocessor.ui.stats.StatsScreen
import com.example.gridprocessor.ui.theme.GridProcessorTheme
import com.example.gridprocessor.viewmodel.MainViewModel

private enum class RootTab(val title: String) {
    HOME("Home"), GRID("Grid"), ACTIVITY("Activity"), STATS("Stats"), SETTINGS("Settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GridProcessorRoot() }
    }
}

@Composable
private fun GridProcessorRoot() {
    var darkMode by remember { mutableStateOf(true) }
    var tab by remember { mutableStateOf(RootTab.HOME) }
    val vm: MainViewModel = viewModel()

    GridProcessorTheme(darkTheme = darkMode) {
        Surface(Modifier.fillMaxSize()) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        RootTab.entries.forEach { item ->
                            NavigationBarItem(
                                selected = tab == item,
                                onClick = { tab = item },
                                icon = {
                                    Icon(
                                        imageVector = when (item) {
                                            RootTab.HOME -> Icons.Outlined.Home
                                            RootTab.GRID -> Icons.Outlined.GridView
                                            RootTab.ACTIVITY -> Icons.Outlined.List
                                            RootTab.STATS -> Icons.Outlined.BarChart
                                            RootTab.SETTINGS -> Icons.Outlined.Settings
                                        },
                                        contentDescription = item.title
                                    )
                                },
                                label = { androidx.compose.material3.Text(item.title) }
                            )
                        }
                    }
                }
            ) { padding ->
                when (tab) {
                    RootTab.HOME -> DashboardScreen(vm, padding) { tab = RootTab.GRID }
                    RootTab.GRID -> Grid3DScreen(vm, padding)
                    RootTab.ACTIVITY -> HistoryScreen(vm, padding)
                    RootTab.STATS -> StatsScreen(vm, padding)
                    RootTab.SETTINGS -> SettingsScreen(vm, padding, darkMode) { darkMode = it }
                }
            }
        }
    }
}
