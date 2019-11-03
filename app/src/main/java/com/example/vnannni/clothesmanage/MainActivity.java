package com.example.vnannni.clothesmanage;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private Obj3D obj=new Obj3D();
    private GLSurfaceView msurfaceview;
    private int mHNormal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("OPenGL");
        LoadvIndex();
        Loadvert();
        obj.setvIndex();
        System.out.print(obj.vertCount);
//        System.out.print(obj.vindexcount);
        obj.Handlevert();
//        obj.setvertNorl();
        setContentView(R.layout.activity_main);
        msurfaceview=findViewById(R.id.myGLsurface);
//        System.out.print(obj.vertCount);
//        System.out.print(obj.vindexcount);
        msurfaceview.setEGLContextClientVersion(2);
        msurfaceview.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
                gl10.glEnable(GL10.GL_CULL_FACE);
                gl10.glClearColor(0.0F,1.0F,1.0F,1.0F);
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int width, int heigh) {
                gl10.glViewport(0,700,width,heigh/2);
//                float ratio =(float)width/heigh;
//                gl10.glMatrixMode(GL10.GL_PROJECTION);
//                gl10.glLoadIdentity();
//                gl10.glFrustumf(-ratio,ratio,-1,1,1,10);
            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                gl10.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
//                gl10.glMatrixMode(GL10.GL_MODELVIEW);
//                gl10.glLoadIdentity();
//                gl10.glFrontFace(GL10.GL_CW);
//                gl10.glTranslatef(0.0f,0.0f,-3.0f);
//                gl10.glScalef(1.0f,1.0f,1.0f);
                int program;
                int mposition;
                int mcolor;
                float color[] = { 0.0f, 120.0f, 120.0f, 1.0f };
                String vertexShaderCode =
                        "attribute vec3 vPosition;" +
                                "void main() {" +
                                "  gl_Position = vec4(vPosition,1.8);" +
                                "}";

                String fragmentShaderCode =
                        "precision mediump float;" +
                                "uniform vec4 vColor;" +
                                "void main() {" +
                                "  gl_FragColor = vColor;" +
                                "}";
                int ver =ModeRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
                int fra =ModeRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
                program = GLES20.glCreateProgram();
                GLES20.glAttachShader(program,ver);
                GLES20.glAttachShader(program,fra);
                GLES20.glLinkProgram(program);
                GLES20.glUseProgram(program);
//                mHNormal=GLES20.glGetAttribLocation(program,"vNormal");
//                GLES20.glVertexAttribPointer(mHNormal,3, GLES20.GL_FLOAT, false, 12,obj.vertnorlbuffer)
                mposition = GLES20.glGetAttribLocation(program,"vPosition");
                GLES20.glEnableVertexAttribArray(mposition);
                GLES20.glVertexAttribPointer(mposition, 3, GLES20.GL_FLOAT, false, 12, obj.vertbuffer);
                mcolor = GLES20.glGetAttribLocation(program,"vColor");
                GLES20.glUniform4fv(mcolor,1,color,0);
                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,obj.vindexcount);
                GLES20.glDisableVertexAttribArray(mposition);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        msurfaceview.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        msurfaceview.onPause();
    }

    private void Loadvert(){
//        ArrayList<Float> alv=new ArrayList<>();//原始顶点坐标列表
        try{
            InputStreamReader isr=new InputStreamReader(getAssets().open("3dres/wman.obj"));
            BufferedReader br=new BufferedReader(isr);
            String temps;
            while((temps=br.readLine())!=null)
            {
                String[] tempsa=temps.split("[ ]+");
                if(tempsa[0].trim().equals("v")) {//此行为顶点坐标
                    obj.vert.add(Float.parseFloat(tempsa[1]));
                    obj.vert.add(Float.parseFloat(tempsa[2]));
                    obj.vert.add(Float.parseFloat(tempsa[3]));
                    obj.vertCount+=1;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void LoadvIndex(){
//        ArrayList<Integer> alv1=new ArrayList<>();//原始顶点坐标列表
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("3dres/wman.obj"));
            BufferedReader br = new BufferedReader(isr);
            String temps;
            while ((temps = br.readLine()) != null) {
                String[] tempsa=temps.split("[ ]+");
                if(tempsa[0].trim().equals("f")){
                    for (int i=1;i<4;i++){
                        String []tm=tempsa[i].split("/");
                        obj.vindex.add(Integer.parseInt(tm[0]));
                        System.out.print(Integer.parseInt(tm[0]));
                    }
                }
            }
        }catch(Exception e){ e.printStackTrace(); }
    }

    private void LoadvVertNorl(){
//        ArrayList<Integer> alv1=new ArrayList<>();//原始顶点坐标列表
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("3dres/patrick.obj"));
            BufferedReader br = new BufferedReader(isr);
            String temps;
            while ((temps = br.readLine()) != null) {
                String[] tempsa=temps.split("[ ]+");
                if(tempsa[0].trim().equals("vn")){
                    obj.vertnorl.add(Float.parseFloat(tempsa[1]));
                    obj.vertnorl.add(Float.parseFloat(tempsa[2]));
                    obj.vertnorl.add(Float.parseFloat(tempsa[3]));
                }
            }
        }catch(Exception e){ e.printStackTrace(); }
    }
}