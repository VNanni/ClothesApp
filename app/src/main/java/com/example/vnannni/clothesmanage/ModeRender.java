package com.example.vnannni.clothesmanage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class ModeRender implements GLSurfaceView.Renderer {
    public List<Obj3D> obj=new ArrayList<>();
    private Shader mshader;
    private int textureId;
    private int mHTexture;
    private int mMVPMatrixHandle;

    private float []mMVPMatrix={
            1,0,0,0,
            0,1,0,0,
            0, 0,-1,0,
            0, 0,0,1
    };

    public ModeRender(List<Obj3D> o,String v,String f){
        for(int i=0;i<o.size();i++){
            obj.add(o.get(i));
        }
        mshader=new Shader(v,f);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        gl10.glEnable(GL10.GL_CULL_FACE);
        gl10.glClearColor(0.0F,1.0F,1.0F,1.0F);
        mshader.createprogram();
        for(int i=0;i<obj.size();i++) {
            if (obj.get(i).textbuffer != null&&obj.get(i).BM!=null) {
                textureId = createTexture(obj.get(i).BM);
                obj.get(i).setTextureID(textureId);
            }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0,700,width,height/2);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        int mposition;
        GLES20.glUseProgram(mshader.mprogram);
        for(int i=0;i<obj.size();i++) {
//            mHNormal=GLES20.glGetAttribLocation(mshader.mprogram,"vNormal");
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mshader.mprogram, "uMVPMatrix");
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

            mposition = GLES20.glGetAttribLocation(mshader.mprogram, "vPosition");
            GLES20.glEnableVertexAttribArray(mposition);
            GLES20.glVertexAttribPointer(mposition, 3, GLES20.GL_FLOAT, false, 12, obj.get(i).vertbuffer);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, obj.get(i).textureID);
            mHTexture = GLES20.glGetUniformLocation(mshader.mprogram, "vTexture");
            GLES20.glUniform1i(mHTexture, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, obj.get(i).vertCount);
            GLES20.glDisableVertexAttribArray(mposition);
        }
    }

    private int createTexture(Bitmap bitmap){
        int[] texture=new int[1];
        if(bitmap!=null&&!bitmap.isRecycled()){
            //生成纹理
            GLES20.glGenTextures(1,texture,0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            return texture[0];
        }
        return 0;
    }
}
