/*
 * Copyright (C) 2022-2026 Project Matrixx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.matrixx.settings.fragments.statusbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import java.util.List;

import lineageos.preference.LineageSystemSettingListPreference;
import com.matrixx.settings.preferences.SystemSettingSwitchPreference;
import com.matrixx.settings.utils.DeviceUtils;

@SearchIndexable
public class StatusBar extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "StatusBar";
    private static final String QUICK_PULLDOWN = "qs_quick_pulldown";

    private static final int PULLDOWN_DIR_NONE = 0;
    private static final int PULLDOWN_DIR_RIGHT = 1;
    private static final int PULLDOWN_DIR_LEFT = 2;
    private static final int PULLDOWN_DIR_ALWAYS = 3;

    private LineageSystemSettingListPreference mQuickPulldown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.matrixx_settings_statusbar);

        final Context context = getContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        final Resources resources = context.getResources();

        mQuickPulldown =
        (LineageSystemSettingListPreference) findPreference(QUICK_PULLDOWN);

        if (mQuickPulldown != null) {
            mQuickPulldown.setOnPreferenceChangeListener(this);
            updateQuickPulldownSummary(mQuickPulldown.getIntValue(0));

    // RTL support
    if (resources.getConfiguration().getLayoutDirection()
            == android.view.View.LAYOUT_DIRECTION_RTL) {
        mQuickPulldown.setEntries(
                R.array.status_bar_quick_qs_pulldown_entries_rtl);
        mQuickPulldown.setEntryValues(
                R.array.status_bar_quick_qs_pulldown_values_rtl);
            }
        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = getContext();
        final ContentResolver resolver = context.getContentResolver();
        if (preference == mQuickPulldown) {
            int value = Integer.parseInt((String) newValue);
            updateQuickPulldownSummary(value);
            return true;
    }

        return false;
    }

    private void updateQuickPulldownSummary(int value) {
    String summary = "";

    switch (value) {
        case PULLDOWN_DIR_NONE:
            summary = getResources().getString(
                    R.string.status_bar_quick_qs_pulldown_off);
            break;

        case PULLDOWN_DIR_ALWAYS:
            summary = getResources().getString(
                    R.string.status_bar_quick_qs_pulldown_always);
            break;

        case PULLDOWN_DIR_LEFT:
        case PULLDOWN_DIR_RIGHT:
            summary = getResources().getString(
                    R.string.status_bar_quick_qs_pulldown_summary,
                    getResources().getString(
                            value == PULLDOWN_DIR_LEFT
                                    ? R.string.status_bar_quick_qs_pulldown_summary_left
                                    : R.string.status_bar_quick_qs_pulldown_summary_right));
            break;
        }

        mQuickPulldown.setSummary(summary);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.MATRIXX;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.matrixx_settings_statusbar) {

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources resources = context.getResources();

                return keys;
            }
        };
}
