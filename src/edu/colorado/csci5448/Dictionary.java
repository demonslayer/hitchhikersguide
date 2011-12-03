package edu.colorado.csci5448;

import static android.provider.BaseColumns._ID;
import static edu.colorado.csci5448.Constants.DEF;
import static edu.colorado.csci5448.Constants.TABLE_NAME;
import static edu.colorado.csci5448.Constants.TITLE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dictionary extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "dictionary.db";
	private static final int DATABASE_VERSION = 1;
	
	private Context context;

	public Dictionary(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + _ID
		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE
		+ " TEXT NOT NULL, " + DEF + " BLOB NOT NULL);";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public Context getContext() {
		return context;
	}

}
