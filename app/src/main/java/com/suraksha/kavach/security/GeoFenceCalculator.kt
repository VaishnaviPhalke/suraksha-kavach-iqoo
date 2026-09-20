package com.suraksha.kavach.security

import kotlin.math.*

/**
 * GeoFenceCalculator executes localized on-device Haversine calculations
 * to verify if the operator is physically situated inside the authorized
 * security perimeter (zero cloud/network query needed).
 */
object GeoFenceCalculator {

    private const val EARTH_RADIUS_METERS = 6371000.0

    /**
     * Calculates the great-circle distance between two GPS points using the Haversine formula.
     * 
     * @param lat1 Latitude of point 1 in degrees
     * @param lon1 Longitude of point 1 in degrees
     * @param lat2 Latitude of point 2 in degrees
     * @param lon2 Longitude of point 2 in degrees
     * @return Distance in meters
     */
    fun calculateHaversineDistanceMeters(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val rLat1 = Math.toRadians(lat1)
        val rLat2 = Math.toRadians(lat2)

        val a = sin(dLat / 2).pow(2.0) +
                cos(rLat1) * cos(rLat2) * sin(dLon / 2).pow(2.0)
        
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_METERS * c
    }

    /**
     * Verifies if current location is within safe perimeter radius.
     */
    fun isWithinPerimeter(
        currentLat: Double,
        currentLon: Double,
        bunkerLat: Double,
        bunkerLon: Double,
        allowedRadiusMeters: Double
    ): Pair<Boolean, Double> {
        val distance = calculateHaversineDistanceMeters(currentLat, currentLon, bunkerLat, bunkerLon)
        return Pair(distance <= allowedRadiusMeters, distance)
    }
}
