package edu.colorado.csci5448;

import static android.provider.BaseColumns._ID;
import static edu.colorado.csci5448.Constants.*;
import static edu.colorado.csci5448.Constants.DEF;
import static edu.colorado.csci5448.Constants.TITLE;

import edu.colorado.csci5448.DBHelper;
import edu.colorado.csci5448.Dictionary;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Hitchhikersguide extends Activity implements OnClickListener {
	private Dictionary terms;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		terms = new Dictionary(this);

		try {
			Cursor cursor = getTerms();
			if (cursor.getCount() == 0) {
				DBHelper.populateInitialData(terms);
				cursor = getTerms();
			}
			
		} finally {
			terms.close();
		}

		View startButton = findViewById(R.id.dontpanic);
		startButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dontpanic:
			Intent i = new Intent(this, Menu.class);
			startActivity(i);
			break;
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
}