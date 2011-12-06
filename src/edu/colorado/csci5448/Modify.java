package edu.colorado.csci5448;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Modify extends Activity implements OnClickListener {
	
	private EditText termName;
	private EditText termDef;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify);
		
		termName = (EditText) findViewById(R.id.termNameText);
		termDef = (EditText) findViewById(R.id.termDescriptionText);
		
		View submitButton = findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);
		View searchButton = findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);
		View indexButton = findViewById(R.id.indexButton);
		indexButton.setOnClickListener(this);
		View homeButton = findViewById(R.id.homeButton);
		homeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitButton:
			Dictionary terms = new Dictionary(this);
			String name = termName.getText().toString();
			String def = termDef.getText().toString();
			DBHelper.addTerm(name, def, terms);
			
			Intent h = new Intent(this, Definition.class);
			h.putExtra("title", name);
			startActivity(h);
			
			break;
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
		}
	}

}
