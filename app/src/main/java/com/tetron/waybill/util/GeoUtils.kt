package com.tetron.waybill.util

import kotlin.math.*

/**This class is designed to work with geo. function computeDistanceBetween calculation of distances between two coordinates return of result in meters**/
class GeoUtils {
    companion object {

        fun computeDistanceBetween(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {

            return computeAngleBetween(lat1, lng1, lat2, lng2) * 6371009.0 //EARTH_RADIUS
        }

        private fun computeAngleBetween(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
            return distanceRadians(
                toRadians(lat1), toRadians(lng1),
                toRadians(lat2), toRadians(lng2)
            )
        }

        private fun distanceRadians(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
            return arcHav(havDistance(lat1, lat2, lng1 - lng2))
        }

        private fun toRadians(angDeg: Double): Double {
            return angDeg / 180.0 * PI
        }

        private fun arcHav(x: Double): Double {
            return 2 * asin(sqrt(x))
        }

        private fun havDistance(lat1: Double, lat2: Double, dLng: Double): Double {
            return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2)
        }

        private fun hav(x: Double): Double {
            val sinHalf = sin(x * 0.5)
            return sinHalf * sinHalf
        }


    }

}