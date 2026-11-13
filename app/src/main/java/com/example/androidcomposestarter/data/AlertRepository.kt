package com.example.androidcomposestarter.data

import com.example.androidcomposestarter.domain.AlertRules
import com.example.androidcomposestarter.domain.DemoScene
import com.example.androidcomposestarter.domain.IslandAlert
import com.example.androidcomposestarter.domain.RestrictionInfo
import com.example.androidcomposestarter.domain.WeatherForecast

class AlertRepository(
    private val city: String = "北京",
) {
    fun alertsFor(scene: DemoScene): List<IslandAlert> {
        val (weather, restriction) = sample(scene)
        return AlertRules.buildAlerts(weather, restriction)
    }

    fun sample(scene: DemoScene): Pair<WeatherForecast, RestrictionInfo?> = when (scene) {
        DemoScene.NORMAL -> WeatherForecast(
            city = city,
            todayHighC = 18,
            tomorrowHighC = 17,
        ) to RestrictionInfo(city = city, isRestrictionDayTomorrow = false)

        DemoScene.RAIN -> WeatherForecast(
            city = city,
            rainTonight = true,
            todayHighC = 16,
            tomorrowHighC = 15,
        ) to RestrictionInfo(city = city, isRestrictionDayTomorrow = false)

        DemoScene.TEMP_DROP -> WeatherForecast(
            city = city,
            todayHighC = 22,
            tomorrowHighC = 14,
        ) to RestrictionInfo(city = city, isRestrictionDayTomorrow = false)

        DemoScene.RESTRICTION -> WeatherForecast(
            city = city,
            todayHighC = 18,
            tomorrowHighC = 17,
        ) to RestrictionInfo(
            city = city,
            isRestrictionDayTomorrow = true,
            tomorrowRestrictedPlates = listOf(3, 8),
        )

        DemoScene.MULTIPLE -> WeatherForecast(
            city = city,
            rainTonight = true,
            rainTomorrow = true,
            todayHighC = 22,
            tomorrowHighC = 13,
        ) to RestrictionInfo(
            city = city,
            isRestrictionDayTomorrow = true,
            tomorrowRestrictedPlates = listOf(3, 8),
        )
    }
}
