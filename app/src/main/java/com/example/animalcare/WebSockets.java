package com.example.animalcare;

import android.util.Log;

import java.net.URI;

import tech.gusavila92.websocketclient.WebSocketClient;

public class WebSockets extends WebSocketClient {

    String img;
    /**
     * Initialize all the variables
     *
     * @param uri URI of the WebSocket server
     */
    public WebSockets(URI uri,String a) {
        super(uri);
        img=a;
    }

    @Override
    public void onOpen() {
        System.out.println("onOpen");
    }

    @Override
    public void onTextReceived(String message) {
        Log.i("WebSocket", "Message received");
        Log.e("Web ", message);
        img = message;
    }

    @Override
    public void onBinaryReceived(byte[] data) {
        System.out.println("onBinaryReceived");
    }

    @Override
    public void onPingReceived(byte[] data) {
        System.out.println("onPingReceived");
    }

    @Override
    public void onPongReceived(byte[] data) {
        System.out.println("onPongReceived");
    }

    @Override
    public void onException(Exception e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onCloseReceived() {
        System.out.println("onCloseReceived");
    }
}
