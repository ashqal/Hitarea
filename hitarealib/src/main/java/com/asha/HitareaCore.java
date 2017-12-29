package com.asha;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/12/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class HitareaCore implements IHitarea {
    private static final int sDefaultId = -1;
    public static final int sDefaultDebugBgColor = 0x6696ffea;
    public static final int sDefaultDebugBgColor2 = 0x66ff969d;
    private static final String TAG = "HitareaHelper";
    private boolean mDebug = false;
    private int mTargetViewId = sDefaultId;
    private View mTargetView;
    private Matrix mTransformMatrix;
    private float[] mPointSrc;
    private float[] mPointDst;

    private View.OnClickListener listener;
    private GestureDetector detector;
    private final Context context;

    public HitareaCore(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Hitarea,defStyleAttr,0);
        if (  ta != null ){
            if (ta.hasValue(R.styleable.Hitarea_hit_targetId))
                mTargetViewId = ta.getResourceId(R.styleable.Hitarea_hit_targetId,sDefaultId);
            if (ta.hasValue(R.styleable.Hitarea_hit_debug))
                mDebug = ta.getBoolean(R.styleable.Hitarea_hit_debug,false);
            ta.recycle();
        }
    }

    public boolean isDebug() {
        return mDebug;
    }

    public boolean onTouchEvent(MotionEvent event, HitareaDelegate delegate) {
        if (detector != null) {
            detector.onTouchEvent(event);
        }

        ensureTargetView(delegate);
        if ( mTargetView == null ) return false;
        if ( mTargetView.getVisibility() != View.VISIBLE ) return false;

        updateTransformMatrix(delegate);
        transformMotionEvent(event);
        return mTargetView.dispatchTouchEvent(event);
    }

    private void updateTransformMatrix(HitareaDelegate delegate) {
        if ( mTransformMatrix == null ){
            mTransformMatrix = new Matrix();
        }
        View hitarea = delegate.getHitareaView();
        float scaleX = mTargetView.getMeasuredWidth() * 1.0f / hitarea.getMeasuredWidth();
        float scaleY = mTargetView.getMeasuredHeight() * 1.0f / hitarea.getMeasuredHeight();
        mTransformMatrix.setScale(scaleX,scaleY);
    }

    private void ensureTargetView(HitareaDelegate delegate) {
        if ( mTargetView != null ) return;
        mTargetView = delegate.getTargetView(mTargetViewId);
        if( mTargetView == null ) Log.e(TAG,"[Hitarea] Can't find target view:" + mTargetViewId);
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

    @Override
    public void setTarget(View view) {
        mTargetView = view;
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.listener = l;
        if (detector == null) {
            detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (listener == null) {
                        return false;
                    }
                    listener.onClick(mTargetView);
                    return true;
                }
            });
        }
    }

    public interface HitareaDelegate {
        View getTargetView(int targetViewId);
        View getHitareaView();
    }
}
