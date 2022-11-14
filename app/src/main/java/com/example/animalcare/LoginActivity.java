package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.net.URI;

/**
 * TODO : Websocket을 활용해서 Login 기능 구현
 */
public class LoginActivity extends AppCompatActivity {
    WebSockets wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button LoginButton = (Button) findViewById(R.id.Login);
        LoginButton.setOnClickListener(new gotoPageListener(this, selectDivice.class));

        Button SignupButton =(Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new gotoPageListener(this, SgininActivity.class));

//        try {
//            wb = connectSocket("ws://13.124.160.248:50394/");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private WebSockets connectSocket(String link) throws Exception{
        URI uri =new URI(link);
        WebSockets wb = new WebSockets(uri,"None");

        wb.connect();
        wb.send("{\"code\":\"phone\"}");

        while(wb.data.equals("None")){sleep(100);}

        return wb;
    }
}