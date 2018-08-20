package in.yuvi.small2dgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    
    //Create New field 
    private GameLoopThread gameLoopThread;
	

  
    
/* // add looping variable to change x in onDraw
    private int x = 0;
    
    // for moving left & right
    
    private int xSpeed = 1;*/

    // above didn't require after loop successful $ implementing sprite
    
    //Create New field Sprite
    //private Sprite sprite;
    //Now need multiple sprites
    
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<String> spritesName = new ArrayList<String>();
    
    private long lastClick;
	
    
    private Bitmap bmpBlood;
	// Sprites blood
	private List<TempSprite> temps = new ArrayList<TempSprite>();
	private int count = 0;
	private int level_count = Level_Selector.level;
	private int life_count = 5;
	
	private Timer timer;
    
	SharedPreferences score_pref;
	Editor score_edit;
	public static int temp_score;
	
	private int killed_sprite = 0;
	Context mContext;
	
    public GameView(Context context) {
          super(context);
          holder = getHolder();
          this.mContext = context;
          
          score_pref = context.getSharedPreferences("KillMe", 0);
          score_edit = score_pref.edit();
          temp_score = score_pref.getInt("HighScore", 0);
          
          Log.d("HighScore", temp_score+"");
          //Initialize game loop
          gameLoopThread = new GameLoopThread(this);
          
          holder.addCallback(new SurfaceHolder.Callback() {

                 @SuppressWarnings("deprecation")
				@Override
                 public void surfaceDestroyed(SurfaceHolder holder) {
                	 timer.cancel();

                	 gameLoopThread.setRunning(false);
                	
                 }

                 @Override
                 public void surfaceCreated(SurfaceHolder holder) {
                        /*Canvas c = holder.lockCanvas(null);
                        onDraw(c);
                        holder.unlockCanvasAndPost(c);*/
                	 
                	 // Above code removed and new loop thread get executed
                	 createSprites();
                	 gameLoopThread.setRunning(true);
                     gameLoopThread.start();
                 }

                 @Override
                 public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
                 }
          });
          /*bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
          
          //Initialize sprite field
          sprite = new Sprite(this, bmp);*/
          
          // sprites to list
          //for blood
          bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
          
    }
    
    private synchronized void createSprites() {
    	
    	level_count = level_count+1;
        sprites.add(createSprite(R.drawable.bad1));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.bad2));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.bad3));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.bad4));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.bad5));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.bad6));
        spritesName.add("bad");
        sprites.add(createSprite(R.drawable.good1));
        spritesName.add("good");
        sprites.add(createSprite(R.drawable.good2));
        spritesName.add("good");
        sprites.add(createSprite(R.drawable.good3));
        spritesName.add("good");
        sprites.add(createSprite(R.drawable.good4));
        spritesName.add("good");
        sprites.add(createSprite(R.drawable.good5));
        spritesName.add("good");
        sprites.add(createSprite(R.drawable.good6));
        spritesName.add("good");
        //constant  = sprites.size();
        Log.d("LEVEL_COUNT", level_count+"");
        timer = new Timer();
        timer.schedule(new UpdateTimeTask(),30000);
  }
    
    private synchronized Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp,level_count);
  }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
          canvas.drawColor(Color.BLACK);
         // Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg02);
          
         
          
         // canvas.setBitmap(bg);
          /*if (x < getWidth() - bmp.getWidth()) {
              x++;
          }*/
          //above code replaced to change direction with constant speed
          
       /* // below code sets value for x to its width of device
		if (x == getWidth() - bmp.getWidth()) {
			//checking the right border
			xSpeed = -1;
		}
		if (x == 0) {
			//x==0 means got the left border
			xSpeed = 1;
		}
		x = x + xSpeed;
       
          //canvas.drawBitmap(bmp, 10, 10, null);
          // add x
          
          canvas.drawBitmap(bmp, x, 10, null);*/
          
          // all above code not required after Sprite just call
          
          //sprite.onDraw(canvas);
          //
          // multiple sprites
          
          for (int i = temps.size() - 1; i >= 0; i--) {
              temps.get(i).onDraw(canvas);
        }
          
          for (Sprite sprite : sprites) {
              sprite.onDraw(canvas);
       }
        
         
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	if (System.currentTimeMillis() - lastClick > 300) {
    		
    		
            lastClick = System.currentTimeMillis();
    	synchronized (getHolder()) { 
    		
    		float x = event.getX();
			float y = event.getY();
			
    	for (int i = sprites.size()-1; i >= 0; i--) {
                 
    			Sprite sprite = sprites.get(i);
    			String spriteName = spritesName.get(i);
                 if (sprite.isCollition(x,y)) {
               
                	 Log.d("Sprite Name : ", spriteName);
                        sprites.remove(i);
                        spritesName.remove(i);
                 //remove above sprite and replace blood sprite       
						temps.add(new TempSprite(temps, this, x, y, bmpBlood));       
                 // to remove only top sprite
						count ++;
						Log.d("Killed---------", count+"");
						if(spriteName.equalsIgnoreCase("good"))
						{
							life_count--;
						}
						else
						{
							killed_sprite++;
							Log.d("killed_sprite---------", killed_sprite+"");
						}
					if(life_count>0)
					{
						if(!(spritesName.contains("bad")))
						//if(count==constant){
								{
							//level_count = level_count+1;
							timer.cancel();
							createSprites();
							Toast.makeText(getContext(), "Congrats..."
									+ "Level Completed", 5000).show();
							count=0;
							life_count++;
						}
					}
					else
					{
						if(killed_sprite>temp_score)
						{
							score_edit.putInt("HighScore", killed_sprite);
							temp_score = killed_sprite;
							score_edit.commit();
						}
						else
						{
							Toast.makeText(getContext(), "High Score", 2000).show();
						}
						
						Toast.makeText(getContext(), "Ooopppsss..."
								+ "Game Over", 5000).show();
						sprites.removeAll(sprites);
						spritesName.clear();
						
						temp_score = killed_sprite;
						killed_sprite = 0;
						
						Log.d(temp_score+"", killed_sprite+"");
						new AlertDialog.Builder(getContext())
					    .setTitle("Delete entry")
					    .setMessage("Are you sure you want continue to play?")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					        	life_count = 5;
					        	createSprites();
					            
					        }
					     })
					    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					        	Toast.makeText(getContext(), "Bye bye ..."
										+ "Game Over", 5000).show();
					        	
					        	Intent intent = new Intent(mContext, MenuActivity.class);
					            mContext.startActivity(intent);
					        	
					        	
					        }
					     })
					    .setIcon(android.R.drawable.ic_dialog_alert)
					     .show();
					}
                 break;
                        
                 }
          }
    	}}
          return true;
    }
    
    class UpdateTimeTask extends TimerTask {

        public void run() 
           {
        	timer.cancel();
        	createSprites();
           }

        }
    
}