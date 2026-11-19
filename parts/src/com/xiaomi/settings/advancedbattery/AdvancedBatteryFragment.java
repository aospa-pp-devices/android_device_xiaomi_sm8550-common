package com.xiaomi.settings.advancedbattery;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settingslib.widget.SettingsBasePreferenceFragment;
import com.android.settingslib.widget.SelectorWithWidgetPreference;
import com.xiaomi.settings.R;

import java.util.Map;

public class AdvancedBatteryFragment extends SettingsBasePreferenceFragment {

    private static final String PREF_KEY_DEFAULT = "thermal_mode_default";
    private static final String PREF_KEY_PERFORMANCE = "thermal_mode_performance";
    private static final String PREF_KEY_GAMING = "thermal_mode_gaming";
    private static final String PREF_KEY_POWERSAVE = "thermal_mode_powersave";
    private static final String PREF_THERMAL_MODE = "thermal_mode_pref";

    private final Map<String, Integer> keyToMode = Map.of(
        PREF_KEY_DEFAULT, 0,
        PREF_KEY_PERFORMANCE, 1,
        PREF_KEY_GAMING, 2,
        PREF_KEY_POWERSAVE, 3
    );

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.advanced_battery_preferences, rootKey);

        for (String key : keyToMode.keySet()) {
            SelectorWithWidgetPreference pref = findPreference(key);
            if (pref != null) {
                pref.setOnClickListener(preference -> onThermalModeSelected(key));
            }
        }

        restoreSelectedMode();
        setupChargingControl();
    }

    private void onThermalModeSelected(String selectedKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int mode = keyToMode.get(selectedKey);
        prefs.edit().putInt(PREF_THERMAL_MODE, mode).apply();
        ThermalUtils.setThermalMode(mode);

        for (String key : keyToMode.keySet()) {
            SelectorWithWidgetPreference pref = findPreference(key);
            if (pref != null) {
                pref.setChecked(key.equals(selectedKey));
            }
        }
    }

    private void restoreSelectedMode() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int savedMode = prefs.getInt(PREF_THERMAL_MODE, 0);
        for (Map.Entry<String, Integer> entry : keyToMode.entrySet()) {
            SelectorWithWidgetPreference pref = findPreference(entry.getKey());
            if (pref != null) {
                pref.setChecked(entry.getValue() == savedMode);
            }
        }
    }

    private void setupChargingControl() {
        final SwitchPreferenceCompat enabledPref =
                findPreference(ChargingControlService.PREF_KEY_ENABLED);
        final SeekBarPreference limitPref =
                findPreference(ChargingControlService.PREF_KEY_LIMIT);
        if (enabledPref == null || limitPref == null) {
            return;
        }

        updateChargingLimitSummary(limitPref.getValue());
        limitPref.setEnabled(enabledPref.isChecked());

        enabledPref.setOnPreferenceChangeListener((preference, newValue) -> {
            final boolean enabled = (Boolean) newValue;
            final Context context = getContext();
            if (enabled) {
                context.startService(new Intent(context, ChargingControlService.class));
            } else {
                context.stopService(new Intent(context, ChargingControlService.class));
                ChargingUtils.setChargingSuspended(false);
            }
            limitPref.setEnabled(enabled);
            return true;
        });

        limitPref.setOnPreferenceChangeListener((preference, newValue) -> {
            updateChargingLimitSummary((Integer) newValue);
            return true;
        });
    }

    private void updateChargingLimitSummary(int limit) {
        final SeekBarPreference limitPref =
                findPreference(ChargingControlService.PREF_KEY_LIMIT);
        if (limitPref != null) {
            limitPref.setSummary(getString(R.string.charging_limit_seekbar_summary, limit));
        }
    }
}
