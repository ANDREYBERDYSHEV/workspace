package com.andreyberdyshev.newworldtranslation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
//import android.content.ContentValues;

public class StixActivity extends Activity {

	GridView GridStix;
//    TextView StixText;
    DBHelper dbHelper1;
    ArrayAdapter<String> adapter;
    String bookname;
    String numchapter;
    String CurrentStix;
    int CountRec;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stix);
		Intent intent = getIntent();
	//	StixText = (TextView) findViewById(R.id.ChapterText2);
		GridStix = (GridView) findViewById(R.id.gridViewStix);
		//int idnumber=intent.getIntExtra("idnumber", 1);
		bookname = intent.getStringExtra("book");
		numchapter = intent.getStringExtra("chapter");
		
		
		 dbHelper1 = new DBHelper(this);
		 
	    SQLiteDatabase db = dbHelper1.getWritableDatabase();
		String sqlQuery="select a._id as id, a.book as book, a.chapter as chapter, b.numstix as numstix, b.stix as stix from main1 as a, maindetail as b where b.linktoid=id and book='"+bookname+"' and chapter="+numchapter; 
		Cursor c = db.rawQuery(sqlQuery,null);
				 
	//	Cursor c=	db.query("maindetail", null, "_id="+idnumber, null, null, null, null);
	//	c.moveToFirst();
		CountRec = c.getCount();
		//Toast.makeText(getApplicationContext(), "Ќайдено стихов: "+CountRec, Toast.LENGTH_LONG).show();
		this.setTitle(bookname+" "+numchapter+":1-"+CountRec);
		//StixText.setText(bookname+" "+numchapter+":1-"+CountRec);
		final ArrayList<String>Stix = new ArrayList<String>();
		
		for (int i=1; i<CountRec+1; ++i)
		{
			Stix.add(" "+i+" ");
		}
		c.close();
		db.close();
		 adapter=new ArrayAdapter<String>(this,
				R.layout.item,R.id.tvText,Stix);
				//присваиваем адаптер списку
				GridStix.setAdapter(adapter);
				GridStix.setNumColumns(GridView.AUTO_FIT);
				GridStix.setColumnWidth(80);
				GridStix.setVerticalSpacing(5);
				GridStix.setHorizontalSpacing(5);
				GridStix.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
				
				
				
// select a._id, a.book, a.chapter, b.numstix, b.stix from main1 a, maindetail b where b.linktoid=a._id and a.book="Бытие" and a.chapter=5 and b.numstix=1
	
				//GridStix.setOnItemLongClickListener(new OnItemLongClickListener() {
				GridStix.setOnItemClickListener(new OnItemClickListener() {
			    	 public void onItemClick(AdapterView<?> parent, View view,
			                    int position, long id)   {
			    		 
			    		 //Toast.makeText(getApplicationContext(), (String) parent.getAdapter().getItem(position), Toast.LENGTH_LONG).show();
			    	
			    		 SQLiteDatabase db = dbHelper1.getWritableDatabase();
			    	  	 String NumStix = (String) parent.getAdapter().getItem(position);	
			    		 String sqlQuery="select a._id as id, a.book as book, a.chapter as chapter, b.numstix as numstix, b.stix as stix from main1 as a, maindetail as b where b.linktoid=id and book='"+bookname+"' and chapter="+numchapter+"and b.numstix>="+NumStix; 
			    		 //String sqlQuery="select stix from maindetail where stix like '130.'"; 
				    			
			    		 Cursor c = db.rawQuery(sqlQuery,null);	
			    			CurrentStix="";
			    		 if (c.moveToFirst()){
			    		 int numColWithStix = c.getColumnIndex("stix");
			    		 do
			    		 {
			    		  CurrentStix = CurrentStix + " \r\n\r\n"+ c.getString(numColWithStix);
			    		 
			    		 } while (c.moveToNext());
			    		 }
			    		 Intent intent = new Intent(parent.getContext(), ShowStixActivity.class) ;
			    		    intent.putExtra("book", bookname);
			    		    intent.putExtra("chapter", numchapter);
			    		    intent.putExtra("textofstix", CurrentStix);
			    		    intent.putExtra("LastStix", CountRec);
			                intent.putExtra("stix", (String) parent.getAdapter().getItem(position));
			    		    startActivity(intent) ;
							
			                
			            }
						          } );
	
	
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stix, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    
     String GT = ""+item.getTitle();
     if (GT.matches(getResources().getString(R.string.title_activity_find))) {

    	 Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT).show();
    	 Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
         startActivity(intent) ;	 
    	 
     } // if (GT.matches("Поиск по Библии"))
     
      return super.onOptionsItemSelected(item);
    }

}




