/*
 * Copyright (C) 2024-2026 Lunaris AOSP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crdroid.settings.fragments.statusbar

import android.content.Context
import android.os.Bundle
import android.os.UserHandle
import android.provider.Settings

import com.android.internal.logging.nano.MetricsProto

import com.android.settings.R
import com.android.settings.SettingsPreferenceFragment
import com.android.settings.search.BaseSearchIndexProvider
import com.android.settingslib.search.SearchIndexable

@SearchIndexable
class DynamicIslandSettings : SettingsPreferenceFragment() {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        addPreferencesFromResource(R.xml.dynamic_island_settings)
    }

    override fun getMetricsCategory(): Int = MetricsProto.MetricsEvent.CRDROID_SETTINGS

    companion object {
        @JvmStatic
        fun reset(context: Context) {
            val resolver = context.contentResolver
            Settings.System.putIntForUser(resolver,
                "status_bar_show_dynamic_island", 0, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_width_scale", 100, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_height_scale", 100, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_popup_color_mode", 0, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_media_controls", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_screen_recording", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_alarms", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_flashlight", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_stopwatch", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_live_scores", 1, UserHandle.USER_CURRENT)
            Settings.System.putIntForUser(resolver,
                "status_bar_dynamic_island_lyrics", 0, UserHandle.USER_CURRENT)
        }

        @JvmField
        val SEARCH_INDEX_DATA_PROVIDER = BaseSearchIndexProvider(R.xml.dynamic_island_settings)
    }
}
