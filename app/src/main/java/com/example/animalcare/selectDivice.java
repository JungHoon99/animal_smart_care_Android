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

        FrameLayout f1 = new FrameLayout(this);     //새로운 FrameLayout 생성
        f1.setBackgroundColor(Color.parseColor("#77b1e5"));
        Button btn = new Button(this);      //새로운 버튼 생성
        btn.setText("Hello");   //버튼에 들어갈 내용을 Hello로 설정

        /**
         * LinearLayout에 들어갈 FrameLayout 파라미터 생성 넓이는 부모를 참조하고 높이는 90dp
         */
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dpToPx(this,90));
        /**
         * FrameLayout에 들어갈  Button 파라미터 생성 넓이와 높이 모두 Text에 맞게 설정
         */
        FrameLayout.LayoutParams bp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        bp.gravity = Gravity.BOTTOM|Gravity.RIGHT;        //버튼 위치를 FrameLayout 오른쪽 아래에 설정
        f1.addView(btn, bp);        //FrameLayout에 버튼 추가
        linear.addView(f1,fp);      //LinearLayout에 FrameLayout 추가
    }

    public void CreateDeviceLine(String deviceId, String PetName, int PetAge, String State){
        LinearLayout linear = (LinearLayout) findViewById(R.id.mainLinear);
        FrameLayout f1 = new FrameLayout(this);     //새로운 FrameLayout 생성
        TextView deviceTextView = new TextView(this);
        TextView petNameTextView = new TextView(this);
        TextView petAgeTextView = new TextView(this);

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