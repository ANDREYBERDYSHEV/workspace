package com.andreyberdyshev.newworldtranslation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;



public class ShowStixActivity extends Activity {
	
final int MENU_SIZE_22 = 1;
final int MENU_SIZE_26 = 2;
final int MENU_SIZE_30 = 3;
final int MENU_SIZE_32 = 4;
final int MENU_SIZE_34 = 5;
final int MENU_FIND = 6;


String TextofCurrStix;
//TextView TextHeader;
TextView TextCurrentStix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_stix);
		// Show the Up button in the action bar.
		//setupActionBar();
		Intent intent = getIntent();
		TextofCurrStix = intent.getStringExtra("textofstix");
		String CurrChapter = intent.getStringExtra("chapter");
		String CurrBook = intent.getStringExtra("book");
		String CurrStix = intent.getStringExtra("stix");
		int LastStix=intent.getIntExtra("LastStix", 1);
		this.setTitle(CurrBook+" "+CurrChapter+":"+CurrStix+"-"+LastStix);
	//	TextHeader = (TextView) findViewById(R.id.textViewStixHeader);
	//	TextHeader.setText(CurrBook+" "+CurrChapter+":"+CurrStix+"-"+LastStix);
		
		TextCurrentStix = (TextView) findViewById(R.id.textViewShowCurrentStix);
		TextCurrentStix.setText(TextofCurrStix);
		TextCurrentStix.setMovementMethod(new ScrollingMovementMethod());
		registerForContextMenu(TextCurrentStix);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	//private void setupActionBar() {
	//	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	//		getActionBar().setDisplayHomeAsUpEnabled(true);
	//	}
	//}
	 @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    // TODO Auto-generated method stub
	    switch (v.getId()) {
	    case R.id.textViewShowCurrentStix:
	      menu.add(0, MENU_SIZE_22, 0, getResources().getString(R.string.font_size)+"16");
	      menu.add(0, MENU_SIZE_26, 0, getResources().getString(R.string.font_size)+"18");
	      menu.add(0, MENU_SIZE_30, 0, getResources().getString(R.string.font_size)+"20");
	      menu.add(0, MENU_SIZE_32, 0, getResources().getString(R.string.font_size)+"22");
	      menu.add(0, MENU_SIZE_34, 0, getResources().getString(R.string.font_size)+"30");
	      menu.add(0, MENU_FIND, 0, getResources().getString(R.string.menu_find));
	      
	      break;
	    }
	  }
	 @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    // TODO Auto-generated method stub
	    switch (item.getItemId()) {
	    // пункты меню для tvColor
	   case MENU_SIZE_22:
		  TextCurrentStix.setTextSize(16);
	
	      break;
	    case MENU_SIZE_26:
	     TextCurrentStix.setTextSize(18);
	      break;
	    case MENU_SIZE_30:
	    	TextCurrentStix.setTextSize(20);
	        break;
	    case MENU_SIZE_32:
	    	TextCurrentStix.setTextSize(22);
	        break;
	    case MENU_SIZE_34:
	    	TextCurrentStix.setTextSize(30);
	        break;
	    case MENU_FIND:
	    	Toast.makeText(getApplicationContext(), "Find", Toast.LENGTH_SHORT).show();
	    	 Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
	         startActivity(intent) ;	 
	    	break;
	    }
	    return super.onContextItemSelected(item);
	  } 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_stix, menu);
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
