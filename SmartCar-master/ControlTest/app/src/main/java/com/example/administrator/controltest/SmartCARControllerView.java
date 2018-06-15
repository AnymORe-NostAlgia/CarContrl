package com.example.administrator.controltest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class SmartCARControllerView extends View {
    //ÆÁÄ»³ß´ç
    private int mScreenWidth, mScreenHeight;
    private double mZoomWidth, mZoomHeight;

    private Handler mHandler;

    private Rect[] mTouchArea;

    private int mBtn_x, mBtn_y;
    private int mInitBtn_x, mInitBtn_y;
    private int mCar_x, mCar_y;
    private int mSensor_x, mSensor_y;
    private int mUltra_x, mUltra_y;

    private Bitmap mImgBg;//±³¾°Í¼
    private Bitmap[] mImgBtn;//Í¼Æ¬°´Å¥
    private Bitmap[] mImgCar;//Æû³µÍ¼Æ¬
    private Bitmap[] mImgSensor;//´«¸ÐÆ÷
    private Bitmap[] mImgUltra;//³¬Éù²¨

    private boolean mBtnTouched;
    private boolean mSpeedUp;
    private int mSpeed;
    private int mLastCarArray;
    private int mCarMode;

    private boolean mSensor, mUltra;

    private String[] mSensorValue = new String[12];
    private String[] mAccel = new String[3];
    private String[] mEncoder = new String[2];
    private String[] mGyro = new String[3];
    private String mInfrared;

    Paint mSTitlePaint = new Paint();
    Paint mSValuePaint = new Paint();
    Paint mUltraPaint = new Paint();

    public SmartCARControllerView(Context context){
        super(context);
    }

    public SmartCARControllerView(Context context, Handler handle) {
        super(context);
        mHandler = handle;

        initScreenSize(context);
        initTouchArea();
        initImage();

        mBtn_x = (mScreenWidth/2) - (mImgBtn[0].getWidth()/2);
        mBtn_y = (int)(995*mZoomHeight) - (mImgBtn[0].getHeight()/2);

        mInitBtn_x = mBtn_x;
        mInitBtn_y = mBtn_y;
        mBtnTouched = false;
        mSensor = false;
        mUltra = false;
        mSpeed = 0;
        mLastCarArray = -1;
        mCar_x = (mScreenWidth/2)-(mImgCar[0].getWidth()/2);
        mCar_y = (int)(mZoomWidth*285);

        mSensor_x = mInitBtn_x-(int)(mZoomWidth*135)-mImgSensor[0].getWidth();
        mSensor_y = (int)(mZoomHeight*780);

        mUltra_x = mInitBtn_x+(int)(mZoomWidth*135)+mImgBtn[0].getWidth();
        mUltra_y = (int)(mZoomHeight*780);

        mUltraPaint.setTextSize(20);
        mUltraPaint.setARGB(255, 128, 128, 128);

        mSTitlePaint.setTextSize(23);
        mSTitlePaint.setARGB(255, 92, 92, 92);

        mSValuePaint.setTextSize(23);
        mSValuePaint.setARGB(255, 128, 128, 128);

        for(int i=0;i<12;i++){
            mSensorValue[i] = new String("0000");
        }

        for(int i=0;i<3;i++){
            mAccel[i] = new String(" -00000");
            mGyro[i] = new String(" -00000");
        }

        for(int i=0;i<2;i++){
            mEncoder[i] = new String("00000");
        }

        mInfrared = new String("00000000");
    }

    @SuppressLint("NewApi") private void initScreenSize(Context context){
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        mScreenWidth = point.x;
        mScreenHeight = point.y;
        mZoomWidth = (double)mScreenWidth/(double)800;
        mZoomHeight = (double)mScreenHeight/(double)1280;
    }


    //¹ØÁªÏàÓ¦µÄtupian
    private void initImage(){
        mImgBtn = new Bitmap[5];
        mImgCar = new Bitmap[3];
        mImgSensor = new Bitmap[2];
        mImgUltra = new Bitmap[2];

        mImgBg = createImageAllScreen(R.drawable.bg);
        mImgBtn[0] = createImage(R.drawable.btn1);
        mImgBtn[1] = createImage(R.drawable.btn2);
        mImgBtn[2] = createImage(R.drawable.btn3);
        mImgBtn[3] = createImage(R.drawable.btn4);
        mImgBtn[4] = createImage(R.drawable.btn5);

        mImgCar[0] = createImage(R.drawable.connect);
        mImgCar[1] = createImage(R.drawable.connecting);
        mImgCar[2] = createImage(R.drawable.connected);

        mImgSensor[0] = createImage(R.drawable.sensor_off);
        mImgSensor[1] = createImage(R.drawable.sensor_on);

        mImgUltra[0] = createImage(R.drawable.ultra_off);
        mImgUltra[1] = createImage(R.drawable.ultra_on);
    }

    private void initTouchArea(){
        mTouchArea = new Rect[8];
        mTouchArea[SmartMessage.TOP_LEFT] = new Rect((int)(185*mZoomWidth),(int)(775*mZoomHeight), (int)(340*mZoomWidth), (int)(930*mZoomHeight));
        mTouchArea[SmartMessage.TOP_CENTER] = new Rect((int)(340*mZoomWidth),(int)(775*mZoomHeight), (int)(460*mZoomWidth), (int)(910*mZoomHeight));
        mTouchArea[SmartMessage.TOP_RIGHT] = new Rect((int)(460*mZoomWidth),(int)(775*mZoomHeight), (int)(615*mZoomWidth), (int)(930*mZoomHeight));
        mTouchArea[SmartMessage.LEFT] = new Rect((int)(185*mZoomWidth),(int)(930*mZoomHeight), (int)(320*mZoomWidth), (int)(1055*mZoomHeight));
        mTouchArea[SmartMessage.RIGHT] = new Rect((int)(480*mZoomWidth),(int)(930*mZoomHeight), (int)(615*mZoomWidth), (int)(1055*mZoomHeight));
        mTouchArea[SmartMessage.BOTTOM_LEFT] = new Rect((int)(185*mZoomWidth),(int)(1055*mZoomHeight), (int)(340*mZoomWidth), (int)(1215*mZoomHeight));
        mTouchArea[SmartMessage.BOTTOM_CENTER] = new Rect((int)(340*mZoomWidth),(int)(1075*mZoomHeight), (int)(460*mZoomWidth), (int)(1215*mZoomHeight));
        mTouchArea[SmartMessage.BOTTOM_RIGHT] = new Rect((int)(460*mZoomWidth),(int)(1055*mZoomHeight), (int)(615*mZoomWidth), (int)(1215*mZoomHeight));
    }

    private Bitmap createImage(int r){
        Resources res = getResources();
        Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);
        int w = (int)(imgPrevConv.getWidth()*mZoomWidth);
        int h = (int)(imgPrevConv.getHeight()*mZoomHeight);
        return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
    }

    private Bitmap createImageAllScreen(int r){
        Resources res = getResources();
        Bitmap imgPrevConv = BitmapFactory.decodeResource(res, r);
        int w = mScreenWidth;
        int h = mScreenHeight;
        return Bitmap.createScaledBitmap(imgPrevConv, w, h, true);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(mImgBg, 0, 0, null);
        canvas.drawBitmap(mImgCar[mCarMode], mCar_x, mCar_y, null);

        if(mSensor) canvas.drawBitmap(mImgSensor[1], mSensor_x, mSensor_y, null);
        else canvas.drawBitmap(mImgSensor[0], mSensor_x, mSensor_y, null);
        if(mUltra) canvas.drawBitmap(mImgUltra[1], mUltra_x, mUltra_y, null);
        else canvas.drawBitmap(mImgUltra[0], mUltra_x, mUltra_y, null);

        canvas.drawBitmap(mImgBtn[mSpeed], mBtn_x, mBtn_y, null);

        canvas.drawText("Accel", (int)(60*mZoomWidth), (int)(90*mZoomHeight), mSTitlePaint);
        canvas.drawText("x:"+mAccel[0]+" y:"+mAccel[1]+" z:"+mAccel[2], (int)(130*mZoomWidth), (int)(90*mZoomHeight), mSValuePaint);
        canvas.drawText("Encoder", (int)(460*mZoomWidth), (int)(90*mZoomHeight), mSTitlePaint);
        canvas.drawText("L:"+mEncoder[0]+" R:"+mEncoder[1], (int)(560*mZoomWidth), (int)(90*mZoomHeight), mSValuePaint);
        canvas.drawText("Gyro", (int)(65*mZoomWidth), (int)(130*mZoomHeight), mSTitlePaint);
        canvas.drawText("x:"+mGyro[0]+" y:"+mGyro[1]+" z:"+mGyro[2], (int)(130*mZoomWidth), (int)(130*mZoomHeight), mSValuePaint);
        canvas.drawText("Infrared", (int)(462*mZoomWidth), (int)(130*mZoomHeight), mSTitlePaint);
        canvas.drawText(mInfrared, (int)(560*mZoomWidth), (int)(130*mZoomHeight), mSValuePaint);

        canvas.drawText(mSensorValue[3], (int)(373*mZoomWidth), (int)(210*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[9], (int)(373*mZoomWidth), (int)(700*mZoomHeight), mUltraPaint);

        canvas.drawText(mSensorValue[2], (int)(160*mZoomWidth), (int)(255*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[1], (int)(160*mZoomWidth), (int)(300*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[0], (int)(160*mZoomWidth), (int)(460*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[7], (int)(160*mZoomWidth), (int)(585*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[8], (int)(160*mZoomWidth), (int)(630*mZoomHeight), mUltraPaint);

        canvas.drawText(mSensorValue[4], (int)(585*mZoomWidth), (int)(255*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[5], (int)(585*mZoomWidth), (int)(300*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[6], (int)(585*mZoomWidth), (int)(460*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[11], (int)(585*mZoomWidth), (int)(585*mZoomHeight), mUltraPaint);
        canvas.drawText(mSensorValue[10], (int)(585*mZoomWidth), (int)(630*mZoomHeight), mUltraPaint);

        canvas.drawText("START", (int)(585*mZoomWidth), (int)(180*mZoomHeight), mSValuePaint);

        super.onDraw(canvas);
    }

    private void touchSmartCAR(){
        mHandler.obtainMessage(SmartMessage.TOUCH).sendToTarget();
    }
    private void touchStart(){
        mHandler.obtainMessage(SmartMessage.AVOID).sendToTarget();
    }

    private void sendSmartCARSpeed(int speed){
        mHandler.obtainMessage(SmartMessage.SPEED, speed, 0).sendToTarget();
    }

    private void sendSmartCARCommand(int x, int y){
        for(int i=0;i<8;i++){
            if(	mTouchArea[i].left<x && mTouchArea[i].right>x &&
                    mTouchArea[i].top<y && mTouchArea[i].bottom>y){
                if(i!=mLastCarArray){
                    mLastCarArray = i;
                    mHandler.obtainMessage(SmartMessage.DIRECT, i, 0).sendToTarget();
                }
                break;
            }
        }
    }

    private boolean isCenterPosition(int x, int y){
        if(	x>mTouchArea[SmartMessage.TOP_LEFT].right && x<mTouchArea[SmartMessage.TOP_RIGHT].left &&
                y>mTouchArea[SmartMessage.TOP_LEFT].bottom && y<mTouchArea[SmartMessage.BOTTOM_LEFT].top
                ){
            return true;
        }
        else return false;

    }

    private void setBtnPosition(int x, int y){
        if(x<mTouchArea[SmartMessage.TOP_LEFT].left) mBtn_x = mTouchArea[SmartMessage.TOP_LEFT].left;
        else if(x>mTouchArea[SmartMessage.TOP_RIGHT].right) mBtn_x = mTouchArea[SmartMessage.TOP_RIGHT].right;
        else mBtn_x = x;

        if(y<mTouchArea[SmartMessage.TOP_LEFT].top) mBtn_y = mTouchArea[SmartMessage.TOP_LEFT].top;
        else if(y>mTouchArea[SmartMessage.BOTTOM_LEFT].bottom) mBtn_y = mTouchArea[SmartMessage.BOTTOM_LEFT].bottom;
        else mBtn_y = y;

        mBtn_x = mBtn_x-(mImgBtn[0].getWidth()/2);
        mBtn_y = mBtn_y-(mImgBtn[0].getHeight()/2);
        invalidate();
    }

    private boolean isCarTouched(int x, int y){
        if(	x>mCar_x && x<mCar_x+mImgCar[0].getWidth() &&
                y>mCar_y && y<mCar_y+mImgCar[0].getHeight()){
            return true;
        }
        return false;
    }
    private boolean isStartTouched(int x, int y){
        if(	x>(int)(585*mZoomWidth) && x<(int)(585*mZoomWidth)+40 &&
                y>(int)(630*mZoomHeight) && y<(int)(630*mZoomHeight)+40){
            return true;
        }
        return false;
    }

    private boolean isSensorTouched(int x, int y){
        if(	x>mSensor_x && x<mSensor_x+mImgSensor[0].getWidth() &&
                y>mSensor_y && y<mSensor_y+mImgSensor[0].getHeight()){
            return true;
        }
        return false;
    }

    private boolean isUltraTouched(int x, int y){
        if(	x>mUltra_x && x<mUltra_x+mImgUltra[0].getWidth() &&
                y>mUltra_y && y<mUltra_y+mImgUltra[0].getHeight()){
            return true;
        }
        return false;
    }

    public void setConnect(){
        mCarMode = 0;
        postInvalidate();
    }

    public void setConnected(){
        mCarMode = 2;
        postInvalidate();
    }

    public void setConnecting(){
        mCarMode = 1;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(isCarTouched(x, y)){
                System.out.print("isCarTouched...\n\n\n\n\n\n\n\n");
                touchSmartCAR();
                return true;
            }
            if(isStartTouched(x, y)){
                touchStart();
                postInvalidate();
            }
            if(isSensorTouched(x, y)){
                if(mSensor){
                    mHandler.obtainMessage(SmartMessage.SENSOR_OFF).sendToTarget();
                    mSensor = false;
                }
                else{
                    mHandler.obtainMessage(SmartMessage.SENSOR_ON).sendToTarget();
                    mSensor = true;
                }
                postInvalidate();
            }
            if(isUltraTouched(x, y)){
                if(mUltra){
                    mHandler.obtainMessage(SmartMessage.ULTRA_OFF).sendToTarget();
                    mUltra = false;
                }
                else{
                    mHandler.obtainMessage(SmartMessage.ULTRA_ON).sendToTarget();
                    mUltra = true;
                }
                postInvalidate();
            }
            if(	x>mTouchArea[SmartMessage.TOP_LEFT].left && x<mTouchArea[SmartMessage.TOP_RIGHT].right &&
                    y>mTouchArea[SmartMessage.TOP_LEFT].top && y<mTouchArea[SmartMessage.BOTTOM_LEFT].bottom){
                mSpeedUp = true;
                mBtnTouched = true;
                if(!isCenterPosition(x, y)){
                    setBtnPosition(x, y);
                    sendSmartCARCommand(x, y);
                    mSpeedUp = false;
                }
                return true;
            }
        } else if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(mBtnTouched){
                mSpeedUp = false;
                setBtnPosition(x, y);
                sendSmartCARCommand(x, y);
            }
            return true;
        } else if(event.getAction()==MotionEvent.ACTION_UP){
            if(mBtnTouched){
                mBtn_x = mInitBtn_x;
                mBtn_y = mInitBtn_y;
                mBtnTouched = false;

                if(mSpeedUp){
                    if(++mSpeed>=4) mSpeed = 0;
                    sendSmartCARSpeed(mSpeed);
                }

                mHandler.obtainMessage(SmartMessage.DIRECT, SmartMessage.CENTER, 0).sendToTarget();
                mLastCarArray = 8;

            }
            invalidate();
            return true;
        }

        return true;
    }

    public void setAccel(short x, short y, short z){
        String str;

        str = String.format("%05d", Math.abs(x));
        if(x>=0) str = "+"+str;
        else str = " -"+str;
        mAccel[0] = str;

        str = String.format("%05d", Math.abs(y));
        if(y>=0) str = "+"+str;
        else str = " -"+str;
        mAccel[1] = str;

        str = String.format("%05d", Math.abs(z));
        if(z>=0) str = "+"+str;
        else str = " -"+str;
        mAccel[2] = str;

        postInvalidate();
    }

    public void setEncoder(int l, int r){
        String str;

        str = String.format("%05d", l);
        mEncoder[0] = str;

        str = String.format("%05d", r);
        mEncoder[1] = str;

        postInvalidate();
    }

    public void setGyro(short x, short y, short z){
        String str;

        str = String.format("%05d", Math.abs(x));
        if(x>=0) str = "+"+str;
        else str = " -"+str;
        mGyro[0] = str;

        str = String.format("%05d", Math.abs(y));
        if(y>=0) str = "+"+str;
        else str = " -"+str;
        mGyro[1] = str;

        str = String.format("%05d", Math.abs(z));
        if(z>=0) str = "+"+str;
        else str = " -"+str;
        mGyro[2] = str;

        postInvalidate();
    }

    public void setInfrared(byte x){
        String str = "";

        for(int i=0;i<8;i++){
            str = String.valueOf((x>>i)&1)+str;
        }
        mInfrared = str;

        postInvalidate();
    }

    public void setUltra(int s1, int s2, int s3, int s4, int s5, int s6, int s7, int s8, int s9, int s10, int s11, int s12){
        mSensorValue[0] = String.format("%04d", s1);
        mSensorValue[1] = String.format("%04d", s2);
        mSensorValue[2] = String.format("%04d", s3);
        mSensorValue[3] = String.format("%04d", s4);
        mSensorValue[4] = String.format("%04d", s5);
        mSensorValue[5] = String.format("%04d", s6);
        mSensorValue[6] = String.format("%04d", s7);
        mSensorValue[7] = String.format("%04d", s8);
        mSensorValue[8] = String.format("%04d", s9);
        mSensorValue[9] = String.format("%04d", s10);
        mSensorValue[10] = String.format("%04d", s11);
        mSensorValue[11] = String.format("%04d", s12);

        postInvalidate();
    }

}
