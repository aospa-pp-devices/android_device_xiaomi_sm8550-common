/*
 * Copyright (C) 2026 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.advancedbattery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

import androidx.preference.PreferenceManager;

/**
 * Suspends charging when the battery is charged to the configured percentage.
 *
 * The service watches the battery level and sets the
 * vendor.xiaomi.parts.input_suspend property when the limit is reached; init
 * applies the property to /sys/class/qcom-battery/input_suspend. Charging
 * resumes once the battery has dropped a couple of percent below the limit.
 */
public class ChargingControlService extends Service {

    private static final String TAG = "XiaomiPartsChargingControl";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    public static final String PREF_KEY_ENABLED = "charging_limit_enabled";
    public static final String PREF_KEY_LIMIT = "charging_limit";
    public static final int DEFAULT_LIMIT = 80;

    /** Battery percentage points below the limit before charging resumes. */
    private static final int RESUME_MARGIN = 2;

    private final BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateChargingSuspend(intent);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) Log.d(TAG, "Creating service");
        registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        unregisterReceiver(mBatteryReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateChargingSuspend(Intent intent) {
        final SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean(PREF_KEY_ENABLED, false)) {
            return;
        }
        final int limit = prefs.getInt(PREF_KEY_LIMIT, DEFAULT_LIMIT);
        final int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        final int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level < 0 || scale <= 0) {
            return;
        }
        final int pct = level * 100 / scale;
        if (DEBUG) Log.d(TAG, "Battery " + pct + "%, limit " + limit + "%");
        if (pct >= limit) {
            ChargingUtils.setChargingSuspended(true);
        } else if (pct <= limit - RESUME_MARGIN) {
            ChargingUtils.setChargingSuspended(false);
        }
    }
}
