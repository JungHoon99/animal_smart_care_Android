package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class FeedTimeActivity extends AppCompatActivity {
    private int LINECOUNTER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_time);
        Button updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new updateOnClickListenser());

        try {
            LoadDataLine();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class updateOnClickListenser implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String tid;
            Spinner hourSpinner =(Spinner) findViewById(R.id.hourSpinner);
            Spinner minSpinner =(Spinner) findViewById(R.id.minSpinner);
            Switch waterSwitch = (Switch) findViewById(R.id.waterSwitch);
            Switch feedSwitch = (Switch) findViewById(R.id.feedSwitch);

            String hour = hourSpinner.getSelectedItem().toString();   // Spinner의 선택한 아이템 값 string으로 가져오기
            String min = minSpinner.getSelectedItem().toString();

            waterSwitch.isChecked();    //  switch의 현재 상태 가져오기 눌러있으면 true값 반환
            feedSwitch.isChecked();     //  아니면 false 반환

            tid = "0"+Character.toString((char)('A'+Integer.parseInt(hour)))+
                    Character.toString((char)('A'+(Integer.parseInt(min)/5)));

            insertFeedTime(tid, waterSwitch.isChecked() ? 1:0, feedSwitch.isChecked() ? 1:0);

            MakeLine(tid,hourSpinner.getSelectedItem().toString(), minSpinner.getSelectedItem().toString(),
                    waterSwitch.isChecked() ? 1:0, feedSwitch.isChecked() ? 1:0);
        }
    }



    private void LoadDataLine() throws JSONException {
        URI uri = null;
        Intent intents = getIntent();
        String getId = String.valueOf(intents.getStringExtra("deviceId"));
        try {
            uri = new URI("wss://animal-service.run.goorm.io/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        WebSockets wb = new WebSockets(uri,"None");
        wb.connect();
        wb.send("{\"code\":\"phone\"}");

        while(wb.data.equals("None")){sleep(10);}
        wb.data = "None";
        wb.send(String.format(
                "{\"kind\":\"select\", \"message\" : \"select f.time_id as tid ,f.water as water, f.feed as feed, t.hour as hour, t.min as min " +
                                                        "from feed_time as f inner join time_slot as t on f.time_id = t.time_id " +
                                                        "where device_id = '%s';\"}", getId));
        while(wb.data.equals("None")){sleep(10);}

        JSONObject json = null;
        JSONArray timeArray;

        try {
            json = new JSONObject(wb.data);
            timeArray = json.getJSONArray("message");
            for(int i=0; i<timeArray.length(); i++){
                json = timeArray.getJSONObject(i);
                MakeLine(json.getString("tid"),json.getString("hour"),json.getString("min"), json.getInt("water"), json.getInt("feed"));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        wb.close();
    }

    private void MakeLine(String tid, String hour, String min, int water, int food){
        LinearLayout linear = (LinearLayout) findViewById(R.id.timesView);

        LinearLayout newLinear = new LinearLayout(this);

        TextView hourText = new TextView(this);
        TextView minText = new TextView(this);
        TextView waterText = new TextView(this);
        TextView foodText = new TextView(this);
        Button DelBtn = new Button(this);

        hourText.setText(hour);
        minText.setText(min);
        waterText.setText( water==1? "On":"Off");
        foodText.setText(food==1? "On":"Off");
        DelBtn.setText("삭제");

        hourText.setTextSize(20.0F);
        minText.setTextSize(20.0F);
        waterText.setTextSize(20.0F);
        foodText.setTextSize(20.0F);

        hourText.setGravity(Gravity.CENTER);
        minText.setGravity(Gravity.CENTER);
        waterText.setGravity(Gravity.CENTER);
        foodText.setGravity(Gravity.CENTER);

        newLinear.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams Params = new FrameLayout.LayoutParams(
                dpToPx(this,80),FrameLayout.LayoutParams.WRAP_CONTENT);

        newLinear.addView(waterText,Params);
        newLinear.addView(foodText,Params);
        newLinear.addView(hourText,Params);
        newLinear.addView(minText,Params);
        newLinear.addView(DelBtn);

        DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URI uri = null;
                Intent intents = getIntent();
                String getId = String.valueOf(intents.getStringExtra("deviceId"));

                try {
                    uri = new URI("wss://animal-service.run.goorm.io/");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                WebSockets wb = new WebSockets(uri,"None");
                wb.connect();
                wb.send("{\"code\":\"phone\"}");

                while(wb.data.equals("None")){sleep(10);}
                wb.data = "None";
                wb.send(String.format(
                        "{\"kind\":\"insert\", \"message\" : \"delete from feed_time where device_id = '%s' and time_id = '%s';\"}",getId, tid));
                while(wb.data.equals("None")){sleep(10);}
                linear.removeView(newLinear);
                LINECOUNTER--;
            }
        });

        newLinear.setMinimumHeight(dpToPx(this,45));

        linear.addView(newLinear,LINECOUNTER);

        LINECOUNTER++;
    }

    /**
     * @apiNote Database insert data function
     * @param time_id : String
     * @param water : int
     * @param feed  : int
     */
    private void insertFeedTime(String time_id, int water, int feed){
        URI uri = null;
        Intent intents = getIntent();
        String getId = String.valueOf(intents.getStringExtra("deviceId"));

        try {
            uri = new URI("wss://animal-service.run.goorm.io/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        WebSockets wb = new WebSockets(uri,"None");
        wb.connect();
        wb.send("{\"code\":\"phone\"}");

        while(wb.data.equals("None")){sleep(10);}
        wb.data = "None";
        wb.send(String.format(
                "{\"kind\":\"insert\", \"message\" : \"insert into feed_time(device_id, time_id, water, feed) " +
                        "value('%s', '%s', %d, %d);\"}", getId, time_id, water, feed));
        while(wb.data.equals("None")){sleep(10);}
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