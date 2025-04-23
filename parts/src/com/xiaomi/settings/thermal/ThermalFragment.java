package com.xiaomi.settings.thermal;

import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;

import com.xiaomi.settings.preferences.RadioButtonPreference;
import com.xiaomi.settings.R;

import java.util.Map;

public class ThermalFragment extends PreferenceFragment {

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
        setPreferencesFromResource(R.xml.thermal_preferences, rootKey);

        for (String key : keyToMode.keySet()) {
            RadioButtonPreference pref = findPreference(key);
            if (pref != null) {
                pref.setOnPreferenceClickListener(preference -> {
                    onThermalModeSelected(key);
                    return true;
                });
            }
        }

        restoreSelectedMode();
    }

    private void onThermalModeSelected(String selectedKey) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int mode = keyToMode.get(selectedKey);
        prefs.edit().putInt(PREF_THERMAL_MODE, mode).apply();
        ThermalUtils.setThermalMode(mode);

        for (String key : keyToMode.keySet()) {
            RadioButtonPreference pref = findPreference(key);
            if (pref != null) {
                pref.setChecked(key.equals(selectedKey));
            }
        }
    }

    private void restoreSelectedMode() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int savedMode = prefs.getInt(PREF_THERMAL_MODE, 0);
        for (Map.Entry<String, Integer> entry : keyToMode.entrySet()) {
            RadioButtonPreference pref = findPreference(entry.getKey());
            if (pref != null) {
                pref.setChecked(entry.getValue() == savedMode);
            }
        }
    }
}
