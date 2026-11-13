package com.example.androidcomposestarter.ui

import androidx.lifecycle.ViewModel
import com.example.androidcomposestarter.data.AlertRepository
import com.example.androidcomposestarter.domain.AlertRules
import com.example.androidcomposestarter.domain.DemoScene
import com.example.androidcomposestarter.domain.IslandAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class IslandUiState(
    val scene: DemoScene = DemoScene.NORMAL,
    val alerts: List<IslandAlert> = emptyList(),
    val primaryAlert: IslandAlert? = null,
    val expanded: Boolean = false,
    val idleStatus: String = "周杰伦 · 晴天",
)

class DemoViewModel(
    private val repository: AlertRepository = AlertRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(IslandUiState())
    val uiState: StateFlow<IslandUiState> = _uiState.asStateFlow()

    init {
        selectScene(DemoScene.NORMAL)
    }

    fun selectScene(scene: DemoScene) {
        val alerts = repository.alertsFor(scene)
        _uiState.update {
            it.copy(
                scene = scene,
                alerts = alerts,
                primaryAlert = AlertRules.primary(alerts),
                expanded = false,
            )
        }
    }

    fun toggleExpanded() {
        val state = _uiState.value
        if (state.alerts.isEmpty() && !state.expanded) return
        _uiState.update { it.copy(expanded = !it.expanded) }
    }
}
