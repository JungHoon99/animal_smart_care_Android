package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        addLine("On","On","8:30");
        addLine("On","On","12:30");
        addLine("On","On","18:30");
    }

    protected void addLine(String feed, String water, String time){
        TableLayout feedTableLayout = (TableLayout) findViewById(R.id.FeedTable);

        String Item[] = new String[]{feed,water,time};

        TableRow tableRow = new TableRow(this);     // tablerow 생성
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        for(int i = 0 ; i < Item.length ; i++) {
            TextView textView = new TextView(this);
            textView.setText(Item[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20.0F);
            tableRow.addView(textView);		// tableRow에 view 추가
        }

        Button deleteTableButton = new Button(this);

        deleteTableButton.setText("Del");
        deleteTableButton.setTextSize(20);
        deleteTableButton.setGravity(Gravity.CENTER);
        tableRow.addView(deleteTableButton);
        feedTableLayout.addView(tableRow);		// tableLayout에 tableRow 추가

    }
}