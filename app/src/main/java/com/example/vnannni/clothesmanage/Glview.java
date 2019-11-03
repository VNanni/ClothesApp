package com.example.vnannni.clothesmanage;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.io.InputStream;

public class Glview extends GLSurfaceView {
    private final ModeRender mrender;
    public Glview(Context context,Obj3D o) {
        super(context);
        setEGLContextClientVersion(2);
        mrender = new ModeRender(o);
        setRenderer(mrender);
    }
}
