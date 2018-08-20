package in.yuvi.small2dgame;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
 
@SuppressLint("DrawAllocation")
public class Sprite {
	
	//For Animation and BMP
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    
       private static final int BMP_COLUMNS = 3;
	private static final int BMP_ROWS = 4;
	private int x;
	private int y;
       private int xSpeed = 5;
       private int ySpeed = 5;
       private GameView gameView;
       private Bitmap bmp;
	private int width;
	private int height;
	
	private int count = 10;
	
	//incrementing the field in update method
	private int currentFrame;
	
      
       public Sprite(GameView gameView, Bitmap bmp, int count) {
             this.gameView=gameView;
             this.bmp=bmp;
             this.count = count;
             // to divide the image
             // no of rows & column in image bad 1
             
             this.width = bmp.getWidth() / BMP_COLUMNS;
             this.height = bmp.getHeight() / BMP_ROWS;
             
             //Random speed of X & Y
             
             Random rnd = new Random();
             xSpeed = rnd.nextInt(this.count)-5;
             ySpeed = rnd.nextInt(this.count)-5;
             
             // initialize X, Y
             x = rnd.nextInt(gameView.getWidth() - width);
             y = rnd.nextInt(gameView.getHeight() - height);
             
       }
 
       private void update() {
        /*     if (x > gameView.getWidth() - bmp.getWidth() width - xSpeed) {
            	 bmp.getWidth()
            	  * because we need one column width got in width variable    
            	 xSpeed = -5;
             }
             if (x + xSpeed< 0) {
                    xSpeed = 5;
             }
             x = x + xSpeed;*/
             //incrementing the field in update method
             // BMP col no of col increasing 0,1,2
             // after reaches to 3 it will be 0
             
             // Changes for X, Y Coordinates
    	   
    	   if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
               xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
               ySpeed = -ySpeed;
        }
        y = y + ySpeed;
             currentFrame = ++currentFrame % BMP_COLUMNS;
       }
      
       public void onDraw(Canvas canvas) {
             update();
             
             // add new printing methods
             
             int srcX = currentFrame * width;
             int srcY = getAnimationRow() * height;
             Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
             Rect dst = new Rect(x, y, x + width, y + height);
             
             canvas.drawBitmap(bmp, src , dst, null);
       }
       
   	//For Animation and BMP
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
       private int getAnimationRow() {
             double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
             int direction = (int) Math.round(dirDouble) % BMP_ROWS;
             return DIRECTION_TO_ANIMATION_MAP[direction];
       }

	public boolean isCollition(float x2, float y2) {
		
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
}  
