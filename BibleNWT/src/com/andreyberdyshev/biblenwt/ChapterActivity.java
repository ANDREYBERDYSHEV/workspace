package com.andreyberdyshev.biblenwt;

import java.util.ArrayList;

import android.app.Activity;
//import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class ChapterActivity extends Activity {

	GridView GridChapter;
	// TextView ChapterText;
	MyDatabase dbHelper;
	ArrayAdapter<String> adapter;
	String bookname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapter);
		Intent intent = getIntent();
		// ChapterText = (TextView) findViewById(R.id.ChapterText2);
		GridChapter = (GridView) findViewById(R.id.gridViewStix);
		bookname = intent.getStringExtra("book");

		dbHelper = new MyDatabase(this);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Cursor c =
		// db.rawQuery(" select book from main1 where book="+bookname,null);

		Cursor c = db.query("main1", null, "book=" + "'" + bookname + "'",
				null, null, null, null);
		// c.moveToFirst();

		int CountRec = c.getCount();
		this.setTitle(bookname + " 1-" + CountRec);
		// ChapterText.setText(bookname+" 1-"+CountRec);
		// Toast.makeText(getApplicationContext(), "������� ����: "+CountRec,
		// Toast.LENGTH_LONG).show();

		final ArrayList<String> Chapters = new ArrayList<String>();

		for (int i = 1; i < CountRec + 1; ++i) {
			Chapters.add(" " + i + " ");
		}
		c.close();
		db.close();

		adapter = new ArrayAdapter<String>(this, R.layout.chaptbook,
				R.id.tvBook, Chapters);
		// ����������� ������� ������
		GridChapter.setAdapter(adapter);
		GridChapter.setNumColumns(GridView.AUTO_FIT);
		GridChapter.setColumnWidth(80);
		GridChapter.setVerticalSpacing(5);
		GridChapter.setHorizontalSpacing(5);
		GridChapter.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);

		GridChapter.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Toast.makeText(getApplicationContext(), (String)
				// parent.getAdapter().getItem(position),
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(parent.getContext(),
						StixActivity.class);
				intent.putExtra("book", bookname);
				intent.putExtra("chapter", (String) parent.getAdapter()
						.getItem(position));
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chapter, menu);
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

class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		// ����������� �����������
		super(context, "NWTDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("CREATE TABLE main1 (_id integer primary key autoincrement, book varchar(50), chapter integer, fullchapter text);");
		// db.execSQL("CREATE TABLE maindetail (_id integer primary key autoincrement, linktoid integer, numstix integer, stix text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}