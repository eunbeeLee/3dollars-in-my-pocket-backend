package com.depromeet.threedollar.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDistanceUtils {

    public static Integer getDistance(double sourceLatitude, double sourceLongitude, double targetLatitude, double targetLongitude) {
        double theta = sourceLongitude - targetLongitude;
        double dist = Math.sin(deg2rad(sourceLatitude)) * Math.sin(deg2rad(targetLatitude))
            + Math.cos(deg2rad(sourceLatitude)) * Math.cos(deg2rad(targetLatitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (int) (dist * 1609.344);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
