package com.example.vnannni.clothesmanage;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;


public class Obj3D{
    public ArrayList<Float> vert =new ArrayList<>();
    public FloatBuffer vertbuffer;
    public int vertCount=0;

    public ArrayList<Float>VertNorl=new ArrayList<>();
    public FloatBuffer vertnorlbuffer;

    public ArrayList<Float> text =new ArrayList<>();
    public FloatBuffer textbuffer;

    public int textureID;
    public MtlInfo mtl;

    public Bitmap BM;


    public void addVert(Float aFloat) {
        vert.add(aFloat);
    }

    public void addVertTexture(Float aFloat) {
        text.add(aFloat);
    }

    public void addVertNorl(Float aFloat) {
        VertNorl.add(aFloat);
    }

    public void dataLock() {
        if(vert!=null){
            setVert(vert);
            vert.clear();
            vert=null;
        }
        if(text!=null){
            setVertTexture(text);
            text.clear();
            text=null;
        }
        if(VertNorl!=null){
            setVertNorl(VertNorl);
            VertNorl.clear();
            VertNorl=null;
        }
    }

    public void setVert(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertbuffer=buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vertbuffer.put(data.get(i));
        }
        vertbuffer.position(0);
        vertCount=size/3;
    }

    public void setVertNorl(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertnorlbuffer=buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vertnorlbuffer.put(data.get(i));
        }
        vertnorlbuffer.position(0);
    }

    public void setVertTexture(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        textbuffer=buffer.asFloatBuffer();
        for (int i=0;i<size;){
            textbuffer.put(data.get(i));
            i++;
        }
        textbuffer.position(0);
    }

    public void setTextureID(int id){
        this.textureID=id;
    }
}
