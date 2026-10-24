/*
 * SPDX-FileCopyrightText: crDroid Android Project
 * SPDX-License-Identifier: GPL-3.0
 */

package com.crdroid.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.SystemProperties
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView

import androidx.lifecycle.lifecycleScope

import com.android.internal.logging.nano.MetricsProto
import com.android.settings.R
import com.android.settings.SettingsPreferenceFragment
import com.android.settings.core.SubSettingLauncher
import com.android.settingslib.widget.LayoutPreference

import com.crdroid.settings.fragments.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class crDroidSettingsLayout : SettingsPreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.crdroid_settings_title)
    }

    override fun getPreferenceScreenResId(): Int = R.xml.atelier_dashboard

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)
        findPreference<LayoutPreference>(KEY_QUICK_ACCESS)?.let { bindQuickAccess(it) }
    }

    private fun bindQuickAccess(preference: LayoutPreference) {
        preference.findViewById<TextView>(R.id.atelier_version)?.text =
            getString(R.string.atelier_version,
                SystemProperties.get("ro.descartes.version",
                    SystemProperties.get("ro.crdroid.build.version")))
        QUICK_ACCESS.forEach { (viewId, destination) ->
            preference.findViewById<android.view.View>(viewId)?.setOnClickListener {
                SubSettingLauncher(requireContext())
                    .setDestination(destination.first.name)
                    .setTitleRes(destination.second)
                    .setSourceMetricsCategory(metricsCategory)
                    .launch()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(0, MENU_RESET, 0, R.string.reset_settings_title)
            .setIcon(R.drawable.ic_reset)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_RESET) {
            showResetAllDialog(requireContext())
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showResetAllDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.reset_settings_title)
            .setMessage(R.string.reset_settings_message)
            .setPositiveButton(R.string.ok) { _, _ -> resetAll(context) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun resetAll(context: Context) {
        lifecycleScope.launch(Dispatchers.Default) {
            Buttons.reset(context)
            LockScreen.reset(context)
            Miscellaneous.reset(context)
            Navigation.reset(context)
            Notifications.reset(context)
            QuickSettings.reset(context)
            Sound.reset(context)
            StatusBar.reset(context)
            UserInterface.reset(context)

            withContext(Dispatchers.Main) {
                requireActivity().finish()
                startActivity(requireActivity().intent)
            }
        }
    }

    override fun getMetricsCategory(): Int = MetricsProto.MetricsEvent.CRDROID_SETTINGS

    companion object {
        private const val MENU_RESET = Menu.FIRST
        private const val KEY_QUICK_ACCESS = "atelier_quick_access"

        private val QUICK_ACCESS = mapOf(
            R.id.themes to (UserInterface::class.java to R.string.ui_title),
            R.id.lockscreen to (LockScreen::class.java to R.string.lockscreen_title),
            R.id.statusbar to (StatusBar::class.java to R.string.statusbar_title),
            R.id.controll to (QuickSettings::class.java to R.string.quicksettings_title)
        )
    }
}
