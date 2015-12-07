package com.asha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by hzqiujiadi on 15/12/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class Hitarea extends View {
    private static final String TAG = "Hitarea";
    private static final int sDefaultId = -1;
    private static final int sDefaultDebugBgColor = 0x6696ffea;
    private boolean mDebug = false;
    private int mTargetViewId = sDefaultId;
    private View mTargetView;
    private Matrix mTransformMatrix;
    private float[] mPointSrc;
    private float[] mPointDst;

    public Hitarea(Context context) {
        this(context,null);
    }

    public Hitarea(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hitarea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Hitarea,defStyleAttr,0);
        if (  ta != null ){
            if (ta.hasValue(R.styleable.Hitarea_targetId))
                mTargetViewId = ta.getResourceId(R.styleable.Hitarea_targetId,sDefaultId);
            if (ta.hasValue(R.styleable.Hitarea_debug))
                mDebug = ta.getBoolean(R.styleable.Hitarea_debug,false);
            ta.recycle();
        }

    }

    /**
     * Draw nothing.
     *
     * @param canvas an unused parameter.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        if (mDebug) {
            if ( getBackground() == null ) setBackgroundColor(sDefaultDebugBgColor);
            super.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");

        ensureTargetView();
        if ( mTargetView == null ) return super.onTouchEvent(event);
        updateTransformMatrix();
        transformMotionEvent(event);
        return mTargetView.onTouchEvent(event);
    }

    private void ensureTargetView() {
        if ( mTargetView != null ) return;
        if ( mTargetViewId == -1 ) return;
        View v = this;
        while ( true ){
            ViewParent vp = v.getParent();
            if ( vp == null ) break;
            if ( !(vp instanceof View) ) break;
            v = (View) vp;
            mTargetView = v.findViewById(mTargetViewId);
            if ( mTargetView != null ) break;
            if ( v == getRootView() ) break;
        }
    }

    private void transformMotionEvent(MotionEvent event){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            event.transform(mTransformMatrix);
        } else {
            if ( mPointSrc == null ){
                mPointSrc = new float[2];
                mPointDst = new float[2];
            }
            mPointSrc[0] = event.getX();
            mPointSrc[1] = event.getY();
            mTransformMatrix.mapPoints(mPointDst,mPointSrc);
            event.setLocation(mPointDst[0],mPointDst[1]);
        }
    }

    private void updateTransformMatrix() {
        if ( mTransformMatrix == null ){
            mTransformMatrix = new Matrix();
        }
        float scaleX = mTargetView.getMeasuredWidth() * 1.0f / getMeasuredWidth();
        float scaleY = mTargetView.getMeasuredHeight() * 1.0f / getMeasuredHeight();
        mTransformMatrix.setScale(scaleX,scaleY);
    }

    /**
     * Compare to: {@link View#getDefaultSize(int, int)}
     * If mode is AT_MOST, return the child size instead of the parent size
     * (unless it is too big).
     */
    private static int getDefaultSize2(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
    }
}
