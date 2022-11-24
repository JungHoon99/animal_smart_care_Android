package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * ToDo :   FeedButton 기능 활성화
 *          WaterButton 기능 활성화
 */
public class MainActivity extends AppCompatActivity {
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //가로 모드 화면 고정
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button timeButton = (Button) findViewById(R.id.feedTimrBtn);
        Button feedButton = (Button) findViewById(R.id.feedBtn);
        Button waterButton = (Button) findViewById(R.id.waterBtn);

        timeButton.setOnClickListener(new putNextPageLinstener(this, FeedTimeActivity.class,
                new String[]{"deviceId"},
                new String[]{getIntent().getStringExtra("deviceId")}
        ));
        createWebSocketClient("ws://13.209.111.12:54270/");

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webSocketClient.send("{'command' : 'feed'}");
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webSocketClient.send("{'command' : 'water'}");
            }
        });
    }

    private void createWebSocketClient(String urlink) {
        URI uri;
        try {
            // Connect to local host
            uri = new URI(urlink);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("{'kind': 1, 'roomNumber' : '1234'}");
            }

            @Override
            public void onTextReceived(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            byte[] ImageByte = Base64.decode(message,Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(ImageByte,0,ImageByte.length);
                            imageView.setImageBitmap(bitmap);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
            }
        };

        webSocketClient.setConnectTimeout(1000);
        webSocketClient.setReadTimeout(600000);
        webSocketClient.enableAutomaticReconnection(50000);
        webSocketClient.connect();
    }
}