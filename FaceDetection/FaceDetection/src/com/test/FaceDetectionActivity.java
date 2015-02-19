package com.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class FaceDetectionActivity extends Activity
{
	int a,b,c,d;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(new MyView(this));
    }
    
    private class MyView extends View
    {
    	private Bitmap myBitmap;
    	private int width, height;
    	private FaceDetector.Face[] detectedFaces;
    	private int NUMBER_OF_FACES=4;
    	private FaceDetector faceDetector;
    	private int NUMBER_OF_FACE_DETECTED;
    	private float eyeDistance;
    	
		public MyView(Context context) 
		{
			super(context);
			BitmapFactory.Options bitmapFatoryOptions=new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig=Bitmap.Config.RGB_565;
			myBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.face1,bitmapFatoryOptions);
			width=myBitmap.getWidth();
			height=myBitmap.getHeight();
			detectedFaces=new FaceDetector.Face[NUMBER_OF_FACES];
			faceDetector=new FaceDetector(width,height,NUMBER_OF_FACES);
			NUMBER_OF_FACE_DETECTED=faceDetector.findFaces(myBitmap, detectedFaces);
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(myBitmap, 0,0, null);
			Paint myPaint = new Paint();
            myPaint.setColor(Color.GREEN);
            myPaint.setStyle(Paint.Style.STROKE); 
            myPaint.setStrokeWidth(3);

            for(int count=0;count<NUMBER_OF_FACE_DETECTED;count++)
            {
            	Face face=detectedFaces[count];
            	PointF midPoint=new PointF();
            	face.getMidPoint(midPoint);
            	
            	eyeDistance=face.eyesDistance();
            	Log.d(""+(midPoint.x-eyeDistance), ""+(midPoint.y-eyeDistance));
            	Log.d(""+(midPoint.x+eyeDistance), ""+(midPoint.y+eyeDistance));
            	canvas.drawRect(midPoint.x-eyeDistance, midPoint.y-eyeDistance, midPoint.x+eyeDistance, midPoint.y+eyeDistance, myPaint);
            	//crop the image
            	a=(int) (midPoint.x-eyeDistance);
            	b=(int) (midPoint.y-eyeDistance);
            	c=(int) (midPoint.x+eyeDistance);
            	d=(int) (midPoint.y+eyeDistance);

            }
        	Bitmap croppedBitmap = Bitmap.createBitmap(myBitmap,Math.abs(a),Math.abs(b),200,200);
        	//canvas.drawBitmap(croppedBitmap, 0, 0, null);
            
			canvas.drawBitmap(croppedBitmap,(canvas.getWidth()/2),0,null);
			storeImage(croppedBitmap,"test"+a+".png");
			
		}
		private boolean storeImage(Bitmap imageData, String filename) {
			//get path to external storage (SD card)
			String iconsStoragePath = Environment.getExternalStorageDirectory() + "/Pictures/";
			File sdIconStorageDir = new File(iconsStoragePath);

			//create storage directories, if they don't exist
			sdIconStorageDir.mkdirs();

			try {
				String filePath = sdIconStorageDir.toString() + filename;
				FileOutputStream fileOutputStream = new FileOutputStream(filePath);

				BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

				//choose another format if PNG doesn't suit you
				imageData.compress(CompressFormat.PNG, 100, bos);

				bos.flush();
				bos.close();

			} catch (FileNotFoundException e) {
				Log.w("TAG", "Error saving image file: " + e.getMessage());
				return false;
			} catch (IOException e) {
				Log.w("TAG", "Error saving image file: " + e.getMessage());
				return false;
			}

			return true;
		}
    	
    }
}