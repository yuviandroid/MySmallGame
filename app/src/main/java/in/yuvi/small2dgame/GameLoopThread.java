package in.yuvi.small2dgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class GameLoopThread extends Thread {
    private GameView view;
    private boolean running = false;
   
    //for constant movement regarding processor speed
    static final long FPS = 10;
    
    public GameLoopThread(GameView view) {
          this.view = view;
    }

    public void setRunning(boolean run) {
          running = run;
    }

    @Override
    public void run() {
    	
    	//ticksPS is the minimum each loop lost
    	long ticksPS = 1000 / FPS; 
    	
        long startTime;
        long sleepTime;
        while (running) {
               Canvas c = null;
               
               //startTime take time in milliseconds
               startTime = System.currentTimeMillis();
               
               try {
                      c = view.getHolder().lockCanvas();
                      synchronized (view.getHolder()) {
                             view.onDraw(c);
                      }
               } finally {
                      if (c != null) {
                             view.getHolder().unlockCanvasAndPost(c);
                      }
               }
               
               // is time difference between startTime & currentTime
               sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
               try {
                      if (sleepTime > 0)
                             sleep(sleepTime);
                      else
                             sleep(10);
               } catch (Exception e) {}
        }
    }
} 
