package com.example.androidcomposestarter.domain

enum class AlertType {
    DRIVING_RESTRICTION,
    RAIN,
    TEMP_DROP,
}

data class WeatherForecast(
    val city: String,
    val rainTonight: Boolean = false,
    val rainTomorrow: Boolean = false,
    val todayHighC: Int,
    val tomorrowHighC: Int,
)

data class RestrictionInfo(
    val city: String,
    val isRestrictionDayTomorrow: Boolean,
    val tomorrowRestrictedPlates: List<Int> = emptyList(),
)

data class IslandAlert(
    val type: AlertType,
    val title: String,
    val message: String,
    val priority: Int,
)

enum class DemoScene(val label: String) {
    NORMAL("普通"),
    RAIN("下雨"),
    TEMP_DROP("降温"),
    RESTRICTION("限行"),
    MULTIPLE("多条同时"),
}
