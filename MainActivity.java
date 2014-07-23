package com.example.alarmtest;

import java.util.Calendar;

import com.example.alarmtest.R.layout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
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
					phf.isStartAlarm = false;
					phf.startAlarm("alarm_set1",phf.isStartAlarm);	
				}else if(text.equals("alarm_set2")) {
					phf.time2 = phf.searchByName(phf.db, "alarm_set2");
					phf.isStartAlarm = false;
					phf.startAlarm("alarm_set2",phf.isStartAlarm);
				}else if(text.equals("alarm_set3")) {
					phf.time3 = phf.searchByName(phf.db, "alarm_set3");
					phf.isStartAlarm = false;
					phf.startAlarm("alarm_set3",phf.isStartAlarm);
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
	    
	    private MySQLiteHelper dbHelper = new MySQLiteHelper(getActivity().getApplication());
        private SQLiteDatabase db = dbHelper.getReadableDatabase();
        private String name = "";
        private int[] time1 = {0, 0}, time2 = {0, 0}, time3 = {0, 0};
        boolean isStartAlarm = true;
		
	    public PlaceholderFragment() {
		}
	        
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	            	rootView = inflater.inflate(R.layout.fragment_main,
	            			container, false);
	            	
	            	//TextView current_time_text = (TextView) rootView.findViewById(R.id.current_time);
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
	                time1 = searchByName(db, "alarm_set1");
	                time2 = searchByName(db, "alarm_set2");
	                time3 = searchByName(db, "alarm_set3");
	                
	            	return rootView;
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
	            break;
	        case R.id.switch2:
	        	name = "alarm_set2";
	            break;
	        case R.id.switch3:
	        	name = "alarm_set3";
	        }
			
			if(isChecked == true) {
				startAlarm(name, isStartAlarm);
				isStartAlarm = true;
			}else if(isChecked == false) {
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
					if(switch1.isChecked() == false) {
						switch1.setChecked(true);
					}
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time1[0]);
			        calendarAlarm.set(Calendar.MINUTE, time1[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
					}
			        
				}else if(name.equals("alarm_set2")) {
					if(switch2.isChecked() == false) {
						switch2.setChecked(true);
					}
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time2[0]);
			        calendarAlarm.set(Calendar.MINUTE, time2[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
					
			        
				}else if(name.equals("alarm_set3")) {
					if(switch1.isChecked() == false) {
						switch1.setChecked(true);
					}
					calendarAlarm.set(Calendar.HOUR_OF_DAY, time3[0]);
			        calendarAlarm.set(Calendar.MINUTE, time3[1]);
			        calendarAlarm.set(Calendar.SECOND, 0);
			        calendarAlarm.set(Calendar.MILLISECOND, 0);
			        
			        alarmManager.set(AlarmManager.RTC_WAKEUP,
		                    calendarAlarm.getTimeInMillis(),
		                    getPendingIntent(name));
					
				}
		}
		
		public void stopAlarm(String name) {
			alarmManager.cancel(getPendingIntent(name));
		}
		
		private PendingIntent getPendingIntent(String name) {
	        if(name.equals("alarm_set1")) {
	        	Intent intent1 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent = 
		                PendingIntent.getActivity(getActivity(), 0, intent1, 0);
		        return pendingIntent;
	        }else if(name.equals("alarm_set2")) {
	        	Intent intent2 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent = 
		                PendingIntent.getActivity(getActivity(), 0, intent2, 0);
		        return pendingIntent;
	        }else {
	        	Intent intent3 = 
		                new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
		        PendingIntent pendingIntent = 
		                PendingIntent.getActivity(getActivity(), 0, intent3, 0);
		        return pendingIntent;
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
	}
}
