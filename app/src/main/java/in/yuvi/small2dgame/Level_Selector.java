package in.yuvi.small2dgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Level_Selector extends Activity {
	
	Button btn_level_sel;
	Spinner spnr_level;
	ArrayList<String> arrlist_level;
	public static int level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_selector);
		
		level = Main.level;
		initializeUI();
		
		for(int i=1;i<=25;i++)
		{
			arrlist_level.add("Level "+i);
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrlist_level);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnr_level.setAdapter(dataAdapter);
			
		btn_level_sel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Level_Selector.this,Main.class).putExtra("Level", level));
				Level_Selector.this.finish();
			}
		});
		
		spnr_level.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getApplicationContext(), "Level = "+(arg2+1), 2000).show();
				level = arg2+1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initializeUI() {
		// TODO Auto-generated method stub
		btn_level_sel = (Button) findViewById(R.id.btn_level_sel);
		spnr_level = (Spinner) findViewById(R.id.spnr_level);
		arrlist_level = new ArrayList<String>();
	}

}
