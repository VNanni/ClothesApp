package com.example.vnannni.clothesmanage;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class ModeRender implements GLSurfaceView.Renderer {
    public Obj3D obj;
//    private InputStream fs;
    public ModeRender(Obj3D o){
        this.obj=o;
        //obj=new Obj3D(s);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        obj=new Obj3D();
        gl10.glEnable(GL10.GL_CULL_FACE);
        gl10.glClearColor(0.0F,1.0F,1.0F,1.0F);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
          gl10.glViewport(0,0,i,i1);
//        float n = (float)i/i1;
//        gl10.glMatrixMode(GL10.GL_PROJECTION);
//        gl10.glLoadIdentity();
//        gl10.glFrustumf(-n,n,-1,1,1,10);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
//        gl10.glMatrixMode(GL10.GL_MODELVIEW);
//        gl10.glLoadIdentity();
//        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
//          obj.draw();
    }

    public static int loadShader(int type, String shaderCode){

        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // 添加上面编写的着色器代码并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
