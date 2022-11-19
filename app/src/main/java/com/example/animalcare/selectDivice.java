package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/*
 * ToDo :   WebSocket을 통해 User에 Device data 기져오기
 *          가져온데이터를 AddDeviceLine함수를 사용해서 데이터 뿌리기
 *          connect 버튼을 누르면 putExtra를 활용해서 MainActivity에 device 정보 전달
 */

/**
 * @apiNote This class can select, delete, create and view devices List.
 */
public class selectDivice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_divice);

        MakeDeviceLine();
        deviceAddButton();
    }

    private void MakeDeviceLine(){
        URI uri = null;
        Intent intents = getIntent();
        String getId = String.valueOf(intents.getStringExtra("id"));
        try {
            uri = new URI("ws://3.39.204.82:59552");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        WebSockets wb = new WebSockets(uri,"None");
        wb.connect();
        wb.send("{\"code\":\"phone\"}");

        while(wb.data.equals("None")){sleep(10);}
        wb.data = "None";
        wb.send("{\"kind\":\"select\", \"message\" : \"select * from register where user_id = '"+getId+"'\"}");
        while(wb.data.equals("None")){sleep(10);}
        Log.e("Get DATA : ", wb.data);

        JSONObject json = null;
        JSONArray deviceArray;

        try {
            json = new JSONObject(wb.data);
            deviceArray = json.getJSONArray("message");
            for(int i=0; i<deviceArray.length(); i++){
                json = deviceArray.getJSONObject(0);
                AddDeviceLine(json.getString("device_id"),json.getString("pet_name"), json.getInt("pet_age"), "on");
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        wb.close();
    }

     private void deviceAddButton(){
         Intent intents = getIntent();
         String getId = String.valueOf(intents.getStringExtra("id"));
         LinearLayout linear = (LinearLayout) findViewById(R.id.mainLinear);
         Button deviceAddButton = new Button(this);
         deviceAddButton.setText("+");
         deviceAddButton.setTextSize(30.0F);
         deviceAddButton.setOnClickListener(new putNextPageLinstener(this, createDevice.class, new String[]{"id"},new String[]{getId}));
         FrameLayout.LayoutParams deviceAddButtonParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dpToPx(this,90));
         linear.addView(deviceAddButton,deviceAddButtonParams);
     }

    /**
     * @apiNote this function is Create FrameLayout and add to LinearLayout
     * @param deviceId String
     * @param PetName String
     * @param PetAge Integer
     * @param State String
     */
    private void AddDeviceLine(String deviceId, String PetName, int PetAge, String State){
        LinearLayout linear = (LinearLayout) findViewById(R.id.mainLinear);

        FrameLayout f1 = new FrameLayout(this);     //새로운 FrameLayout 생성
        f1.setBackgroundResource(R.drawable.setboxborder1dp);   //FrameLayout에 네모 생성
        TextView deviceTextView = new TextView(this);
        TextView petNameTextView = new TextView(this);
        TextView petAgeTextView = new TextView(this);
        TextView stateTextView = new TextView(this);
        Button connectButton = new Button(this);

        connectButton.setOnClickListener(new putNextPageLinstener(
                this,
                MainActivity.class,
                new String[]{"deviceId","petName","petAge"},
                new String[]{deviceId,PetName,Integer.toString(PetAge)}
        ));

        deviceTextView.setText(deviceId);
        petNameTextView.setText(PetName);
        petAgeTextView.setText(Integer.toString(PetAge));
        stateTextView.setText(State);
        connectButton.setText("connect");

        deviceTextView.setTextSize(20.0F);
        petNameTextView.setTextSize(20.0F);
        petAgeTextView.setTextSize(20.0F);
        stateTextView.setTextSize(20.0F);

        deviceTextView.setGravity(Gravity.CENTER);
        petNameTextView.setGravity(Gravity.CENTER);
        petAgeTextView.setGravity(Gravity.CENTER);
        connectButton.setGravity(Gravity.CENTER);


        FrameLayout.LayoutParams deviceTextViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams petNameTextViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams petAgeTextViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams connectButtonParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);

        deviceTextViewParams.gravity = Gravity.LEFT|Gravity.TOP;
        petNameTextViewParams.gravity = Gravity.LEFT|Gravity.CENTER;
        petAgeTextViewParams.gravity = Gravity.LEFT|Gravity.BOTTOM;
        connectButtonParams.gravity  = Gravity.BOTTOM|Gravity.RIGHT;

        f1.addView(deviceTextView,deviceTextViewParams);
        f1.addView(petNameTextView,petNameTextViewParams);
        f1.addView(petAgeTextView,petAgeTextViewParams);
        f1.addView(connectButton,connectButtonParams);

        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dpToPx(this,90));
        linear.addView(f1,fp);      //LinearLayout에 FrameLayout 추가
    }

    /**
     * @apiNote dp to Pixcel convert function
     * @param context : Context
     * @param dp : float
     * @returns px : int
     */
    public int dpToPx(Context context, float dp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}