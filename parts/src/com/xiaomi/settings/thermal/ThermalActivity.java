package com.xiaomi.settings.thermal;

import android.os.Bundle;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class ThermalActivity extends CollapsingToolbarBaseActivity {

    private static final String TAG_THERMAL = "thermal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(com.android.settingslib.collapsingtoolbar.R.id.content_frame,
                new ThermalFragment(), TAG_THERMAL).commit();
    }
}
