package com.ct.fitorto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "History_db";
	public static final String TABLE_NAME = "History";
	public static final int VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String FNAME = "history";
	//public static final String LNAME = "L_NAME";
	/*public static final String SCRIPT = "create table " + TABLE_NAME + " ("
			+ KEY_ID + " integer primary key autoincrement, " + FNAME
			+ " text not null);";*/
	public static final String SCRIPT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
			+ KEY_ID + " integer primary key autoincrement, " + FNAME
			+ " TEXT NOT NULL UNIQUE);";

	public HistoryOpenHelper(Context context, String name,
							 CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table " + TABLE_NAME);
		onCreate(db);
	}

}
