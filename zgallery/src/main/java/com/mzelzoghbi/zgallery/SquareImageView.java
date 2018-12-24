package com.mzelzoghbi.zgallery;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by mohamedzakaria on 8/7/16.
 */
public class SquareImageView extends android.support.v7.widget.AppCompatImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMinimumHeight();
        setMeasuredDimension(width, height);
    }
}
