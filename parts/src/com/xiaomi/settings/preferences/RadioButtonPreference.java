package com.xiaomi.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.CheckBoxPreference;

import com.xiaomi.settings.R;

/**
 * Check box preference with check box replaced by radio button.
 * <p>
 * Functionally speaking, it's actually a CheckBoxPreference. We only modified
 * the widget to RadioButton to make it "look like" a RadioButtonPreference.
 * <p>
 * In other words, there's no "RadioButtonPreferenceGroup" in this
 * implementation. When you check one RadioButtonPreference, if you want to
 * uncheck all the other preferences, you should do that by code yourself.
 */

public class RadioButtonPreference extends CheckBoxPreference {

    public RadioButtonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWidgetLayoutResource(R.layout.preference_widget_radiobutton);
    }

    public RadioButtonPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context,
                androidx.preference.R.attr.preferenceStyle,
                android.R.attr.preferenceStyle));
    }

    public RadioButtonPreference(Context context) {
        this(context, null);
    }
}
