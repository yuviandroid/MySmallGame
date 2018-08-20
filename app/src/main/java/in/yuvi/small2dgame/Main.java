package in.yuvi.small2dgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
 
public class Main extends Activity {
    /** Called when the activity is first created. */
    public static int level;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        level = intent.getIntExtra("Level", 1);
        Log.d("LLLLLLLLLLL", level+"");
        setContentView(new GameView(this));
        
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	this.finish();
    }
}