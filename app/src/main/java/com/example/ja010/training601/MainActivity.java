package com.example.ja010.training601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static String Server_IP = "172.17.65.239";
    static int Server_port = 200;
    String msg = "";
    EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.etmsg);

    } // sorket client
    public void onClick(View v){
        msg = e1.getText().toString();
        mythead.start();
    }

    Handler myHandler = new Handler();
    Thread mythead = new Thread(){
        @Override
        public void run() {
            try {
                Socket aSocket = new Socket(Server_IP,Server_port);
                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(msg);
                outstream.flush();
                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                final Object obj = instream.readObject();
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),(String)obj,Toast.LENGTH_SHORT).show();
                    }
                });

                aSocket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

}
