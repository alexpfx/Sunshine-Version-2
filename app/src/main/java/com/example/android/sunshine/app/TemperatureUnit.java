package com.example.android.sunshine.app;

import android.util.Log;

/**
 * Created by alexandre on 18/09/2016.
 */
public enum TemperatureUnit {


    CELSIUS("metric") {
        @Override
        public double fromCelsius(double value) {
            Log.d(TAG, "fromCelsius (c): "+value);
            return value * 1;
        }
    },

    FAHRENHEIT("imperial") {
        @Override
        public double fromCelsius(double value) {
            Log.d(TAG, "fromCelsius (f): "+value);
            return value * (9 / 5) + 32;
        }
    };
    private static final String TAG = "TemperatureUnit";
    private String code;

    TemperatureUnit(String code) {
        this.code = code;
    }

    public static TemperatureUnit getByCode(String code) {
        for (TemperatureUnit t : TemperatureUnit.values()) {
            if (t.code.equals(code)) {
                return t;
            }
        }
        return null;
    }

    public double fromCelsius(double value) {
        throw new IllegalStateException("invalid call");
    }


}
