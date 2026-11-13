package com.example.androidcomposestarter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcomposestarter.domain.DemoScene
import com.example.androidcomposestarter.ui.island.YecaiIsland
import com.example.androidcomposestarter.ui.theme.DesktopBg
import com.example.androidcomposestarter.ui.theme.LeafGreen
import com.example.androidcomposestarter.ui.theme.Muted
import com.example.androidcomposestarter.ui.theme.OnDesktop
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun YecaiIslandApp(viewModel: DemoViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val clock = Calendar.getInstance().let { cal ->
        String.format(Locale.US, "%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF102016), DesktopBg, Color(0xFF070A08)),
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            YecaiIsland(
                primaryAlert = state.primaryAlert,
                alerts = state.alerts,
                idleStatus = state.idleStatus,
                expanded = state.expanded,
                onClick = viewModel::toggleExpanded,
            )

            Spacer(Modifier.height(64.dp))
            Text(
                text = clock,
                color = OnDesktop,
                fontSize = 72.sp,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = "野菜桌面 · 演示",
                color = Muted,
                fontSize = 14.sp,
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "演示场景",
                color = Muted,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                DemoScene.entries.forEach { scene ->
                    val selected = state.scene == scene
                    FilterChip(
                        selected = selected,
                        onClick = { viewModel.selectScene(scene) },
                        label = { Text(scene.label) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFF1A221C),
                            labelColor = OnDesktop,
                            selectedContainerColor = LeafGreen,
                            selectedLabelColor = DesktopBg,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            borderColor = Color(0xFF2A332C),
                            selectedBorderColor = LeafGreen,
                        ),
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
