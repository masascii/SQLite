package com.example.alarmtest;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetActivity extends Activity {
	private static String TAG = "SetActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_setting);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
	}
	
	
	public static class PlaceholderFragment extends Fragment implements OnClickListener{
        
		private MySQLiteHelper dbHelper;
        private SQLiteDatabase db;
        String name;
		
		
		public PlaceholderFragment() {
		}
	        
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	            	View rootView = inflater.inflate(R.layout.fragment_setting,
	            			container, false);
	            	Intent intent = getActivity().getIntent();
	            	name = intent.getStringExtra("keyword");
	            	
	            	
	            	Button okBtn = (Button) rootView.findViewById(R.id.alarm_set);
	            	Button cancelBtn = (Button) rootView.findViewById(R.id.cancel_set);
	            	
	                okBtn.setOnClickListener(this);
	                cancelBtn.setOnClickListener(this);
	                
	            	return rootView;
		}
		
		@Override
		public void onClick(View v) {
			Log.i(TAG,"onClick");
			switch(v.getId()) {
	        case R.id.alarm_set:
	        	doSetting();
	            break;
	        case R.id.cancel_set:
	        	doCancel();
	            break;
	        }
		}
		
		@Override
		public void onAttach(Activity act) {
			super.onAttach(act);
			dbHelper = new MySQLiteHelper(act.getApplication());
			db = dbHelper.getWritableDatabase();
			}
		
		public void doSetting() {
			Log.i(TAG, "doSetting");
			
			TimePicker timePicker = (TimePicker) getActivity().findViewById(R.id.time_picker);
			
			int hour = timePicker.getCurrentHour();
			int min = timePicker.getCurrentMinute();
			
			updateEntry(db, name, hour , min);
			Intent data = new Intent();
			data.putExtra("keyword",name);
			getActivity().setResult(RESULT_OK, data);
			getActivity().finish();
		}
		
		public void doCancel() {
			Log.i(TAG, "doCancel");
			getActivity().finish();
		}
		
		private void updateEntry(SQLiteDatabase db, String targetName, int newHour, int newMinutes) {
            
            ContentValues val = new ContentValues();
            val.put("hour", newHour);
            val.put("minutes" , newMinutes);
 
            
            db.update("alarm", 
                    val,
                    "name = ?",
                    new String[]{"" + targetName});
        }
		
        
	}
}
