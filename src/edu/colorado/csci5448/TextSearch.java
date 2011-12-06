package edu.colorado.csci5448;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class TextSearch extends Activity implements OnClickListener {
	
	private EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textsearch);
		
		text = (EditText) findViewById(R.id.autoCompleteTextView1);
		
		View searchButton = findViewById(R.id.searchButton1);
		searchButton.setOnClickListener(this);
		
		View homeButton = findViewById(R.id.home1);
		homeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String term = text.getText().toString();
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
