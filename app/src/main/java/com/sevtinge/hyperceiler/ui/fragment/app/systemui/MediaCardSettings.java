package com.sevtinge.hyperceiler.ui.fragment.app.systemui;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.sevtinge.hyperceiler.R;
import com.sevtinge.hyperceiler.ui.fragment.dashboard.DashboardFragment;
import com.sevtinge.hyperceiler.utils.prefs.PrefsUtils;

import fan.preference.ColorPickerPreference;
import fan.preference.DropDownPreference;
import fan.preference.SeekBarPreferenceCompat;

public class MediaCardSettings extends DashboardFragment implements Preference.OnPreferenceChangeListener {
    SwitchPreference mRemoveMediaCardBackFix;
    SwitchPreference mRemoveMediaCardBack;
    DropDownPreference mProgressMode;
    SeekBarPreferenceCompat mProgressModeThickness;
    SeekBarPreferenceCompat mProgressModeCornerRadius;
    ColorPickerPreference mSliderColor;
    ColorPickerPreference mProgressBarColor;

    @Override
    public int getPreferenceScreenResId() {
        return R.xml.system_ui_control_center_media_cards;
    }

    @Override
    public void initPrefs() {
        boolean mRemoveMediaCardBackground = PrefsUtils.mSharedPreferences.getBoolean("prefs_key_system_ui_control_center_remove_media_control_panel_background", false);

        mRemoveMediaCardBackFix = findPreference("prefs_key_system_ui_control_center_media_control_panel_background_mix");
        mRemoveMediaCardBack = findPreference("prefs_key_system_ui_control_center_remove_media_control_panel_background");
        mProgressMode = findPreference("prefs_key_system_ui_control_center_media_control_progress_mode");
        mProgressModeThickness = findPreference("prefs_key_system_ui_control_center_media_control_progress_thickness");
        mProgressModeCornerRadius = findPreference("prefs_key_system_ui_control_center_media_control_progress_corner_radius");
        mSliderColor = findPreference("prefs_key_system_ui_control_center_media_control_seekbar_thumb_color");
        mProgressBarColor = findPreference("prefs_key_system_ui_control_center_media_control_seekbar_color");

        mProgressModeThickness.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) == 2);
        mProgressModeCornerRadius.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) == 2);

        if (mRemoveMediaCardBackground) {
            mSliderColor.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) != 2);
            mProgressBarColor.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) != 2);
        } else {
            mSliderColor.setVisible(true);
            mProgressBarColor.setVisible(true);
        }

        mRemoveMediaCardBackFix.setOnPreferenceChangeListener((preference, o) -> {
            if (!(boolean) o) {
                mRemoveMediaCardBack.setChecked(false);
                mSliderColor.setVisible(true);
                mProgressBarColor.setVisible(true);
            }
            return true;
        });
        mRemoveMediaCardBack.setOnPreferenceChangeListener((preference, o) -> {
            if ((boolean) o) {
                mSliderColor.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) != 2);
                mProgressBarColor.setVisible(Integer.parseInt(PrefsUtils.mSharedPreferences.getString("prefs_key_system_ui_control_center_media_control_progress_mode", "0")) != 2);
            } else {
                mSliderColor.setVisible(true);
                mProgressBarColor.setVisible(true);
            }
            return true;
        });

        mProgressMode.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object o) {
        if (preference == mProgressMode) {
            setCanBeVisibleProgressMode(Integer.parseInt((String) o));
        }
        return true;
    }

    private void setCanBeVisibleProgressMode(int mode) {
        boolean mRemoveMediaCardBackground = PrefsUtils.mSharedPreferences.getBoolean("prefs_key_system_ui_control_center_remove_media_control_panel_background", false);

        mProgressModeThickness.setVisible(mode == 2);
        mProgressModeCornerRadius.setVisible(mode == 2);
        if (mRemoveMediaCardBackground) {
            mSliderColor.setVisible(mode != 2);
            mProgressBarColor.setVisible(mode != 2);
        } else {
            mSliderColor.setVisible(true);
            mProgressBarColor.setVisible(true);
        }
    }
}