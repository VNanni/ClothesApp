package com.example.vnannni.clothesmanage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Obj3DLoad extends AppCompatActivity {
    public List<Obj3D> obj;
    private Glview msurfaceview;
    private ModeRender mGLRender;
    public String vertCode;
    public String fragCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShowObj();
    }
    private void ShowObj(){
        setTitle("模型捏造");
        LoadCode();
        System.out.print(vertCode);
        obj=readMultiObj();
        LoadMap();
        msurfaceview=new Glview(this);
        setContentView(R.layout.activity_main);
        msurfaceview = findViewById(R.id.myGLsurface);
        msurfaceview.setrender(obj,vertCode,fragCode);
//        msurfaceview.setEGLContextClientVersion(2);
//        mGLRender = new ModeRender(obj,vertCode,fragCode);
//        msurfaceview.setRenderer(mGLRender);
//        msurfaceview.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
//        msurfaceview.setFocusableInTouchMode(true);
//        msurfaceview.requestFocus();
    }


    public HashMap<String,MtlInfo> readMtl(InputStream stream){
        HashMap<String,MtlInfo> map=new HashMap<>();
        try{
            InputStreamReader isr=new InputStreamReader(stream);
            BufferedReader br=new BufferedReader(isr);
            String temps;
            MtlInfo mtlInfo =new MtlInfo();
            while((temps=br.readLine())!=null)
            {
                String[] tempsa=temps.split("[ ]+");
                switch (tempsa[0].trim()){
                    case "newmtl":  //材质
                        mtlInfo=new MtlInfo();
                        mtlInfo.newmtl=tempsa[1];
                        map.put(tempsa[1],mtlInfo);
                        break;
                    case "illum":     //光照模型
                        mtlInfo.illum=Integer.parseInt(tempsa[1]);
                        break;
                    case "Kd":
                        read(tempsa,mtlInfo.Kd);
                        break;
                    case "Ka":
                        read(tempsa,mtlInfo.Ka);
                        break;
                    case "Ke":
                        read(tempsa,mtlInfo.Ke);
                        break;
                    case "Ks":
                        read(tempsa,mtlInfo.Ks);
                        break;
                    case "Ns":
                        mtlInfo.Ns=Float.parseFloat(tempsa[1]);
                        break;
                    case "map_Kd":
                        mtlInfo.map_Kd=tempsa[1];
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private void read(String[] value, ArrayList<Float> list){
        for (int i=1;i<value.length;i++){
            list.add(Float.parseFloat(value[i]));
        }
    }

    private void read(String[] value,float[] fv){
        for (int i=1;i<value.length;i++){
            fv[i-1]=Float.parseFloat(value[i]);
        }
    }

    public List<Obj3D> readMultiObj(){
        ArrayList<Obj3D> data=new ArrayList<>();
        ArrayList<Float> oVs=new ArrayList<>();     //原始顶点坐标列表
        ArrayList<Float> oVNs=new ArrayList<>();    //原始顶点法线列表
        ArrayList<Float> oVTs=new ArrayList<>();    //原始贴图坐标列表
        HashMap<String,MtlInfo> mTls=null;
        HashMap<String,Obj3D> mObjs=new HashMap<>();
        Obj3D nowObj=null;
        MtlInfo nowMtl=null;
        try{
            InputStreamReader isr=new InputStreamReader(getResources().getAssets().open("3dres/wm7.obj"));
            BufferedReader br=new BufferedReader(isr);
            String temps;
            while((temps=br.readLine())!=null){
                if("".equals(temps)){

                }else{
                    String[] tempsa=temps.split("[ ]+");
                    switch (tempsa[0].trim()){
                        case "mtllib":  //材质
                            mTls=readMtl(getResources().getAssets().open("3dres/wm7.mtl"));
                            break;
                        case "usemtl":  //采用纹理
                            if(mTls!=null){
                                nowMtl=mTls.get(tempsa[1]);
                            }
                            if(mObjs.containsKey(tempsa[1])){
                                nowObj=mObjs.get(tempsa[1]);
                            }else{
                                nowObj=new Obj3D();
                                nowObj.mtl=nowMtl;
                                mObjs.put(tempsa[1],nowObj);
                            }
                            break;
                        case "v":       //原始顶点
                            read(tempsa,oVs);
                            break;
                        case "vn":      //原始顶点法线
                            read(tempsa,oVNs);
                            break;
                        case "vt":    //原始纹理作坐标
                            read(tempsa,oVTs);
                            break;
                        case "f":
                            for (int i=1;i<tempsa.length;i++){
                                String[] fs=tempsa[i].split("/");
                                int index;
                                if(fs.length>0){
                                    //顶点索引
                                    index=Integer.parseInt(fs[0])-1;
                                    nowObj.addVert(oVs.get(index*3));
                                    nowObj.addVert(oVs.get(index*3+1));
                                    nowObj.addVert(oVs.get(index*3+2));
                                }
                                if(fs.length>1){
                                    //贴图
                                    index=Integer.parseInt(fs[1])-1;
                                    nowObj.addVertTexture(oVTs.get(index*2));
                                    nowObj.addVertTexture(oVTs.get(index*2+1));
                                }
                                if(fs.length>2){
                                    //法线索引
                                    index=Integer.parseInt(fs[2])-1;
                                    nowObj.addVertNorl(oVNs.get(index*3));
                                    nowObj.addVertNorl(oVNs.get(index*3+1));
                                    nowObj.addVertNorl(oVNs.get(index*3+2));
                                }
                            }
                            break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for (Map.Entry<String, Obj3D> stringObj3DEntry : mObjs.entrySet()) {
            Obj3D obj = stringObj3DEntry.getValue();
            data.add(obj);
            obj.dataLock();
        }
        return data;
    }

    public void LoadCode() {
        String s="";
        try{
            InputStreamReader isr=new InputStreamReader(getResources().getAssets().open("3dres/obj.vert"));
            BufferedReader br=new BufferedReader(isr);
            String temps;
            while((temps=br.readLine())!=null)
            {
                s+=temps;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        vertCode=s;
        s="";
        try{
            InputStreamReader isr=new InputStreamReader(getResources().getAssets().open("3dres/obj.frag"));
            BufferedReader br=new BufferedReader(isr);
            String temps;
            while((temps=br.readLine())!=null)
            {
                s+=temps;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        fragCode=s;
    }

    public void LoadMap(){
        try{
            for(int i=0;i<obj.size();i++) {
                String tm = obj.get(i).mtl.map_Kd;
                if (tm != null) {
                    obj.get(i).BM=BitmapFactory.decodeStream(getAssets().open("3dres/" + obj.get(i).mtl.map_Kd));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
}
