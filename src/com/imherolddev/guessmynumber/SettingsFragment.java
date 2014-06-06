package com.imherolddev.guessmynumber;

import java.util.Arrays;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener, OnPreferenceChangeListener {

	Preference minPref;
	Preference maxPref;
	Preference diffPref;

	EditTextPreference minimum;
	EditTextPreference maximum;

	SharedPreferences sharedPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.fragment_settings);

		minPref = findPreference(getString(R.string.pref_min_key));
		maxPref = findPreference(getString(R.string.pref_max_key));
		diffPref = findPreference(getString(R.string.pref_diff_key));

		minimum = (EditTextPreference) minPref;
		maximum = (EditTextPreference) maxPref;
		minimum.setOnPreferenceChangeListener(this);
		maximum.setOnPreferenceChangeListener(this);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		Resources res = getActivity().getResources();
		String[] difficulty = res.getStringArray(R.array.difficulty);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		if (key.equals(getString(R.string.pref_diff_key))) {

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
		}

		editor.putBoolean(getString(R.string.pref_is_guessed), false);
		editor.putInt(getString(R.string.pref_num_guesses), 0);
		editor.commit();

		minimum.setText(sharedPreferences.getString(
				getString(R.string.pref_min_key), getString(R.string.zero)));
		maximum.setText(sharedPreferences.getString(
				getString(R.string.pref_max_key),
				getString(R.string.twentyFive)));

		minPref.setSummary(sharedPreferences.getString(
				getString(R.string.pref_min_key), getString(R.string.zero)));
		maxPref.setSummary(sharedPreferences.getString(
				getString(R.string.pref_max_key),
				getString(R.string.twentyFive)));
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

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {

		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		int min = Integer.parseInt(sharedPrefs.getString(
				getString(R.string.pref_min_key), getString(R.string.zero)));
		int max = Integer.parseInt(sharedPrefs.getString(
				getString(R.string.pref_max_key),
				getString(R.string.twentyFive)));
		int change = Integer.parseInt((String) newValue);
		int saved;

		if (preference == minPref) {

			saved = Integer.parseInt(sharedPrefs.getString(
					getString(R.string.pref_max_key),
					getString(R.string.twentyFive)));

			if (change < saved) {

				if ((max - change) >= 5) {

					return true;

				} else {

					this.toast(getString(R.string.warn_diff));
					return false;

				}

			} else {
				this.toast(getString(R.string.high));
				return false;
			}

		} else if (preference == maxPref) {

			saved = Integer.parseInt(sharedPrefs.getString(
					getString(R.string.pref_min_key),
					getString(R.string.twentyFive)));

			if (change > saved) {

				if ((change - min) >= 5) {

					return true;

				} else {

					this.toast(getString(R.string.warn_diff));
					return false;

				}

			} else {
				this.toast(getString(R.string.low));
				return false;
			}

		} else {

			return false;

		}

	}

	private void toast(String msg) {

		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

	}

}
