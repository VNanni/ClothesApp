package com.example.vnannni.clothesmanage;


import android.app.Activity;
import android.opengl.GLES20;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.Display;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;


public class Obj3D extends AppCompatActivity {
    public ArrayList<Float> vert =new ArrayList<>();
    public FloatBuffer vertbuffer;
    public int vertCount=0;

//    public FloatBuffer text;
//    public int texturecount;
//
    public ArrayList<Integer> vindex=new ArrayList<>();
    public IntBuffer vindexbuffer;
    public int vindexcount=0;

    public ArrayList<Float> vertnorl;
    public FloatBuffer vertnorlbuffer;
//
//    public IntBuffer tindex;
//    public int tindexcount;

    private FloatBuffer colorbufer;
    public float color[] = { 255, 250, 0, 1.0f };

    public int program;
    public int mposition;
    public int mcolor;

    private static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    //--------------------------------------------------
    public Obj3D()
    {
//        int ver =ModeRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
//        int fra =ModeRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
//        program = GLES20.glCreateProgram();
//        GLES20.glAttachShader(program,ver);
//        GLES20.glAttachShader(program,fra);
//        GLES20.glLinkProgram(program);
    }

//    ////读取顶点坐标------------------------------------
//    public void readvert(InputStream stream){
//        ArrayList<Float> alv=new ArrayList<>();//原始顶点坐标列表
//        try{
//            InputStreamReader isr=new InputStreamReader();
//            BufferedReader br=new BufferedReader(isr);
//            String temps;
//            while((temps=br.readLine())!=null)
//            {
//                String[] tempsa=temps.split(" ");
//                if(tempsa[0].trim().equals("v")) {//此行为顶点坐标
//                    alv.add(Float.parseFloat(tempsa[1]));
//                    alv.add(Float.parseFloat(tempsa[2]));
//                    alv.add(Float.parseFloat(tempsa[3]));
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        setVert(alv);
//    }

//    ///读取纹理坐标-------------------------------------------
//    public void readtexture(InputStream stream){
//        ArrayList<Float> alv=new ArrayList<>();//原始顶点坐标列表
//        try{
//            InputStreamReader isr=new InputStreamReader(stream);
//            BufferedReader br=new BufferedReader(isr);
//            String temps;
//            while((temps=br.readLine())!=null)
//            {
//                String[] tempsa=temps.split("[ ]+");
//                if(tempsa[0].trim().equals("vt")) {//此行为顶点坐标
//                    alv.add(Float.parseFloat(tempsa[1]));
//                    alv.add(Float.parseFloat(tempsa[2]));
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        setTexture(alv);
//    }

//    ///读取顶点索引
//    public void readvindex(){
//        try {
//            InputStreamReader isr = new InputStreamReader(getAssets().open("3dres/wman.obj"));
//            BufferedReader br = new BufferedReader(isr);
//            String temps;
//            while ((temps = br.readLine()) != null) {
//                String[] tempsa=temps.split("[ ]+");
//                if(tempsa[0].trim().equals("f")){
//                    if(tempsa.length==5){
//                        for (int i=1;i<4;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv1.add(Integer.parseInt(tm[0]));
//                        }
//                        for(int i=2;i<5;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv1.add(Integer.parseInt(tm[0]));
//                        }
//                    }else{
//                        for (int i=1;i<4;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv1.add(Integer.parseInt(tm[0]));
//                        }
//                    }
//                }
//        }
//        }catch(Exception e){ e.printStackTrace(); }
//        setvIndex(alv1);
//    }

//    ///读取纹理坐标索引
//    public void readtindex(InputStream stream){
//        ArrayList<Integer> alv=new ArrayList<>();//原始顶点坐标列表
//        try {
//            InputStreamReader isr = new InputStreamReader(stream);
//            BufferedReader br = new BufferedReader(isr);
//            String temps;
//            while ((temps = br.readLine()) != null) {
//                String[] tempsa=temps.split("[ ]+");
//                if(tempsa[0].trim().equals("f")){
//                    if(tempsa.length==5){
//                        for (int i=1;i<4;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv.add(Integer.parseInt(tm[1]));
//                        }
//                        for(int i=2;i<5;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv.add(Integer.parseInt(tm[1]));
//                        }
//                    }else{
//                        for (int i=1;i<4;i++){
//                            String []tm=tempsa[i].split("/");
//                            alv.add(Integer.parseInt(tm[1]));
//                        }
//                    }
//                }
//            }
//        }catch(Exception e){ e.printStackTrace(); }
//        settIndex(alv);
//    }

//    ////将顶点坐标放到Float缓存里-------------------------------
//    public void setVert(ArrayList<Float> data){
//        int size=data.size();
//        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
//        buffer.order(ByteOrder.nativeOrder());
//        vert=buffer.asFloatBuffer();
//        for (int i=0;i<data.size();i++){
//            vert.put(data.get(i));
//        }
//        vert.position(0);
//        vertCount=size/3;
//    }

    public void setvIndex() {
        int size=vindex.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vindexbuffer=buffer.asIntBuffer();
        for (int i=0;i<size;i++){
            vindexbuffer.put(vindex.get(i));
        }
        vindexcount = size;
        vindexbuffer.position(0);
    }

    public void setvertNorl() {
        int size=vertnorl.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertnorlbuffer=buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vertnorlbuffer.put(vertnorl.get(i));
        }
        vertnorlbuffer.position(0);
    }

//    ///将提取的纹理坐标放到Float缓存里
//    private void setTexture(ArrayList<Float> data){
//        int size=data.size();
//        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
//        buffer.order(ByteOrder.nativeOrder());
//        text=buffer.asFloatBuffer();
//        for (int i=0;i<size;i++){
//            text.put(data.get(i));
//        }
//        text.position(0);
//        texturecount=size/2;
//    }
//
//    private void settIndex(ArrayList<Integer> data){
//        int size=data.size();
//        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
//        buffer.order(ByteOrder.nativeOrder());
//        tindex=buffer.asIntBuffer();
//        for (int i=0;i<size;i++){
//            tindex.put(data.get(i));
//        }
//        tindexcount = size;
//        tindex.position(0);
//    }

//    private void setColor() {
//        ByteBuffer buffer=ByteBuffer.allocateDirect(color.length*4);
//        buffer.order(ByteOrder.nativeOrder());
//        colorbufer=buffer.asFloatBuffer();
//        for (int i=0;i<color.length;i++){
//            colorbufer.put(color[i]);
//        }
//        colorbufer.position(0);
//    }

    public void Handlevert(){
        int size=vert.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(vindexcount*4*3);
        buffer.order(ByteOrder.nativeOrder());
        vertbuffer=buffer.asFloatBuffer();
        for (int i=0;i<vindexcount;i++){
            int tm = vindex.get(i)-1;
            vertbuffer.put(vert.get(tm*3));
            vertbuffer.put(vert.get(tm*3+1));
            vertbuffer.put(vert.get(tm*3+2));
        }
        vertCount=size;
        vertbuffer.position(0);
    }

    public void Setvert(){
        int size=vert.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(vertCount*4);
        buffer.order(ByteOrder.nativeOrder());
        vertbuffer=buffer.asFloatBuffer();
        for (int i=0;i<vertCount;i++){
            vertbuffer.put(vert.get(i));
        }
        vertCount=size;
        vertbuffer.position(0);
    }

//    public void draw(){
//        GLES20.glUseProgram(program);
//        mposition = GLES20.glGetAttribLocation(program,"vPosition");
//        GLES20.glEnableVertexAttribArray(mposition);
//        GLES20.glVertexAttribPointer(mposition, 3,
//                GLES20.GL_FLOAT, false,
//                12, vertbuffer);
//        mcolor = GLES20.glGetAttribLocation(program,"vColor");
//        GLES20.glUniform4fv(mcolor,1,color,0);
//        GLES20.glDrawElements(GL10.GL_LINE_STRIP,vertCount,GL10.GL_UNSIGNED_BYTE,vindexbuffer);
////        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP,0,vertCount);
//        GLES20.glDisableVertexAttribArray(mposition);
//        }
}
