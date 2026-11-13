package com.example.androidcomposestarter.domain

object AlertRules {
    const val TEMP_DROP_THRESHOLD_C = 6

    const val PRIORITY_RESTRICTION = 0
    const val PRIORITY_RAIN = 1
    const val PRIORITY_TEMP_DROP = 2

    fun temperatureDropC(todayHighC: Int, tomorrowHighC: Int): Int =
        todayHighC - tomorrowHighC

    fun shouldAlertTempDrop(dropC: Int, thresholdC: Int = TEMP_DROP_THRESHOLD_C): Boolean =
        dropC >= thresholdC

    fun formatRestrictedPlates(plates: List<Int>): String {
        val distinct = plates.distinct().sorted()
        return when (distinct.size) {
            0 -> ""
            1 -> distinct[0].toString()
            2 -> "${distinct[0]} 和 ${distinct[1]}"
            else -> distinct.dropLast(1).joinToString("、") + " 和 ${distinct.last()}"
        }
    }

    fun rainMessage(rainTonight: Boolean, rainTomorrow: Boolean): String? = when {
        rainTonight && rainTomorrow -> "今晚到明天都有雨，记得带伞"
        rainTonight -> "今晚有雨，记得带伞"
        rainTomorrow -> "明天有雨，出门带伞"
        else -> null
    }

    fun tempDropMessage(dropC: Int): String? =
        if (shouldAlertTempDrop(dropC)) "明天降温 ${dropC}°C，注意添衣" else null

    fun restrictionMessage(info: RestrictionInfo?): String? {
        if (info == null || !info.isRestrictionDayTomorrow) return null
        val plates = formatRestrictedPlates(info.tomorrowRestrictedPlates)
        return if (plates.isBlank()) {
            "明日${info.city}限行，请提前准备"
        } else {
            "明日限行 $plates，请提前准备"
        }
    }

    fun buildAlerts(
        weather: WeatherForecast,
        restriction: RestrictionInfo?,
    ): List<IslandAlert> {
        val alerts = mutableListOf<IslandAlert>()

        restrictionMessage(restriction)?.let { message ->
            alerts += IslandAlert(
                type = AlertType.DRIVING_RESTRICTION,
                title = "限行提醒",
                message = message,
                priority = PRIORITY_RESTRICTION,
            )
        }

        rainMessage(weather.rainTonight, weather.rainTomorrow)?.let { message ->
            alerts += IslandAlert(
                type = AlertType.RAIN,
                title = "下雨提醒",
                message = message,
                priority = PRIORITY_RAIN,
            )
        }

        tempDropMessage(temperatureDropC(weather.todayHighC, weather.tomorrowHighC))?.let { message ->
            alerts += IslandAlert(
                type = AlertType.TEMP_DROP,
                title = "降温提醒",
                message = message,
                priority = PRIORITY_TEMP_DROP,
            )
        }

        return alerts.sortedBy { it.priority }
    }

    fun primary(alerts: List<IslandAlert>): IslandAlert? = alerts.minByOrNull { it.priority }
}
