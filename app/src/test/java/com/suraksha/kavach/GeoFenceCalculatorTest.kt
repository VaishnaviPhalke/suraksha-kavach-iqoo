package com.suraksha.kavach

import com.suraksha.kavach.security.GeoFenceCalculator
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests verifying pure Kotlin on-device Haversine calculation.
 */
class GeoFenceCalculatorTest {

    @Test
    fun testHaversineDistance_withinPerimeter() {
        val currentLat = 28.613939
        val currentLon = 77.209021

        // Small offset coordinates (~8.4 meters)
        val targetLat = 28.614000
        val targetLon = 77.209050

        val (isInside, distance) = GeoFenceCalculator.isWithinPerimeter(
            currentLat = currentLat,
            currentLon = currentLon,
            bunkerLat = targetLat,
            bunkerLon = targetLon,
            allowedRadiusMeters = 50.0
        )

        assertTrue("Distance should be within the 50m perimeter", isInside)
        assertTrue("Distance should be greater than 0", distance > 0.0)
        assertTrue("Distance should be under 50m", distance < 50.0)
    }

    @Test
    fun testHaversineDistance_outsidePerimeter() {
        val currentLat = 28.613939
        val currentLon = 77.209021

        // Far coordinates (~10 km away)
        val farLat = 28.704059
        val farLon = 77.102490

        val (isInside, distance) = GeoFenceCalculator.isWithinPerimeter(
            currentLat = currentLat,
            currentLon = currentLon,
            bunkerLat = farLat,
            bunkerLon = farLon,
            allowedRadiusMeters = 50.0
        )

        assertFalse("Far distance must fail perimeter validation", isInside)
        assertTrue("Distance should be over 50m", distance > 50.0)
    }
}
