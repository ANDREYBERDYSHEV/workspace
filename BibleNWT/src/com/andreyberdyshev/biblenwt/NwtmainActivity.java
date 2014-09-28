package com.andreyberdyshev.biblenwt;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NwtmainActivity extends Activity {

	String[] Evr1;
	String[] Grec1;

	ListView lvEvr1;
	ListView lvGrec1;
	MyDatabase dbHelper1;
	final int MENU_FIND = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nwtmain);

		dbHelper1 = new MyDatabase(this);

		SQLiteDatabase db = dbHelper1.getWritableDatabase();
		db.close();
		dbHelper1.close();
		ListView lvEvr1 = (ListView) findViewById(R.id.lvEvr);
		ListView lvGrec1 = (ListView) findViewById(R.id.lvGrec);
		Evr1 = getResources().getStringArray(R.array.Evr);
		Grec1 = getResources().getStringArray(R.array.Grec);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.my_lay_list, Evr1);
		// ����������� ������� ������
		lvEvr1.setAdapter(adapter);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.my_lay_list, Grec1);
		// ����������� ������� ������
		lvGrec1.setAdapter(adapter2);

		// lvEvr1.setOnItemLongClickListener(new OnItemLongClickListener() {
		lvEvr1.setOnItemClickListener(new OnItemClickListener() {
			// public boolean onItemLongClick(AdapterView<?> parent, View view,
			// int position, long id) {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Toast.makeText(getApplicationContext(), (String)
				// parent.getAdapter().getItem(position),
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(parent.getContext(),
						ChapterActivity.class);
				intent.putExtra("book",
						(String) parent.getAdapter().getItem(position));
				startActivity(intent);
				// return false;

			}
		});
		lvGrec1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Toast.makeText(getApplicationContext(), (String)
				// parent.getAdapter().getItem(position),
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(parent.getContext(),
						ChapterActivity.class);
				intent.putExtra("book",
						(String) parent.getAdapter().getItem(position));
				startActivity(intent);

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nwtmain, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String GT = "" + item.getTitle();
		if (GT.matches(getResources().getString(R.string.title_activity_find))) {
			Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(getApplicationContext(),
					FindActivity.class);
			startActivity(intent);

		} // if (GT.matches("����� �� ������"))

		return super.onOptionsItemSelected(item);
	}
}