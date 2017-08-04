package me.iacn.mbestyle.ui.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/8/4
 * Emali i@iacn.me
 */

public class LicenseActivity extends PreferenceActivity {

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.license);
    }
}