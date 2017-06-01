package com.example.ja010.training601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main4Activity extends AppCompatActivity {
    TextView t1;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        t1 = (TextView)findViewById(R.id.title);
        e1 = (EditText)findViewById(R.id.e1);
        e2 = (EditText)findViewById(R.id.e2);

    }
    public void cl(View v){
        thr.start();
    }
    Handler h = new Handler();
    Thread thr = new Thread(){
        @Override
        public void run() {
            try {
                String userid =e1.getText().toString();
                String password =e2.getText().toString();
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php");// url 접속 을 위한 url
                // url 의 속성 설정
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // connection 으로 연결
                httpURLConnection.setRequestMethod("POST"); // byte 단위로? post 형태로 가져옴
                httpURLConnection.setDoOutput(true); // output 을 기다림

                String postData = "userid=" + URLEncoder.encode(userid)
                        + "&password=" + URLEncoder.encode(password);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    inputStream = httpURLConnection.getInputStream();
                else
                    inputStream = httpURLConnection.getErrorStream();
                final String result = loginResult(inputStream);
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("FAIL"))
                            t1.setText("로그인이 실패했습니다 .");
                        else {
                            t1.setText(result + "님 로그인 성공");
                        }
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    String loginResult(InputStream is){
        String data = "";
        Scanner s = new Scanner(is);
        while(s.hasNext()){
            data += s.nextLine();
        }
        s.close();
        return data;
    }
}
