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
package com.matrixx.settings.fragments.ui;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import com.matrixx.settings.preferences.SystemSettingListPreference;
import com.matrixx.settings.preferences.SystemPropertySwitchPreference;
import com.android.internal.util.matrixx.ThemeUtils;
import com.android.internal.util.matrixx.SystemRestartUtils;

import java.util.List;

@SearchIndexable
public class UserInterface extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    public static final String TAG = "UserInterface";

    private static final String KEY_FONT_SETTINGS = "font_settings";
    private static final String SYS_ANI_OVERRIDE_ENABLED = "persist.sys.activity_anim_perf_override";

    private Preference mFontSettingsPref;
    private ThemeUtils mThemeUtils;
    private SystemPropertySwitchPreference mAniOverrideEnabled;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.matrixx_settings_ui);

        mThemeUtils = ThemeUtils.getInstance(getActivity());

        mFontSettingsPref = findPreference(KEY_FONT_SETTINGS);
      
        mAniOverrideEnabled = (SystemPropertySwitchPreference) findPreference(SYS_ANI_OVERRIDE_ENABLED);
        mAniOverrideEnabled.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = getContext();
        final ContentResolver resolver = context.getContentResolver();
        int value = 0;

        if (preference == mAniOverrideEnabled) {
            SystemRestartUtils.showSystemRestartDialog(getContext());
            return true;
        }

        return false;
    }

    public static void reset(Context context) {
        ContentResolver resolver = context.getContentResolver();

    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.MATRIXX;
    }

    /**
     * For search
     */
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.matrixx_settings_ui) {

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    return super.getNonIndexableKeys(context);
                }
            };
}
