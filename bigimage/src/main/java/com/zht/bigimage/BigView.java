package com.zht.bigimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

public class BigView extends View implements GestureDetector.OnGestureListener,View.OnTouchListener {
    private Rect mRect;
    private BitmapFactory.Options mOptions;
    private int mImageWidth;
    private int mImageHeight;
    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private BitmapRegionDecoder mDecoder;
    private Bitmap bitmap;
    private GestureDetector mGestureDetector;
    private Scroller mScroller;
    public BigView(Context context) {
        this(context,null,0);
    }

    public BigView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(context,this);
        setOnTouchListener(this);
        mScroller = new Scroller(context);
    }

    public void setImage(InputStream is){
        //先读取图片的宽高
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;
        //复用
        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inJustDecodeBounds = false;
        //创建区域解码器，用于区域解码图片
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取图片要加载的宽高
        mViewWidth = getMeasuredWidth();
        mViewHeight =  getMeasuredHeight();
        if(null == mDecoder){
            return;
        }
        //确定要加载图片的区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;
        mScale = mViewWidth/(float)mImageWidth;
        mRect.bottom = (int) (mViewHeight/mScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == mDecoder){
            return;
        }
        //复用上一张bitmap
        mOptions.inBitmap = bitmap;
        //解码制定区域
        bitmap = mDecoder.decodeRegion(mRect,mOptions);
        //使用矩阵对图片进行缩放
        Matrix matrix = new Matrix();
        matrix.setScale(mScale,mScale);
        canvas.drawBitmap(bitmap,matrix,null);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(!mScroller.isFinished()){
            mScroller.forceFinished(true);
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset(0, (int) distanceY);
        //bottom 大于图片的高了，或者top小于0了
        if(mRect.bottom>mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = (int) (mImageHeight-mViewHeight/mScale);
        }
        if(mRect.top<0){
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight/mScale);
        }
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(0,mRect.top,
                0,-(int)velocityY,
                0,0,
                0, (int) (mImageHeight-mImageHeight/mScale));
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.isFinished()){
            return;
        }
        if(mScroller.computeScrollOffset()){
            mRect.top = mScroller.getCurrY();
            mRect.bottom = (int) (mRect.top+mViewHeight/mScale);
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
