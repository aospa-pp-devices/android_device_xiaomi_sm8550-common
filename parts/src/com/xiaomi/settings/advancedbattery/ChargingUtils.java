/*
 * Copyright (C) 2026 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.advancedbattery;

import android.util.Log;

import com.xiaomi.settings.utils.FileUtils;

public class ChargingUtils {

    private static final String TAG = "ChargingUtils";

    /**
     * Holds the charge at the current level while keeping the charger connected.
     *
     * The charger stays online, so the framework does not see a plug/unplug transition and does
     * not wake the screen. Suspending the input instead would make the charger disappear and
     * reappear, which wakes the display on devices with config_unplugTurnsOnScreen set.
     */
    public static final String NIGHT_CHARGING = "/sys/class/qcom-battery/night_charging";

    public static boolean isChargingSuspended() {
        String line = FileUtils.readLine(NIGHT_CHARGING);
        return line != null && line.trim().equals("1");
    }

    public static void setChargingSuspended(boolean suspend) {
        String value = suspend ? "1" : "0";
        if (value.equals(FileUtils.readLine(NIGHT_CHARGING))) {
            return;
        }
        Log.i(TAG, "night_charging -> " + value);
        FileUtils.writeLine(NIGHT_CHARGING, value);
    }
}
