package com.asha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by hzqiujiadi on 15/12/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class Hitarea extends View implements HitareaHelper.HitareaDelegate {
    private static final String TAG = "Hitarea";

    private HitareaHelper mHelper;

    public Hitarea(Context context) {
        this(context,null);
    }

    public Hitarea(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hitarea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new HitareaHelper(context,attrs,defStyleAttr);
        setFocusable(false);
    }

    /**
     * Draw nothing.
     *
     * @param canvas an unused parameter.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        if (mHelper.isDebug()) {
            canvas.drawColor(HitareaHelper.sDefaultDebugBgColor);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mHelper.onTouchEvent(event,this);
    }

    /**
     * copied from {@link android.widget.Space}
     *
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

    /**
     * copied from {@link android.widget.Space}
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    public View getTargetView(int targetViewId) {
        // find target view by id nearby
        if ( targetViewId == -1 ) return null;
        View targetView = null;
        View v = this;
        while ( true ){
            ViewParent vp = v.getParent();
            if ( vp == null ) break;
            if ( !(vp instanceof View) ) break;
            v = (View) vp;
            targetView = v.findViewById(targetViewId);
            if ( targetView != null ) break;
            if ( v == getRootView() ) break;
        }
        return targetView;
    }

    @Override
    public View getHitareaView() {
        return this;
    }
}
