package com.asha;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by hzqiujiadi on 15/12/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class HitareaWrapper extends RelativeLayout implements HitareaCore.HitareaDelegate,IHitarea {
    private HitareaCore mHelper;

    public HitareaWrapper(Context context) {
        this(context, null);
    }

    public HitareaWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HitareaWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new HitareaCore(context,attrs,defStyleAttr);
        this.setWillNotDraw(!mHelper.isDebug());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(HitareaCore.sDefaultDebugBgColor2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        return handled || mHelper.onTouchEvent(event,this);
    }

    @Override
    public View getTargetView(int targetViewId) {
        View target = findViewById(targetViewId);
        if ( target == null ) target = findViewWithTag(this.getContext().getString(R.string.tag_hitarea));
        if ( target == null && getChildCount() > 0 ) target = getChildAt(0);
        return target;
    }

    @Override
    public View getHitareaView() {
        return this;
    }

    @Override
    public void setTarget(View view) {
        mHelper.setTarget(view);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mHelper.setOnClickListener(l);
    }
}
