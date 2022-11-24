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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginActivity extends AppCompatActivity {
    WebSockets wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button LoginButton = (Button) findViewById(R.id.Login);
        LoginButton.setOnClickListener(new aSignInfoButtonListener());

        Button SignupButton =(Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new gotoPageListener(this, SgininActivity.class));
    }

    class aSignInfoButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            URI uri = null;
            try {
                uri = new URI("ws://13.209.111.12:50317");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            WebSockets wb = new WebSockets(uri,"None");
            wb.connect();
            wb.send("{\"code\":\"phone\"}");

            EditText idEditText = (EditText) findViewById(R.id.editTextTextPersonName);
            EditText pwEditText = (EditText) findViewById(R.id.editTextTextPassword2);

            while(wb.data.equals("None")){sleep(10);}
            wb.data = "None";
            wb.send("{\"kind\":\"select\", \"message\" : \"select count(*) from user where user_id = '"+idEditText.getText()+"' and pw = '"+pwEditText.getText()+"'\"}");
            while(wb.data.equals("None")){sleep(10);}

            JSONObject json = null;
            try {
                json = new JSONObject(wb.data);
                if(Integer.parseInt(json.getJSONArray("message").getJSONObject(0).getString("count(*)")) == 1) {
                    Intent intent = new Intent(getApplicationContext(), selectDivice.class);
                    String getTexts = String.valueOf(idEditText.getText());
                    intent.putExtra("id", getTexts);
                    startActivity(intent);
                    wb.close();
                }
                else{
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(    LoginActivity.this);
                    // alert의 title과 Messege 세팅
                    myAlertBuilder.setTitle("Try Again Fixed it");
                    myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){}
                        }
                    );
                    myAlertBuilder.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();

        }
            wb.close();
        }
    }
}