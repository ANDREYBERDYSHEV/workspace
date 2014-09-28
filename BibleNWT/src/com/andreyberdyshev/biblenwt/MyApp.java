package com.andreyberdyshev.biblenwt;

import java.util.ArrayList;
import java.util.Map;

import android.app.Application;

public class MyApp extends Application {

	private ArrayList<Map<String, Object>> privatedata = new ArrayList<Map<String, Object>>(
			0);
	private String LastTitle = "Поиск по Библии - первый вызов";
	private String FindText = "";

	public String getLastFindext() {
		return FindText;
	}

	public void setFindText(String FT) {
		this.FindText = FT;
	}

	public ArrayList<Map<String, Object>> getMapData() {
		return privatedata;

	}

	public String getLastTitle() {
		return LastTitle;

	}

	public void setLastTitle(String LT) {
		this.LastTitle = LT;
	}

	public void setMapData(ArrayList<Map<String, Object>> mydata) {
		this.privatedata.clear();
		this.privatedata.addAll(mydata);

	}

}
