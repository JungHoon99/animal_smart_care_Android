package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

/**
 *  ToDo :  식수 및 사료 제공 시간 설정 db 가져오기
 *          삭제 기능 만들기
 */
public class FeedTimeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_time);
        Button updateBtn = (Button) findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new updateOnClickListenser());

    }

    class updateOnClickListenser implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Spinner hourSpinner =(Spinner) findViewById(R.id.hourSpinner);
            Spinner minSpinner =(Spinner) findViewById(R.id.minSpinner);
            Switch waterSwitch = (Switch) findViewById(R.id.waterSwitch);
            Switch feedSwitch = (Switch) findViewById(R.id.feedSwitch);

            hourSpinner.getSelectedItem().toString();   // Spinner의 선택한 아이템 값 string으로 가져오기
            minSpinner.getSelectedItem().toString();

            waterSwitch.isChecked();    //  switch의 현재 상태 가져오기 눌러있으면 true값 반환
            feedSwitch.isChecked();     //  아니면 false 반환
            MakeLine(hourSpinner.getSelectedItem().toString(), minSpinner.getSelectedItem().toString(),
                    waterSwitch.isChecked() ? 1:0, feedSwitch.isChecked() ? 1:0);
        }
    }

    private void MakeLine(String hour, String min, int water, int food){
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

        newLinear.setMinimumHeight(dpToPx(this,45));

        linear.addView(newLinear,0);

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