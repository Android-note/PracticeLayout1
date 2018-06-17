package com.hencoder.hencoderpracticelayout1.practice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author yangyi 2018年06月17日18:44:30
 */

public class SomeLayout extends ViewGroup {
    public SomeLayout(Context context) {
        super(context);
    }

    public SomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SomeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWidthSpec = 0;
        //已经使用了的空间的宽度
        int usedWidth = 50;
        //获取父类的ViewGroup限定的最多的宽度的mode和size，虽然只是一个限制，但是依据这个限制，可以得到一个可用空间
        int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            switch (layoutParams.width) {
                case LayoutParams.MATCH_PARENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY
                            || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        //selfWidthSpecSize - usedWidth代表可用空间
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth,
                                MeasureSpec.EXACTLY);
                    } else {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(0,
                                MeasureSpec.UNSPECIFIED);
                    }
                    break;
                case LayoutParams.WRAP_CONTENT:
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY
                            || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth,
                                MeasureSpec.AT_MOST);
                    } else {
                        childWidthSpec = MeasureSpec.makeMeasureSpec(0,
                                MeasureSpec.UNSPECIFIED);
                    }
                    break;
                default:
                    //开发者在xml布局文件中定义的是固定的尺寸值
                    childWidthSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
                    break;
            }
            setMeasuredDimension(childWidthSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }
    }
}
