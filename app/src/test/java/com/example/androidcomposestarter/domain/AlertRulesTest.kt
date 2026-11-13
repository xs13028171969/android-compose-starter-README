package com.example.androidcomposestarter.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AlertRulesTest {

    private val fairWeather = WeatherForecast(
        city = "北京",
        todayHighC = 18,
        tomorrowHighC = 17,
    )

    @Test
    fun rainTonightProducesTonightMessage() {
        val alerts = AlertRules.buildAlerts(
            fairWeather.copy(rainTonight = true),
            restriction = null,
        )
        assertEquals(1, alerts.size)
        assertEquals(AlertType.RAIN, alerts[0].type)
        assertEquals("今晚有雨，记得带伞", alerts[0].message)
    }

    @Test
    fun rainTomorrowProducesTomorrowMessage() {
        val message = AlertRules.rainMessage(rainTonight = false, rainTomorrow = true)
        assertEquals("明天有雨，出门带伞", message)
    }

    @Test
    fun noRainProducesNoAlert() {
        assertNull(AlertRules.rainMessage(rainTonight = false, rainTomorrow = false))
        assertTrue(AlertRules.buildAlerts(fairWeather, restriction = null).isEmpty())
    }

    @Test
    fun tempDropAtThresholdCreatesAlert() {
        val drop = AlertRules.temperatureDropC(todayHighC = 20, tomorrowHighC = 14)
        assertEquals(6, drop)
        assertEquals("明天降温 6°C，注意添衣", AlertRules.tempDropMessage(drop))
    }

    @Test
    fun tempDropBelowThresholdIsIgnored() {
        val drop = AlertRules.temperatureDropC(todayHighC = 18, tomorrowHighC = 13)
        assertEquals(5, drop)
        assertNull(AlertRules.tempDropMessage(drop))
    }

    @Test
    fun restrictionTheDayBeforeCreatesAlert() {
        val alerts = AlertRules.buildAlerts(
            fairWeather,
            RestrictionInfo(
                city = "北京",
                isRestrictionDayTomorrow = true,
                tomorrowRestrictedPlates = listOf(8, 3, 3),
            ),
        )
        assertEquals(1, alerts.size)
        assertEquals(AlertType.DRIVING_RESTRICTION, alerts[0].type)
        assertEquals("明日限行 3 和 8，请提前准备", alerts[0].message)
    }

    @Test
    fun restrictionNotTomorrowIsIgnored() {
        val message = AlertRules.restrictionMessage(
            RestrictionInfo(
                city = "北京",
                isRestrictionDayTomorrow = false,
                tomorrowRestrictedPlates = listOf(3, 8),
            ),
        )
        assertNull(message)
    }

    @Test
    fun multipleAlertsAreOrderedByPriority() {
        val alerts = AlertRules.buildAlerts(
            WeatherForecast(
                city = "北京",
                rainTonight = true,
                todayHighC = 22,
                tomorrowHighC = 13,
            ),
            RestrictionInfo(
                city = "北京",
                isRestrictionDayTomorrow = true,
                tomorrowRestrictedPlates = listOf(3, 8),
            ),
        )
        assertEquals(
            listOf(AlertType.DRIVING_RESTRICTION, AlertType.RAIN, AlertType.TEMP_DROP),
            alerts.map { it.type },
        )
        assertEquals(AlertType.DRIVING_RESTRICTION, AlertRules.primary(alerts)?.type)
        assertEquals("明日限行 3 和 8，请提前准备", AlertRules.primary(alerts)?.message)
    }

    @Test
    fun formatThreePlatesUsesEnumeration() {
        assertEquals("1、3 和 8", AlertRules.formatRestrictedPlates(listOf(8, 1, 3)))
    }
}
