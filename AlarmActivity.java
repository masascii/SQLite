package com.example.alarmtest;


import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AlarmActivity extends Activity {
	private static String TAG = "AlarmActivity";
	private PlaceholderFragment phf = new PlaceholderFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		
		
		setContentView(R.layout.activity_alarm);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, phf).commit();
        }
	}
	
	public static class PlaceholderFragment extends Fragment implements OnClickListener {
		Button alarmStop;
	    
	    private View rootView;
		
	    public PlaceholderFragment() {
		}
	        
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	            	rootView = inflater.inflate(R.layout.fragment_alarm,
	            			container, false);
	            	
	            	alarmStop = (Button) rootView.findViewById(R.id.alarmstop);
	            	
	                
	                alarmStop.setOnClickListener(this);
	                
	            	return rootView;
		}
		
		@Override
		public void onClick(View v) {
			getActivity().finish();
		}
		
 
	}
}
