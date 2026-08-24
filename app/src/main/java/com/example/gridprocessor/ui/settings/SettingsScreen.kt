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
fun SettingsScreen(
    vm: MainViewModel,
    padding: PaddingValues,
    darkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var animation by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "SETTINGS",
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = 2.sp
            )
        }

        item {
            SettingCard("Appearance") {
                SettingRow(
                    label = "Black / White theme",
                    value = if (darkMode) "BLACK" else "WHITE"
                ) {
                    Switch(
                        checked = darkMode,
                        onCheckedChange = onThemeChange
                    )
                }
            }
        }

        item {
            SettingCard("Grid") {
                SettingRow(
                    label = "Animation",
                    value = if (animation) "Normal" else "Off"
                ) {
                    Switch(
                        checked = animation,
                        onCheckedChange = { animation = it }
                    )
                }

                SettingRow(
                    label = "Grid size",
                    value = "16 × 16 × 16"
                ) { }

                SettingRow(
                    label = "Refresh",
                    value = "500 ms"
                ) { }
            }
        }
    }
}

@Composable
private fun SettingCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                color = GridMuted,
                fontSize = 12.sp,
                letterSpacing = 1.2.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            content()
        }
    }
}

@Composable
private fun SettingRow(
    label: String,
    value: String,
    control: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = label)
            Text(
                text = value,
                color = GridMuted,
                fontSize = 11.sp
            )
        }

        control()
    }
}
