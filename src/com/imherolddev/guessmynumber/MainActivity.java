package com.imherolddev.guessmynumber;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	SharedPreferences sharedPrefs;
	private NumberGenerator gen = new NumberGenerator();
	private Comparator check = new Comparator(this);

	private String minGuess;
	private String maxGuess;
	private String number;
	private int numGuesses = 0;
	private boolean isGuessed = false;

	private TextView tv_guess_tagline_cont;
	private EditText et_guess;
	private Button btn_guess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public View onCreatePanelView(int featureId) {

		this.et_guess = (EditText) findViewById(R.id.et_guess);
		this.btn_guess = (Button) findViewById(R.id.btn_guess);

		return null;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.action_settings:
			Intent settings = new Intent(this, SettingsActivity.class);
			startActivity(settings);
			break;

		case R.id.action_about:
			Intent about = new Intent(this, AboutActivity.class);
			startActivity(about);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {

		super.onResume();

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		this.minGuess = sharedPrefs.getString(getString(R.string.pref_min_key),
				getString(R.string.zero));
		this.maxGuess = sharedPrefs.getString(getString(R.string.pref_max_key),
				getString(R.string.twentyFive));
		this.isGuessed = sharedPrefs.getBoolean(
				getString(R.string.pref_is_guessed), false);

		if (!isGuessed) {

			gen.makeNumber(Integer.parseInt(minGuess),
					Integer.parseInt(maxGuess));
			this.number = gen.toString();

		} else {

			this.number = sharedPrefs.getString(getString(R.string.number_key),
					getString(R.string.zero));
			this.numGuesses = sharedPrefs.getInt(
					getString(R.string.pref_num_guesses), 0);

		}

		this.updateTagLine();

	}

	@Override
	protected void onPause() {

		super.onPause();
		Editor editor = sharedPrefs.edit();
		if (isGuessed) {

			editor.putBoolean(getString(R.string.pref_is_guessed),
					this.isGuessed);
			editor.putInt(getString(R.string.pref_num_guesses), this.numGuesses);
			editor.putString(getString(R.string.number_key), this.number);
			editor.commit();

		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

	}

	private void updateTagLine() {

		String range;
		this.tv_guess_tagline_cont = (TextView) findViewById(R.id.tv_guess_tagline_cont);

		range = getString(R.string.guess_tagline_cont)
				+ " "
				+ sharedPrefs.getString(getString(R.string.pref_min_key),
						getString(R.string.zero))
				+ " "
				+ getString(R.string.and)
				+ " "
				+ sharedPrefs.getString(getString(R.string.pref_max_key),
						getString(R.string.twentyFive));

		this.tv_guess_tagline_cont.setText(range);

	}

	public void showAnswer(View v) {

		CheckBox show = (CheckBox) findViewById(R.id.cb_show);

		if (show.isChecked()) {

			this.toast(number);

		}

	}

	public void compareGuess(View v) {

		this.numGuesses++;
		this.isGuessed = true;
		this.btn_guess.setText(getString(R.string.guess_again_btn));

		if (!this.et_guess.getText().toString().equals("")) {

			String guess = this.et_guess.getText().toString();
			String check = this.check.compare(Integer.parseInt(this.number),
					Integer.parseInt(guess));
			String msg = getString(R.string.toast_guess) + " ";
			String guesses = " (" + String.valueOf(this.numGuesses) + " "
					+ getString(R.string.guesses);

			if (check.equals(getString(R.string.high))) {

				this.toast(msg + getString(R.string.high) + guesses + ")");

			} else if (check.equals(getString(R.string.low))) {

				this.toast(msg + getString(R.string.low) + guesses + ")");

			} else if (check.equals(getString(R.string.correct))) {

				this.isGuessed = false;
				this.numGuesses = 0;
				this.btn_guess.setText(getString(R.string.guess_btn));
				this.toast(msg + getString(R.string.correct) + guesses + ")");
				gen.makeNumber(Integer.parseInt(minGuess),
						Integer.parseInt(maxGuess));
				this.number = gen.toString();
				this.et_guess.setText("");

			}
		} else {

			this.toast(getString(R.string.no_number));

		}

	}

	private void toast(String msg) {

		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}

}
