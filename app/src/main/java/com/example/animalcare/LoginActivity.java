package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * TODO : Websocket을 활용해서 Login 기능 구현
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button LoginButton = (Button) findViewById(R.id.Login);
        LoginButton.setOnClickListener(new gotoPageListener(this, selectDivice.class));

        Button SignupButton =(Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new gotoPageListener(this, SgininActivity.class));

        try {
            connectSocket("ws://13.124.160.248:50394/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectSocket(String link) throws Exception{
        URI uri =new URI(link);
        String GetData = null;
        WebSockets wb = new WebSockets(uri,GetData);
        String s = new String();

        wb.connect();
        wb.send("hi");
        sleep(100);
        System.out.println("fdsaf"+wb.img);
        wb.send("his");
        sleep(100);
        System.out.println("fdsaf"+wb.img);
        wb.send("hiss");
        sleep(100);
        System.out.println("fdsaf"+wb.img);
    }
}