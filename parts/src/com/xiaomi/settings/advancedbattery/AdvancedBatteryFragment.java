package com.xiaomi.settings.advancedbattery;

import android.os.Bundle;

import com.android.settingslib.widget.SettingsBasePreferenceFragment;
import com.xiaomi.settings.R;

public class AdvancedBatteryFragment extends SettingsBasePreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.advanced_battery_preferences, rootKey);
    }
}
