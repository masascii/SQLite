package com.example.alarmtest;

import java.util.Calendar;

import com.example.alarmtest.R.layout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static String TAG = "MainActivity";
	private PlaceholderFragment phf = new PlaceholderFragment();
	private static final int SETACTIVITY = 1001;
	private String text;
	
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		
		
		setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, phf).commit();
        }
        
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == SETACTIVITY) {
			if(resultCode == RESULT_OK) {
				text = data.getStringExtra("keyword");
				if(text.equals("alarm_set1")) {
					phf.time1 = phf.searchByName(phf.db, "alarm_set1");
					phf.startAlarm("alarm_set1",phf.isStartAlarm);	
					phf.isStartAlarm = false;
					phf.switch1.setChecked(true);
					phf.isStartAlarm = true;
				}else if(text.equals("alarm_set2")) {
					phf.time2 = phf.searchByName(phf.db, "alarm_set2");
					phf.startAlarm("alarm_set2",phf.isStartAlarm);
					phf.isStartAlarm = false;
					phf.switch2.setChecked(true);
					phf.isStartAlarm = true;
				}else if(text.equals("alarm_set3")) {
					phf.time3 = phf.searchByName(phf.db, "alarm_set3");
					phf.startAlarm("alarm_set3",phf.isStartAlarm);
					phf.isStartAlarm = false;
					phf.switch3.setChecked(true);
					phf.isStartAlarm = true;
				}
			}
		}
	}
	
	    
	
	@Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        // TextView resultView = (TextView)findViewById(R.id.first_value_view);
        // resultView.setText(result);;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class PlaceholderFragment extends Fragment implements OnClickListener, CompoundButton.OnCheckedChangeListener {
		Button alarmSetBtn1;
	    Button alarmSetBtn2; 
	    Button alarmSetBtn3;
	    Switch switch1, switch2, switch3;
	    private View rootView;
	    private AlarmManager alarmManager;
	    Calendar calendarAlarm;
        private String name = "";
        boolean isStartAlarm = true;
        int[] time1 = {0, 0}, time2 = {0, 0}, time3 = {0, 0};
        private MySQLiteHelper dbHelper;
        private SQLiteDatabase db;
        private String swi_name = "";
		
	    public PlaceholderFragment() {
		}
	        
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	            	rootView = inflater.inflate(R.layout.fragment_main,
	            			container, false);
	            	
	            	
	            	alarmSetBtn1 = (Button) rootView.findViewById(R.id.alarm_set1);
	            	alarmSetBtn2 = (Button) rootView.findViewById(R.id.alarm_set2);
	            	alarmSetBtn3 = (Button) rootView.findViewById(R.id.alarm_set3);
	                switch1 = (Switch) rootView.findViewById(R.id.switch1);
	                switch2 = (Switch) rootView.findViewById(R.id.switch2);
	                switch3 = (Switch) rootView.findViewById(R.id.switch3);
	                
	                alarmSetBtn1.setOnClickListener(this);
	                alarmSetBtn2.setOnClickListener(this);
	                alarmSetBtn3.setOnClickListener(this);
	                switch1.setOnCheckedChangeListener(this);
	                switch2.setOnCheckedChangeListener(this);
	                switch3.setOnCheckedChangeListener(this);
	                
	            	return rootView;
		}
		@Override
		public void onAttach(Activity act) {
			super.onAttach(act);
			dbHelper = new MySQLiteHelper(act.getApplication());
			db = dbHelper.getWritableDatabase();
			time1 = searchByName(db, "alarm_set1");
	        time2 = searchByName(db, "alarm_set2");
	        time3 = searchByName(db, "alarm_set3");
	        if(searchByNameSwitch(db, "switch1") == true) {
	        	isStartAlarm = false;
	        	switch1.setChecked(true);
	        	isStartAlarm = true;
	        }else if(searchByNameSwitch(db, "switch1") == false){
	        	switch1.setChecked(false);
	        }
	        if(searchByNameSwitch(db, "switch2")) {
	        	isStartAlarm = false;
	        	switch2.setChecked(true);
	        	isStartAlarm = true;
	        }else {
	        	switch2.setChecked(false);
	        }
	        if(searchByNameSwitch(db, "switch3")) {
	        	isStartAlarm = false;
	        	switch3.setChecked(true);
	        	isStartAlarm = true;
	        }else {
	        	switch3.setChecked(false);
	        }
	        
	        
		}
		

		
		@Override
		public void onClick(View v) {
			Log.i(TAG,"onClick");
			switch(v.getId()) {
	        case R.id.alarm_set1:
	        	doSetting("alarm_set1");
	            break;
	        case R.id.alarm_set2:
	        	doSetting("alarm_set2");
	            break;
	        case R.id.alarm_set3:
	        	doSetting("alarm_set3");
	        	break;
	        }
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Log.i(TAG, "onCheckedChanged");
			switch(buttonView.getId()) {
	        case R.id.switch1:
	        	name = "alarm_set1";
	        	swi_name = "switch1";
	            break;
	        case R.id.switch2:
	        	name = "alarm_set2";
	        	swi_name = "switch2";
	            break;
	        case R.id.switch3:
	        	name = "alarm_set3";
	        	swi_name = "switch3";
	        }
			
			if(isChecked == true) {
				updateEntry(db, swi_name, "on");
				startAlarm(name, isStartAlarm);
				isStartAlarm = true;
			}else if(isChecked == false) {
				updateEntry(db, swi_name, "off");
				stopAlarm(name);
			}
			
		}
		
		public void doSetting(String name) {
		     Log.i(TAG,"doSetting");
			 Intent intent = new Intent(getActivity(), SetActivity.class);
			 intent.putExtra("keyword", name);
		     startActivityForResult(intent, SETACTIVITY);
		 }
		
		public void startAlarm(String name, boolean isStartAlarm) {
			if(isStartAlarm == true) {
				alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
				calendarAlarm = Calendar.getInstance();
				calendarAlarm.setTimeInMillis(System.currentTimeMillis());
				if(name.equals("alarm_set1")) {
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time1[0]);
			        calendarAlarm.set(Calendar.MINUTE, time1[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
			        
				}else if(name.equals("alarm_set2")) {
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time2[0]);
			        calendarAlarm.set(Calendar.MINUTE, time2[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
					
				}else if(name.equals("alarm_set3")) {
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time3[0]);
			        calendarAlarm.set(Calendar.MINUTE, time3[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
					
				}
			}
		}
		
		public void stopAlarm(String name) {
			alarmManager.cancel(getPendingIntent(name));
		}
		
		private PendingIntent getPendingIntent(String name) {
	        if(name.equals("alarm_set1")) {
	        	Intent intent1 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent1 = 
		                PendingIntent.getActivity(getActivity(), 0, intent1, 0);
		        return pendingIntent1;
	        }else if(name.equals("alarm_set2")) {
	        	Intent intent2 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent2 = 
		                PendingIntent.getActivity(getActivity(), 0, intent2, 0);
		        return pendingIntent2;
	        }else {
	        	Intent intent3 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent3 = 
		                PendingIntent.getActivity(getActivity(), 0, intent3, 0);
		        return pendingIntent3;
	        }
	    }
		
		private int[] searchByName(SQLiteDatabase db, String name){
            // Cursorを確実にcloseするために、try{}～finally{}にする
            Cursor cursor = null;
            try{
                // name_book_tableからnameとageのセットを検索する
                // ageが指定の値であるものを検索
                cursor = db.query("alarm", 
                        new String[]{"hour", "minutes"}, 
                        "name = ?", new String[]{"" + name}, 
                        null, null, null );
 
                // 検索結果をcursorから読み込んで返す
                return readCursor(cursor);
            }
            finally{
                // Cursorを忘れずにcloseする
                if( cursor != null ){
                    cursor.close();
                }
            }
        }
 
 
        /** 検索結果の読み込み */
        private int[] readCursor(Cursor cursor) {
            int[] result = {0, 0};
 
            // まず、Cursorからnameカラムとageカラムを
            // 取り出すためのインデクス値を確認しておく
            int indexHour = cursor.getColumnIndex("hour");
            int indexMinutes  = cursor.getColumnIndex("minutes");
 
            // ↓のようにすると、検索結果の件数分だけ繰り返される
            while(cursor.moveToNext()){
                // 検索結果をCursorから取り出す
            	int hour = cursor.getInt(indexHour);
                int minutes  = cursor.getInt(indexMinutes);
                result[0] = hour;
                result[1] = minutes;
            }
            return result;
        }
        
        private boolean searchByNameSwitch(SQLiteDatabase db, String name){
            // Cursorを確実にcloseするために、try{}～finally{}にする
            Cursor cursor = null;
            try{
                // name_book_tableからnameとageのセットを検索する
                // ageが指定の値であるものを検索
                cursor = db.query("button", 
                        new String[]{"onoff"}, 
                        "switch = ?", new String[]{"" + name}, 
                        null, null, null );
 
                // 検索結果をcursorから読み込んで返す
                return readCursorSwitch(cursor);
            }
            finally{
                // Cursorを忘れずにcloseする
                if( cursor != null ){
                    cursor.close();
                }
            }
        }
 
 
        /** 検索結果の読み込み */
        private boolean readCursorSwitch(Cursor cursor) {
            boolean result = false;
 
            // まず、Cursorからnameカラムとageカラムを
            // 取り出すためのインデクス値を確認しておく
            int indexOnOff = cursor.getColumnIndex("onoff");
            
 
            // ↓のようにすると、検索結果の件数分だけ繰り返される
            while(cursor.moveToNext()){
                // 検索結果をCursorから取り出す
            	String onoff = cursor.getString(indexOnOff);
                if(onoff.equals("on")) {
                	result = true;
                }else {
                	result = false;
                }
            }
            return result;
        }
        
        
        private void updateEntry(SQLiteDatabase db, String targetName, String onoff) {
			ContentValues val = new ContentValues();
            val.put("onoff", onoff);
 
            
            db.update("button", 
                    val,
                    "switch = ?",
                    new String[]{"" + targetName});
		}
	}
}
