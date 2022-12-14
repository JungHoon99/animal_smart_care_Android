package com.example.animalcare;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 */
public class SgininActivity extends AppCompatActivity {
    int isPwCheck = 0;
    int isIdCheck = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sginin);
        EditText idEdit = (EditText) findViewById(R.id.editTextTextID);
        EditText pwCheckEdit = (EditText) findViewById(R.id.editTextTextPassword3);
        Button IdCheck = (Button) findViewById(R.id.idCheckButton);
        Button button  = (Button) findViewById(R.id.idCheckButton3);
        button.setOnClickListener(new aSignInfoButtonListener());
        pwCheckEdit.setOnKeyListener(new EditMessageOnKeyListener());
        IdCheck.setOnClickListener(new idCheckButtonListener());
    }

    class aSignInfoButtonListener implements View.OnClickListener{

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

            EditText idEdit = findViewById(R.id.editTextTextID);
            EditText pwEdit = (EditText) findViewById(R.id.editTextTextPassword3);
            EditText nameEdit = (EditText) findViewById(R.id.editTextTextPersonName3);

            if(isPwCheck==0 || isIdCheck==1){
                AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(SgininActivity.this);
                // alert??? title??? Messege ??????
                myAlertBuilder.setTitle("Try Again \n Fixed it");
                myAlertBuilder.show();
                return;
            }

            while(wb.data.equals("None")){sleep(10);}
            wb.data = "None";
            wb.send("{\"kind\":\"insert\", \"message\" : \"insert into user (user_id, pw, name) values('"+idEdit.getText()+"', '"+pwEdit.getText()+"', '"+ nameEdit.getText() +"')\"}");
            while(wb.data.equals("None")){sleep(10);}
            JSONObject json = null;
            try {
                json = new JSONObject(wb.data);
                Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(SgininActivity.this);
                // alert??? title??? Messege ??????
                myAlertBuilder.setTitle("Try Again \n Fixed it");
                myAlertBuilder.show();
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

            EditText idEdit = findViewById(R.id.editTextTextID);

            while(wb.data.equals("None")){sleep(10);}
            wb.data = "None";
            wb.send("{\"kind\":\"select\", \"message\" : \"select count(*) from user where user_id = '"+idEdit.getText()+"'\"}");
            while(wb.data.equals("None")){sleep(10);}
            JSONObject json = null;
            try {
                json = new JSONObject(wb.data);
                isIdCheck = Integer.parseInt(json.getJSONArray("message").getJSONObject(0).getString("count(*)"));
            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(SgininActivity.this);
                // alert??? title??? Messege ??????
                myAlertBuilder.setTitle("Duplicate");
                myAlertBuilder.show();
            }
            wb.close();
        }
    }

    class EditMessageOnKeyListener implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText pwEdit = (EditText) findViewById(R.id.editTextTextPassword);
            EditText pwCheckEdit = (EditText) findViewById(R.id.editTextTextPassword3);
            TextView pwCheckText = (TextView) findViewById(R.id.pwCheckTextView);
            if(event.getAction() == KeyEvent.ACTION_UP){
                String pw = String.valueOf(pwEdit.getText());
                String pwCheck = String.valueOf(pwCheckEdit.getText());
                if(pw.equals(pwCheck)){
                    pwCheckText.setText("??????????????? ???????????????.");
                    pwCheckText.setTextColor(Color.GREEN);
                    isPwCheck = 1;
                }
                else{
                    pwCheckText.setText("??????????????? ????????? ?????????.");
                    pwCheckText.setTextColor(Color.RED);
                    isPwCheck = 0;
                }
            }
            if(keyCode ==KeyEvent.KEYCODE_DEL){
                String pw = String.valueOf(pwEdit.getText());
                String pwCheck = String.valueOf(pwCheckEdit.getText());
                if(pwCheck.length() == 0 ){
                    return false;
                }
                if(pw.equals(pwCheck.substring(0,pwCheck.length()-1))){
                    pwCheckText.setText("??????????????? ???????????????.");
                    pwCheckText.setTextColor(Color.GREEN);
                    isPwCheck = 1;
                }
                else{
                    pwCheckText.setText("??????????????? ????????? ?????????.");
                    pwCheckText.setTextColor(Color.RED);
                    isPwCheck = 0;
                }
            }
            return false;
        }
    }
}