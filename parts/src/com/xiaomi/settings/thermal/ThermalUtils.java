package com.xiaomi.settings.thermal;

import android.util.Log;

import com.xiaomi.settings.utils.FileUtils;
import com.xiaomi.settings.R;

public class ThermalUtils {

    private static final String TAG = "ThermalUtils";

    public static final String THERMAL_SCONFIG = "/sys/class/thermal/thermal_message/sconfig";

    public static int getCurrentThermalMode() {
        String line = FileUtils.readLine(THERMAL_SCONFIG);
        if (line != null) {
            try {
                int value = Integer.parseInt(line.trim());
                switch (value) {
                    case 0: return 0; // Default
                    case 6: return 1; // Performance
                    case 19: return 2; // Gaming
                    case 1: return 3; // Battery Saver
                    default: return 4; // Unknown mode
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing thermal mode value: ", e);
            }
        }
        return 4; // Treat invalid or missing values as Unknown
    }

    public static void setThermalMode(int mode) {
        int thermalValue;
        switch (mode) {
            case 0: thermalValue = 0; break;  // Default
            case 1: thermalValue = 6; break;  // Performance
            case 2: thermalValue = 16; break; // Gaming
            case 3: thermalValue = 1; break;  // Battery Saver
            default: thermalValue = 0; break; // Reset to Default for Unknown
        }
        FileUtils.writeLine(THERMAL_SCONFIG, String.valueOf(thermalValue));
    }
}
