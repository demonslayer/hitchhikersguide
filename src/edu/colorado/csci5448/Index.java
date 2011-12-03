package edu.colorado.csci5448;

import static android.provider.BaseColumns._ID;
import static edu.colorado.csci5448.Constants.TABLE_NAME;
import static edu.colorado.csci5448.Constants.DEF;
import static edu.colorado.csci5448.Constants.TITLE;

import java.util.ArrayList;
import java.util.List;

import edu.colorado.csci5448.DBHelper;
import edu.colorado.csci5448.Dictionary;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Index extends Activity {

	private Dictionary terms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);

		terms = new Dictionary(this);

		try {
			Cursor cursor = getTerms();

			showTerms(cursor);
		} finally {
			terms.close();
		}
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

	private void showTerms(Cursor cursor) {
		List<String> names = new ArrayList<String>();

		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String title = cursor.getString(1);
			names.add(title);
		}

		ArrayAdapter <CharSequence> adapter =
			new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for (String name : names) {
			adapter.add(name);
		}

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
	}

}
