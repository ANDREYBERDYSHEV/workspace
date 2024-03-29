package com.andreyberdyshev.newworldtranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class FindActivity extends Activity {
	
	DBHelper dbHelper;
	ListView FindList;
    EditText myFindText;
    Button FindButton;
    MyApp aps;
    ArrayList<Map<String, Object>> data;
    Handler h;
    int DataCount;
    ProgressBar pb;
    String fText;
    
    
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
        	
		
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		dbHelper = new DBHelper(this);
//		Toast.makeText(getApplicationContext(), "������ ������!", Toast.LENGTH_LONG).show();
//		if (aps==null) {aps = new MyApp();}
		aps = (MyApp)getApplication();
	    String LLT = aps.getLastTitle();
		this.setTitle(LLT);
		myFindText = (EditText) findViewById(R.id.editFindText);
		String FT = aps.getLastFindext();
		myFindText.setText(FT);
		
		FindList =  (ListView) findViewById (R.id.listFind) ; 
  		 
		if (!aps.getMapData().isEmpty()) {
					
			String[] from ={"title","stix"};
	   		 int[] to={R.id.TitleField,R.id.textStixField};
	   		 
	   		 
	   		 SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(), aps.getMapData(), R.layout.show_find_result,
	   		        from, to);
		   		FindList.setAdapter(sAdapter);
			
		}
		
		FindButton = (Button) findViewById(R.id.buttonGoFind);
		// ������� ����������� ����� ���������� ������ � ������, ����������� �� ������� ������
		h = new Handler() {
			public void handleMessage(android.os.Message msg){
				
			
					
				
				FindActivity.this.setTitle(getResources().getString(R.string.title_reslut_find_window)+DataCount);
		   		 String[] from ={"title","stix"};
		   		 int[] to={R.id.TitleField,R.id.textStixField};
		   		 
		 	   		 SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(), aps.getMapData(), R.layout.show_find_result,
		   		        from, to);
			   		FindList.setAdapter(sAdapter);
			   		FindList.requestFocus();
					pb.setVisibility(View.INVISIBLE);
			
			};
			
		};
	}		
		
		       public void onclick(View v) 
			   {
		         
		//		FindButton.setEnabled(false);   

		    	   switch (v.getId()) {
		    	case R.id.buttonGoFind:
		    		myFindText = (EditText) findViewById(R.id.editFindText);
		    		pb.setVisibility(View.VISIBLE);
		    		fText=myFindText.getText().toString();
		    	Thread t = new Thread(new Runnable()
		    	{
	        	
		    	public void run()
		    	{
				
				
		   		SQLiteDatabase db = dbHelper.getWritableDatabase();		
		   		String sqlQuery="select a._id as id, a.book as book, " +
		   				"a.chapter as chapter, b.numstix as numstix, " +
		   				"b.stix as stix from main1 as a, maindetail as b where b.linktoid=id"; 
				Cursor c = db.rawQuery(sqlQuery,null);

		   		
		   		c.moveToFirst();
		   		
		   		int ColStix=c.getColumnIndex("stix");
		   		int ColBook=c.getColumnIndex("book");
		   		int ColChapter=c.getColumnIndex("chapter");
		   		int ColNumStix=c.getColumnIndex("numstix");
		   		
		   		int CountRec = c.getCount();
		   			
		   		final ArrayList<String>Chapters = new ArrayList<String>();
		   		final ArrayList<String>Titles = new ArrayList<String>();
		   				   		
		   		for (int i=1; i < CountRec+1; ++i)
		   		{
		   			
		   		
		   			String StrFind=c.getString(ColStix);
		   			if (StrFind.indexOf(fText)>0)
		   			{
		   				
		   			String Titles1 = c.getString(ColBook)+
		   					" "+c.getString(ColChapter)+":"+c.getString(ColNumStix);
		   			Titles.add(Titles1);	
		   			Chapters.add(StrFind);
		   		
		   			//	Toast.makeText(getApplicationContext(), "�������: "+Titles1, Toast.LENGTH_SHORT).show();
		   			
		   			}
		   			c.moveToNext();
		   			
		   		}
		   		c.close();
		   		db.close();
		
		   		data = new ArrayList<Map<String, Object>>(Titles.size());
	   	
		   		Map<String, Object> m;
	   		    
	   		 for (int i=0; i < Titles.size(); ++i)
		   		{
	   			  m = new HashMap<String, Object>();
		   		  m.put("title", Titles.get(i).toString());
		   		  m.put("stix", Chapters.get(i).toString());
		   		  data.add(m);
		   			   		
		   		  
		   		}
	   		 DataCount=Titles.size();
	   		 Titles.clear();
	   		 Chapters.clear();
	    	   MyApp aps = (MyApp)getApplication();
	    		 aps.setLastTitle("����� �� ������ ������� "+DataCount);
	    		 aps.setMapData(data);
	    		 aps.setFindText(fText);
		   		 data.clear();
		   		 h.sendEmptyMessage(0);
	   		 	}; 
		    	});
		    	t.start();
		   	break;
		   		default:
		   			break;
		    	} //	if (v.getId()=R.id.buttonGoFind)	
		       } //public void onClick(View v)
		     
		
			
	
		
			

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find, menu);
		return true;
	}
	

}


