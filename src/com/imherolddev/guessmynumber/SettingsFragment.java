package com.imherolddev.guessmynumber;

import java.util.Arrays;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	Preference minPref;
	Preference maxPref;
	Preference diffPref;

	EditTextPreference minimum;
	EditTextPreference maximum;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.fragment_settings);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		Resources res = getActivity().getResources();
		String[] difficulty = res.getStringArray(R.array.difficulty);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putBoolean(getString(R.string.pref_is_guessed), false);
		editor.putInt(getString(R.string.pref_num_guesses), 0);

		switch (Arrays.asList(difficulty).indexOf(
				sharedPreferences.getString(key,
						getString(R.string.pref_diff_key)))) {

		case 0:
			editor.putString(getString(R.string.pref_min_key),
					getString(R.string.zero));
			editor.putString(getString(R.string.pref_max_key),
					getString(R.string.twentyFive));
			editor.commit();
			break;

		case 1:
			editor.putString(getString(R.string.pref_min_key),
					getString(R.string.zero));
			editor.putString(getString(R.string.pref_max_key),
					getString(R.string.fifty));
			editor.commit();
			break;

		case 2:
			editor.putString(getString(R.string.pref_min_key),
					getString(R.string.zero));
			editor.putString(getString(R.string.pref_max_key),
					getString(R.string.hundred));
			editor.commit();
			break;

		default:
			break;

		}

		minPref = findPreference(getString(R.string.pref_min_key));
		minPref.setSummary(sharedPreferences.getString(
				getString(R.string.pref_min_key), getString(R.string.zero)));

		maxPref = findPreference(getString(R.string.pref_max_key));
		maxPref.setSummary(sharedPreferences.getString(
				getString(R.string.pref_max_key),
				getString(R.string.twentyFive)));

		diffPref = findPreference(getString(R.string.pref_diff_key));
		diffPref.setSummary(sharedPreferences.getString(
				getString(R.string.pref_diff_key), difficulty[0]));

	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

}
