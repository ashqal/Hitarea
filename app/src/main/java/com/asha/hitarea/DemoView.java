package com.asha.hitarea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/12/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class DemoView extends View {
    private static final String TAG = "DemoView";
    private Paint mPaint;

    public DemoView(Context context) {
        super(context);
        init();
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        mPaint.setAntiAlias(true);
    }

    int x = -1, y = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"DemoView onTouchEvent");
        final int action = MotionEventCompat.getActionMasked(event);
        switch ( action ){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                x = -1;
                y = -1;
                break;
            default:
                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if ( x == -1 && y == -1 ) return;
        canvas.drawCircle(x,y,10,mPaint);
    }
}
