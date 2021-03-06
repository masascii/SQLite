package com.example.alarmtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public MySQLiteHelper(Context context){
        
        super(context, "alarm.db", null, 1);
    }
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL(
                 "create table alarm("
                 + "name text not null, "
                 + "hour integer not null, "
                 + "minutes integer not null)");
		 db.execSQL("insert into alarm(name, hour, minutes) values('alarm_set1', 0, 0)");
		 db.execSQL("insert into alarm(name, hour, minutes) values('alarm_set2', 0, 0)");
		 db.execSQL("insert into alarm(name, hour, minutes) values('alarm_set3', 0, 0)");
		 
		 db.execSQL(
				 "create table button("
				 + "switch text not null, "
				 + "onoff text not null)");
		 db.execSQL("insert into button(switch, onoff) values('switch1', 'off')");
		 db.execSQL("insert into button(switch, onoff) values('switch2', 'off')");
		 db.execSQL("insert into button(switch, onoff) values('switch3', 'off')");
		 
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + "alarm");
		db.execSQL("DROP TABLE IF EXISTS " + "button");
	    onCreate(db);
	}
	
}
