package com.example.animalcare;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @apiNote This class can select, delete, create and view devices.
 */
public class selectDivice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_divice);

        LinearLayout linear = (LinearLayout) findViewById(R.id.mainLinear); //생성한 LinearLayout 가져오기

        CreateDeviceLine("1234","야옹이", 5, "on");
        CreateDeviceLine("1254","쫑이", 10, "off");
        CreateDeviceLine("1234","보리", 6, "on");

    }

    public void CreateDeviceLine(String deviceId, String PetName, int PetAge, String State){
        LinearLayout linear = (LinearLayout) findViewById(R.id.mainLinear);

        FrameLayout f1 = new FrameLayout(this);     //새로운 FrameLayout 생성

        TextView deviceTextView = new TextView(this);
        TextView petNameTextView = new TextView(this);
        TextView petAgeTextView = new TextView(this);
        Button connectButton = new Button(this);

        deviceTextView.setText(deviceId);
        petNameTextView.setText(PetName);
        petAgeTextView.setText(Integer.toString(PetAge));
        connectButton.setText("connect");


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
     * @param context
     * @param dp
     * @returns px
     */
    public int dpToPx(Context context, float dp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}