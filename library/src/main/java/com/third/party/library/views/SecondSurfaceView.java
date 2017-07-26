package com.third.party.library.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ZY on 2017/6/13.
 */

public class SecondSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    /**
     * 是否处于绘制状态
     */
    private boolean mIsDrawing;
    /**
     * 帮助类
     */
    private SurfaceHolder mHolder;
    /**
     * 画布
     */
    private Canvas mCanvas;
    /**
     * 路径
     */
    private Path mPath;
    private Path mPath1;
    private Path mPath2;
    private Path mPath3;
    /**
     * 画笔
     */
    private Paint mPaint;
    private int mPainttype;
    private int mX;
    private int mY;

    public SecondSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public SecondSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SecondSurfaceView(Context context) {
        super(context);
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pathMoveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                pathlineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private void pathlineTo(int x, int y) {

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3)
        {
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            switch (mPainttype){
                case 0:mPath.quadTo(previousX, previousY, cX, cY);
                    break;
                case 1:mPath1.quadTo(previousX, previousY, cX, cY);
                    break;
                case 2:mPath2.quadTo(previousX, previousY, cX, cY);
                    break;
                case 3:mPath3.quadTo(previousX, previousY, cX, cY);
                    break;
            }
            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }

    }

    private void pathMoveTo(int x, int y) {

        mX = x;
        mY = y;
        switch (mPainttype){
            case 0:mPath.moveTo(x, y);
                break;
            case 1:mPath1.moveTo(x, y);
                break;
            case 2:mPath2.moveTo(x, y);
                break;
            case 3:mPath3.moveTo(x, y);
                break;
        }

    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        doDrawing();
    }



    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (mIsDrawing) {
            draw();
        }
        long end = System.currentTimeMillis();
        if (end - start < 100) {
            try {
                Thread.sleep(100 - (end - start));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        mIsDrawing = false;
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            switch (mPainttype){
                case 0:
                    mCanvas.drawPath(mPath, mPaint);
                    break;
                case 1:
                    mCanvas.drawPath(mPath1, mPaint);
                    break;
                case 2:
                    mCanvas.drawPath(mPath2, mPaint);
                    break;
                case 3:
                    mCanvas.drawPath(mPath3, mPaint);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void initXiangpi(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
//        mPaint.setColor(mPaintColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
        mPaint.setAlpha(0); ////
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    //设置画笔
    public void doDrawing() {
        mPainttype=0;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeWidth(20);
    }

    ////橡皮擦
    public void setEraser(int i){
        mPaint = new Paint();
        mPath = new Path();
        mPath1 = new Path();
        mPath2 = new Path();
        mPath3 = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(i);
//        mPaint.setColor(mPaintColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //圆头
        mPaint.setDither(true);//消除拉动，使画面圓滑
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合方式，平滑
        mPaint.setAlpha(0); ////
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    /**
     * 清除内容
     */
    public void clean() {
        mCanvas.drawColor(Color.BLACK);
        initView();
    }
}
