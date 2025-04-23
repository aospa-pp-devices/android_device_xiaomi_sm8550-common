/*
 * Copyright (C) 2023-2024 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Display.HdrCapabilities;

import com.xiaomi.settings.display.ColorModeService;
import com.xiaomi.settings.display.DcDimmingService;
import com.xiaomi.settings.touch.TouchOrientationService;
import com.xiaomi.settings.touch.TouchPollingRateService;
import com.xiaomi.settings.thermal.ThermalUtils;

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final String TAG = "XiaomiParts";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    private static final String PREF_NAME = "thermal_prefs";
    private static final String PREF_KEY = "thermal_mode_pref";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (DEBUG) Log.d(TAG, "Received boot completed intent: " + intent.getAction());
        switch (intent.getAction()) {
            case Intent.ACTION_LOCKED_BOOT_COMPLETED:
                onLockedBootCompleted(context);
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                onBootCompleted(context);
                break;
        }
    }

    private static void onLockedBootCompleted(Context context) {
        // Display
        context.startServiceAsUser(new Intent(context, ColorModeService.class),
                UserHandle.CURRENT);
        context.startServiceAsUser(new Intent(context, DcDimmingService.class),
                UserHandle.CURRENT);

        // Touchscreen
        context.startServiceAsUser(new Intent(context, TouchOrientationService.class),
                UserHandle.CURRENT);
        context.startServiceAsUser(new Intent(context, TouchPollingRateService.class),
                UserHandle.CURRENT);

        // Thermal
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int mode = prefs.getInt(PREF_KEY, 0); // Default to 0 if unset

        Log.d(TAG, "Applying thermal mode on boot: " + mode);
        ThermalUtils.setThermalMode(mode);

        // Override HDR types to enable Dolby Vision
        final DisplayManager displayManager = context.getSystemService(DisplayManager.class);
        displayManager.overrideHdrTypes(Display.DEFAULT_DISPLAY, new int[]{
                HdrCapabilities.HDR_TYPE_DOLBY_VISION, HdrCapabilities.HDR_TYPE_HDR10,
                HdrCapabilities.HDR_TYPE_HLG, HdrCapabilities.HDR_TYPE_HDR10_PLUS});
    }

    private static void onBootCompleted(Context context) {
    }
}
