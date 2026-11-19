package com.xiaomi.settings.advancedbattery;

import android.os.Bundle;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class AdvancedBatteryActivity extends CollapsingToolbarBaseActivity {

    private static final String TAG_ADVANCED_BATTERY = "advanced_battery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(com.android.settingslib.collapsingtoolbar.R.id.content_frame,
                new AdvancedBatteryFragment(), TAG_ADVANCED_BATTERY).commit();
    }
}
