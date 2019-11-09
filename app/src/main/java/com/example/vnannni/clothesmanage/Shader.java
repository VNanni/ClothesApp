package com.example.vnannni.clothesmanage;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.example.vnannni.clothesmanage.BuildConfig.DEBUG;

public class Shader {

    public int mprogram;
    private static String vertCode;
    private static String fragCode;

    public Shader(String v,String f) {
        vertCode=v;
        fragCode=f;
    }

    public static int loadShader(int shaderType, String source){
        int shader= GLES20.glCreateShader(shaderType);
        if(0!=shader){
            GLES20.glShaderSource(shader,source);
            GLES20.glCompileShader(shader);
            int[] compiled=new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,compiled,0);
            if(compiled[0]==0){
                Log.e(TAG,"Could not compile shader:"+shaderType);
                Log.e(TAG,"GLES20 Error:"+ GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader=0;
            }
        }
        return shader;
    }

    public void createprogram(){
        System.out.print(vertCode);
        int vertshader =loadShader(GLES20.GL_VERTEX_SHADER,vertCode);
        int fragshader =loadShader(GLES20.GL_FRAGMENT_SHADER,fragCode);
        mprogram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mprogram, vertshader);
        GLES20.glAttachShader(mprogram, fragshader);
        GLES20.glLinkProgram(mprogram);

        int[] linkStatus=new int[1];
        GLES20.glGetProgramiv(mprogram, GLES20.GL_LINK_STATUS,linkStatus,0);
        if(linkStatus[0]!= GLES20.GL_TRUE){
            glError(1,"Could not link program:"+ GLES20.glGetProgramInfoLog(mprogram));
            GLES20.glDeleteProgram(mprogram);
            mprogram=0;
        }
    }

    public static void glError(int code,Object index){
        if(DEBUG&&code!=0){
            Log.e(TAG,"glError:"+code+"---"+index);
        }
    }
}
