package edu.colorado.csci5448;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import static edu.colorado.csci5448.Constants.*;

public class Definition extends Activity implements OnClickListener{

	private String title;
	private String next;
	private String prev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definition);

		title = this.getIntent().getStringExtra("title");

		TextView textTitle = (TextView) findViewById(R.id.textTitle);
		textTitle.setText(title);

		TextView textDef = (TextView) findViewById(R.id.textDefinition);
		textDef.setMovementMethod(new ScrollingMovementMethod()); 

		Dictionary terms = new Dictionary(this);
		SQLiteDatabase db = terms.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { DEF }, "title LIKE?", new String[] {title}, null, null, null);

		if (cursor.moveToPosition(0)) {
			textDef.setText(cursor.getString(0));
		} else {
			textDef.setText("Sorry, that term was not found!");
		}

		View searchButton = findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);
		View indexButton = findViewById(R.id.indexButton);
		indexButton.setOnClickListener(this);
		View homeButton = findViewById(R.id.homeButton);
		homeButton.setOnClickListener(this);

		View prevButton = findViewById(R.id.previousButton);
		prevButton.setOnClickListener(this);
		View nextButton = findViewById(R.id.nextButton);
		nextButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Dictionary dictionary = new Dictionary(this);
		SQLiteDatabase db = dictionary.getReadableDatabase();
		String[] from = { TITLE };
		
		try {
			Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null, TITLE + " ASC");
			findNext(cursor);
			findPrev(cursor);
		} finally {
			dictionary.close();
		}

		switch (v.getId()) {
		case R.id.searchButton:
			Intent i = new Intent(this, TextSearch.class);
			startActivity(i);
			break;
		case R.id.indexButton:
			Intent j = new Intent(this, Index.class);
			startActivity(j);
			break;
		case R.id.homeButton:
			Intent k = new Intent(this, Menu.class);
			startActivity(k);
			break;
		case R.id.nextButton:
			Intent l = new Intent(this, Definition.class);
			l.putExtra("title", next);
			startActivity(l);
			break;
		case R.id.previousButton:
			Intent m = new Intent(this, Definition.class);
			m.putExtra("title", prev);
			startActivity(m);
			break;
		} 
	}

	private void findNext(Cursor cursor) {
		String current = title;

		String thisString = new String();

		while(cursor.moveToNext()) {
			thisString = cursor.getString(0);

			if (thisString.compareTo(current) > 0) {
				this.next = thisString;
				return;
			}

		}
	}

	private void findPrev(Cursor cursor) {
		String current = title;

		String thisString = new String();
		String lastString = new String();

		while(cursor.moveToNext()) {
			thisString = cursor.getString(0);
			this.prev = current;

			if (thisString.compareTo(current) == 0) {
				this.prev = lastString;
			} else {
				return;
			}
			
			lastString = thisString;
		}
	}

}
