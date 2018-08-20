package in.yuvi.small2dgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {
    /** Called when the activity is first created. */
    
	Button btn_start;
	Button btn_quit;
	Button btn_highScore;
	Button btn_level;
	SharedPreferences score_pref;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_layout);
        initializeUI();
        
        btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MenuActivity.this,Main.class).putExtra("Level", 0));
			}
		});
        
        btn_level.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MenuActivity.this,Level_Selector.class));
			}
		});
        
        btn_quit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MenuActivity.this.finish();
			}
		});
        
        btn_highScore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int high_score = score_pref.getInt("HighScore", 0);
				Toast.makeText(MenuActivity.this, "Current High Score is = "+high_score, 5000).show();
			}
		});
    }
    
    private void initializeUI() {
		// TODO Auto-generated method stub
    	btn_start = (Button) findViewById(R.id.btn_start);
    	btn_level = (Button) findViewById(R.id.btn_level);
    	btn_quit = (Button) findViewById(R.id.btn_quit);
    	btn_highScore = (Button) findViewById(R.id.btn_highScore);
    	score_pref = MenuActivity.this.getSharedPreferences("KillMe", 0);
    	
	}

	@Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	this.finish();
    }
}