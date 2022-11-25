package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class createDevice extends AppCompatActivity {
    int isDeviceIdCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        Button deviceIdButton = (Button) findViewById(R.id.idButton);
        Button submitButton = (Button) findViewById(R.id.button2);

        deviceIdButton.setOnClickListener(new idCheckButtonListener());
        submitButton.setOnClickListener(new submitButtonListener());
    }

    class submitButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            URI uri = null;

            try {
                uri = new URI("wss://animal-service.run.goorm.io/");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            WebSockets wb = new WebSockets(uri,"None");
            wb.connect();
            wb.send("{\"code\":\"phone\"}");

            EditText deviceId = findViewById(R.id.deviceIdTextBox);
            EditText petName = (EditText) findViewById(R.id.petNameEditText);
            EditText petAge = (EditText) findViewById(R.id.petAgeEditText);
            Intent getIntent = getIntent();

            if(isDeviceIdCheck == 0){
                wb.close();
                AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(createDevice.this);
                // alert의 title과 Messege 세팅
                myAlertBuilder.setTitle("Try Again \n Fixed it");
                myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){}
                        }
                );
                myAlertBuilder.show();
                return;
            }
            while(wb.data.equals("None")){sleep(10);}
            wb.data = "None";
            wb.send("{\"kind\":\"insert\", \"message\" : \"insert into register (device_id, user_id, pet_name, pet_age) values('"+
                    deviceId.getText()+"', '"+getIntent.getStringExtra("id")+"', '"+ petName.getText() +"', "+petAge.getText()+")\"}");
            while(wb.data.equals("None")){sleep(10);}
            JSONObject json;
            try {
                json = new JSONObject(wb.data);
                if(json.getInt("message") != 1){
                    AlertDialog.Builder myAlertBuilder =
                            new AlertDialog.Builder(createDevice.this);
                    // alert의 title과 Messege 세팅
                    myAlertBuilder.setTitle("Try Again \n Fixed it");
                    myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int which){}
                            }
                    );
                    myAlertBuilder.show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext() , selectDivice.class);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            wb.close();
        }
    }

    class idCheckButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            URI uri = null;
            try {
                uri = new URI("wss://animal-service.run.goorm.io/");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            WebSockets wb = new WebSockets(uri,"None");
            wb.connect();
            wb.send("{\"code\":\"phone\"}");

            EditText idEdit = findViewById(R.id.deviceIdTextBox);

            while(wb.data.equals("None")){sleep(10);}
            wb.data = "None";
            wb.send("{\"kind\":\"select\", \"message\" : \"select count(*) from device where device_id = '"+idEdit.getText()+"'\"}");
            while(wb.data.equals("None")){sleep(10);}
            JSONObject json;
            try {
                json = new JSONObject(wb.data);
                isDeviceIdCheck = Integer.parseInt(json.getJSONArray("message").getJSONObject(0).getString("count(*)"));
            } catch (JSONException e) {
                Log.e("Error","this Page Error");
            }
            wb.close();
        }
    }

}