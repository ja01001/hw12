package com.example.ja010.training601;

import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    EditText e1;
    TextView e2;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e1 = (EditText)findViewById(R.id.e1);
        e2 = (TextView) findViewById(R.id.e2);
        url =e1.getText().toString();
    }
    public void clcl(View v){
        threads.start();
    }
    Handler handler = new Handler();
    Thread threads = new Thread(){
        @Override
        public void run() {
            try {
                URL urls = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection)urls.openConnection();
                if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    final String data = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            e2.setText(data);
                        }

                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };
    String readData(InputStream is){
        String data = "";
        Scanner s = new Scanner(is);
        while(s.hasNext()){
            data += s.nextLine()+"\n";
        }
        s.close();
        return data;
    }
}
