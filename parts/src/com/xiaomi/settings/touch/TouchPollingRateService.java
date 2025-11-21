/*
 * Copyright (C) 2023 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.touch;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

public class TouchPollingRateService extends Service {

    private static final String TAG = "XiaomiPartsTouchPollingRateService";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    private static final String SECURE_KEY_POLLING = "touch_polling_enabled";
    private Handler mHandler = new Handler();

    private final ContentObserver mSettingObserver = new ContentObserver(mHandler) {
        @Override
        public void onChange(boolean selfChange) {
            if (DEBUG) Log.d(TAG, "SettingObserver: onChange");
            updateTouchPollingRate();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) Log.d(TAG, "Creating service");
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SECURE_KEY_POLLING),
                    false, mSettingObserver, UserHandle.USER_CURRENT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        updateTouchPollingRate();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        getContentResolver().unregisterContentObserver(mSettingObserver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateTouchPollingRate() {
        final int enabled = Settings.Secure.getInt(getContentResolver(), SECURE_KEY_POLLING, 0);
        if (DEBUG) Log.d(TAG, "updateTouchPollingRate: enabled=" + enabled);
        try {
            TfWrapper.setTouchFeature(
                    new TfWrapper.TfParams(/*THP_HAL_REPORT_RATE*/ 1011, enabled));
        } catch (Exception e) {
            Log.e(TAG, "updateTouchPollingRate failed!", e);
        }
    }
}
