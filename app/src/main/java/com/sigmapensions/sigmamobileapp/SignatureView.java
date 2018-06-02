package com.sigmapensions.sigmamobileapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

import com.sigmapensions.sigmamobileapp.utils.CommonOps;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignatureView extends View{

	Paint penPaint;
	Paint screenPaint;
	Bitmap surfaceBitmap;
	Canvas bitmapCanvas;
	HashMap<Integer, Path> pathMap;
	HashMap<Integer, Point> pointMap;
	private final float TOUCH_TOLERANCE = 10;
	
	boolean canSave;
	Path path;
	Point point;
	
	Context context;
	Activity activity;
	
	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		screenPaint = new Paint();
		screenPaint.setColor(Color.WHITE);

		penPaint = new Paint();
		penPaint.setAntiAlias(true); 
		penPaint.setColor(Color.BLACK);
		penPaint.setStyle(Paint.Style.STROKE); 
		penPaint.setStrokeWidth(7); 
		penPaint.setStrokeCap(Paint.Cap.ROUND); 
		pathMap = new HashMap<Integer, Path>();
		pointMap = new HashMap<Integer, Point>();
		
	}	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		surfaceBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		bitmapCanvas = new Canvas(surfaceBitmap);
		surfaceBitmap.eraseColor(Color.WHITE);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(surfaceBitmap, 0, 0, screenPaint);
		for (Integer key : pathMap.keySet())
			canvas.drawPath(pathMap.get(key), penPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){

		int action = event.getActionMasked(); 
		int actionIndex = event.getActionIndex(); 
		if (action == MotionEvent.ACTION_DOWN ||
				action == MotionEvent.ACTION_POINTER_DOWN){
			touchStarted(event.getX(actionIndex), event.getY(actionIndex),
					event.getPointerId(actionIndex));
		}
		else if (action == MotionEvent.ACTION_UP ||
				action == MotionEvent.ACTION_POINTER_UP){
			touchEnded(event.getPointerId(actionIndex));
			
		} 
		else{
			touchMoved(event);
		} 

		invalidate(); 
		return true; 
	}


	private void touchEnded(int lineID) {
		path = pathMap.get(lineID); 
		bitmapCanvas.drawPath(path, penPaint); 
		path.reset();	
		canSave = true;
	}

	private void touchMoved(MotionEvent event) {
		for (int i = 0; i < event.getPointerCount(); i++){
			int pointerID = event.getPointerId(i);
			int pointerIndex = event.findPointerIndex(pointerID);
			if (pathMap.containsKey(pointerID)){
				float newX = event.getX(pointerIndex);
				float newY = event.getY(pointerIndex);
				path = pathMap.get(pointerID);
				point = pointMap.get(pointerID);
				float deltaX = Math.abs(newX - point.x);
				float deltaY = Math.abs(newY - point.y);
				if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE ){
					path.quadTo(point.x, point.y, (newX + point.x) / 2,
							(newY + point.y) / 2);
					point.x = (int) newX;
					point.y = (int) newY;
				}
			}
		}

	}

	private void touchStarted(float x, float y, int lineID) {
		
		if (pathMap.containsKey(lineID)){
			path = pathMap.get(lineID); 
			path.reset(); 
			point = pointMap.get(lineID); 
		}else {
			path = new Path(); 
			pathMap.put(lineID, path); 
			point = new Point(); 
			pointMap.put(lineID, point); 
		} 

		path.moveTo(x, y);
		point.x = (int) x;
		point.y = (int) y;
	} 	
	
	public void clear(){	
		pathMap.clear();
		pointMap.clear(); 
		surfaceBitmap.eraseColor(Color.WHITE); 
		invalidate(); 
		canSave = false;
	} 
	
	public void saveSignature(){
		if(canSave){
			FileOutputStream fos;
			try {
				// actually this would be unique for each contributor of course
				
				fos = new FileOutputStream(new File(CommonOps.getPrefixString(context) + "_signature.jpg"));
				surfaceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				RegisterContributor.signature_directory = CommonOps.getPrefixString(context) + "_signature.jpg";
				fos.flush();
				fos.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clear();
			
		}else{
			new MyDialog(getContext(), null, "Hold it!", "You haven't signed, you know?", "OK").show();
		}		
	}

}
