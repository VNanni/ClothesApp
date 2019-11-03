package com.example.vnannni.clothesmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    @Override
    public void onCreate(Bundle saveinstanceState){
        super.onCreate( saveinstanceState );
        setContentView( R.layout.activity_main);
        Intent intent = new Intent( Welcome.this,Appstart.class );
        startActivity( intent );
    }
}
