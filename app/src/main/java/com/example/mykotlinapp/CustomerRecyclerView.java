package com.example.mykotlinapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

public class CustomerRecyclerView extends RecyclerView {

    private ViewParent mViewParent;

    public CustomerRecyclerView(Context context) {
        super(context);
    }

    public CustomerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setViewParent(@Nullable final ViewParent viewParent) { //any ViewGroup
        mViewParent = viewParent;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
//    @Override
//    public boolean onInterceptTouchEvent(final MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                Log.e("ahihi", "onInterceptTouchEvent: down");
////                if (null == mViewParent) {
////                } else {
////                    mViewParent.requestDisallowInterceptTouchEvent(true);
////                }
//                break;
//            case MotionEvent.ACTION_UP:
//                getParent().requestDisallowInterceptTouchEvent(false);
//                Log.e("ahihi", "onInterceptTouchEvent: up");
////                if (null == mViewParent) {
////                } else {
////                    mViewParent.requestDisallowInterceptTouchEvent(false);
////                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            default:
//                break;
//        }
//
//        return super.onInterceptTouchEvent(event);
//    }
}

