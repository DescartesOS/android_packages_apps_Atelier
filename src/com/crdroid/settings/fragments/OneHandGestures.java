/*
 * Copyright (C) 2026 The DescartesOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.crdroid.settings.fragments;

import android.os.Bundle;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class OneHandGestures extends SettingsPreferenceFragment {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        addPreferencesFromResource(R.xml.onehand_gestures);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CRDROID_SETTINGS;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.onehand_gestures);
}
