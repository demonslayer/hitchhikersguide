package edu.colorado.csci5448;

import static edu.colorado.csci5448.Constants.DEF;
import static edu.colorado.csci5448.Constants.TABLE_NAME;
import static edu.colorado.csci5448.Constants.TITLE;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	
	public static void addTerm(String title, String def, Dictionary terms) {
		SQLiteDatabase db = terms.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TITLE, title);
		values.put(DEF, def);
		db.insertOrThrow(TABLE_NAME, null, values);
	}

	public static void populateInitialData(Dictionary terms) {
		Map<String,String> termsList = new HashMap<String, String>();

		try {
			AssetManager mgr = terms.getContext().getAssets();
			InputStream in = mgr.open("terms.csv");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader buff = new BufferedReader(isr);

			String title;
			String def;

			String s = "";
			while ((s = buff.readLine()) != null){
				String[] RowData = s.split(",");
				title = RowData[0];
				def = RowData[1];
				termsList.put(title, def);
			}
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set<String> keySet = termsList.keySet();
		Object[] keyObjects = keySet.toArray();

		for (Object o : keyObjects) {
			String key = o.toString();
			String defn = termsList.get(key);
			DBHelper.addTerm(key, defn, terms);
		}
	}

}
