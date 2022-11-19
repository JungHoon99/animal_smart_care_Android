package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * ToDo :   이미지 받아서 업데이트
 */
public class MainActivity extends AppCompatActivity {
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //가로 모드 화면 고정
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button timeButton = (Button) findViewById(R.id.feedTimrBtn);
        timeButton.setOnClickListener(new putNextPageLinstener(this, FeedTimeActivity.class,
                new String[]{"deviceId"},
                new String[]{getIntent().getStringExtra("deviceId")}
        ));
        createWebSocketClient("ws://3.39.204.82:51698/");
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
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("{'kind': 1, 'roomNumber' : '1234'}");
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.e("get Message", message);
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
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }
}