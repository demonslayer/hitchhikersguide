package edu.colorado.csci5448;

import static android.provider.BaseColumns._ID;
import static edu.colorado.csci5448.Constants.DEF;
import static edu.colorado.csci5448.Constants.TABLE_NAME;
import static edu.colorado.csci5448.Constants.TITLE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Index extends Activity implements OnClickListener {

	private Dictionary terms;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		terms = new Dictionary(this);
		spinner = (Spinner) findViewById(R.id.spinner1);

		try {
			Cursor cursor = getTerms();
			populateSpinner(cursor, spinner);
		} finally {
			terms.close();
		}
				
		View searchButton = findViewById(R.id.searchButton1);
		searchButton.setOnClickListener(this);
		
		View homeButton = findViewById(R.id.home1);
		homeButton.setOnClickListener(this);
	}

	private static String[] FROM = { _ID, TITLE, DEF };
	private static String ORDER_BY = TITLE + " ASC";
	private Cursor getTerms() {
		SQLiteDatabase db = terms.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null,
				ORDER_BY);
		startManagingCursor(cursor);
		return cursor;
	}

	private void populateSpinner(Cursor cursor, Spinner spinner) {
		List<String> names = new ArrayList<String>();

		while (cursor.moveToNext()) {
			String title = cursor.getString(1);
			names.add(title);
		}

		ArrayAdapter <CharSequence> adapter =
			new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for (String name : names) {
			adapter.add(name);
		}
		spinner.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		String term = spinner.getSelectedItem().toString();
		
		switch(v.getId()) {
		case R.id.searchButton1:
			Intent i = new Intent(this, Definition.class);
			i.putExtra("title", term);
			startActivity(i);
			break;
		case R.id.home1:
			Intent j = new Intent(this, Menu.class);
			startActivity(j);
			break;
		}
	}

}
