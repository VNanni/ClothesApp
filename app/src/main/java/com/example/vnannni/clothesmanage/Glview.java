package com.example.vnannni.clothesmanage;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.List;

public class Glview extends GLSurfaceView {
    private ModeRender mRender;
    public Glview(Context context) {
        super(context);
    }
    public Glview(Context context,AttributeSet attrs) {
        super(context,attrs);
    }

    public void setrender(List<Obj3D> o,String v,String f){
        setEGLContextClientVersion(2);
        this.mRender=new ModeRender(o,v,f);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        float x = e.getX();
//        float y = e.getY();
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                float dx = x - mPreviousX; // 从左往有滑动时: x 值增大，dx 为正；反之则否。
//                float dy = y - mPreviousY; // 从上往下滑动时: y 值增大，dy 为正；反之则否。
//
//                // OpenGL 绕 z 轴的旋转符合左手定则，即 z 轴朝屏幕里面为正。
//                // 用户面对屏幕时，是从正面向里看（此时 camera 所处的 z 坐标位置为负数），当旋转度数增大时会进行逆时针旋转。
//
//                // 逆时针旋转判断条件1：触摸点处于 view 水平中线以下时，x 坐标应该要符合从右往左移动，此时 x 是减小的，所以 dx 取负数。
//                if (y > getHeight() / 2) {
//                    dx = dx * -1 ;
//                }
//
//                // 逆时针旋转判断条件2：触摸点处于 view 竖直中线以左时，y 坐标应该要符合从下往上移动，此时 y 是减小的，所以 dy 取负数。
//                if (x < getWidth() / 2) {
//                    dy = dy * -1 ;
//                }
//
//                mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
//
//                // 在计算旋转角度后，调用requestRender()来告诉渲染器现在可以进行渲染了
//                requestRender();
//        }
//        return true;
//    }
}
