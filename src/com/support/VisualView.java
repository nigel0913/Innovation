package com.support;


import com.example.innovation.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class VisualView extends View {

	private boolean mShowText;
	private int mTextPos;
	
	/** 
     * 画笔对象的引用 
     */  
    private Paint paint;  
      
    /** 
     * 圆环的宽度 
     */  
    private float roundWidth;  
      
    public static final int STROKE = 0;  
    public static final int FILL = 1;  
      
    private float[] storeData;
    private float[] mPoints;
    private float max, heigh;
    private int pos, width;
    
    private int mColor;
	
	public VisualView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint(); 
		TypedArray a = context.getTheme().obtainStyledAttributes(
	        attrs,
	        R.styleable.VisualView,
	        0, 0);

	   try {
//	       mShowText = a.getBoolean(R.styleable.VisualView_showText, false);
	       mColor = a.getInteger(R.styleable.VisualView_vcolor, 0);
	       switch (mColor) {
	       case 0: mColor = Color.BLUE; break;
	       case 1: mColor = Color.RED; break;
	       default:break;
	       }
	   } finally {
	       a.recycle();
	   }
	   
	   init();
	}
	
	private void init() {
		storeData = new float[1100];
		for (int i=0; i<getWidth()+8; i++)
			storeData[i]=0;
		max = getWidth()/6;
		width = getWidth();
		pos = 0;
	}
	
	public synchronized void pushdata(float d) {
		storeData[pos] = d;
		pos = (pos+1)%width;
//		Log.v("pushdata", ""+d);
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth()/2; //获取圆心的x坐标  
        int radius = (int) (centre - 20/2); //圆环的半径  
//        paint.setColor(Color.RED); //设置圆环的颜色  
        paint.setStyle(Paint.Style.STROKE); //设置空心  
//        paint.setStrokeWidth(5); //设置圆环的宽度  
        paint.setAntiAlias(true);  //消除锯齿   
//        canvas.drawCircle(centre, centre, radius, paint); //画出圆环  
        
//        paint.setColor(Color.GRAY); //设置圆环的颜色  
//        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        
        paint.setStrokeWidth(1);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, paint);
        if (mPoints == null || mPoints.length < getWidth() * 4+4)  
        {  
            mPoints = new float[getWidth() * 4+4];  
        }  

        max = getWidth()/6;
 	   	width = getWidth();
        paint.setColor(mColor);
        int pp = 0;
        for (int i=0; i<width; i++) {
        	pp = (pos-i + width)%width;
        	heigh = getHeight()/2 - storeData[pp] * max/128;
//        	Log.v("storeData", "width="+width+" pp="+pp+" heigh="+heigh+" storeData[pp]="+storeData[pp]);
//        	if (heigh != getHeight()/2)
        	mPoints[i * 4] = i;
        	mPoints[i * 4 + 1] = getHeight()/2;
        	mPoints[i * 4 + 2] = i;
        	mPoints[i * 4 + 3] = heigh;
        }
        canvas.drawLines(mPoints, paint);
        Log.e("losssg", "width=" + width + " max=" + max + " pos=" + pos);  
    }

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBounds = new RectF(0, 0, w, h);
    }

    RectF mBounds;
}
