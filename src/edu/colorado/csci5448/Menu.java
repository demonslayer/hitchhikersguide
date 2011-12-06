package edu.colorado.csci5448;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import static edu.colorado.csci5448.Constants.*;

public class Menu extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		View textSearchButton = findViewById(R.id.textSearch1);
		textSearchButton.setOnClickListener(this);
		
		View indexButton = findViewById(R.id.indexhButton1);
		indexButton.setOnClickListener(this);
		
		View randomTermButton = findViewById(R.id.randomTerm1);
		randomTermButton.setOnClickListener(this);
		
		View modifyButton = findViewById(R.id.modifyButton1);
		modifyButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Dictionary dictionary = new Dictionary(this);
		SQLiteDatabase db = dictionary.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + TITLE + " FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
		String term = new String();
		
		switch (v.getId()) {
		case R.id.textSearch1:
			Intent i = new Intent(this, TextSearch.class);
			startActivity(i);
			break;
		case R.id.indexhButton1:
			Intent j = new Intent(this, Index.class);
			startActivity(j);
			break;
		case R.id.randomTerm1:
			Intent k = new Intent(this, Definition.class);
			if (cursor.moveToPosition(0)) {
				term = cursor.getString(0);
			}
			k.putExtra("title", term);
			startActivity(k);
			break;
		case R.id.modifyButton1:
			Intent l = new Intent(this, Modify.class);
			startActivity(l);
			break;
		}
			
	}

}
