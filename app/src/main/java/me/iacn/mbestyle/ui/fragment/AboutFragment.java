package me.iacn.mbestyle.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class AboutFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference version = findPreference("version");
        version.setSummary(BuildConfig.VERSION_NAME);
    }
}