package com.example.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class PieChart extends View {
	protected Boolean mShowText;
	protected Integer mTextPos;
	protected Paint mPaint;  
	
	public PieChart(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        
        TypedArray a = ctx.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);
        
        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
        		// TypedArray是共享资源
            a.recycle();
        }
        
        mPaint = new Paint();
    }
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
        mPaint.setColor(Color.GREEN);  
        mPaint.setStyle(Style.FILL);    
        canvas.drawRect(new Rect(10, 10, 100, 100), mPaint);  
        mPaint.setColor(Color.BLUE);
        canvas.drawText("hello, custom view", 10, 120, mPaint);  
	}

	public boolean isShowText() {
		return mShowText;
	}

	public void setShowText(boolean showText) {
	   mShowText = showText;
	   invalidate();
	   requestLayout();
	}
}
