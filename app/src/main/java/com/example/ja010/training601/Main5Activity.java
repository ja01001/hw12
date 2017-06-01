package com.example.ja010.training601;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
    }
    public void ok(View v){
        switch (v.getId()){
            case R.id.bu:
                Intent i1 = new Intent(Main5Activity.this,Main2Activity.class);
                startActivity(i1);
                break;
            case R.id.bu2:
                Intent i2 = new Intent(Main5Activity.this,Main3Activity.class);
                startActivity(i2);
                break;
            case R.id.bu3:
                Intent i3 = new Intent(Main5Activity.this,Main4Activity.class);
                startActivity(i3);
                break;
        }

    }
}
