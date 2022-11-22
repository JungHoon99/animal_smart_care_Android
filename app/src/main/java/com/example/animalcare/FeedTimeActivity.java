package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        createTimeAddLine();
    }

    private void createTimeAddLine(){
        String[] min = new String[]{"0", "5", "10", "15", "20", "25", "30", "35", "40", "50", "55"};
        TableLayout feedTableLayout = (TableLayout) findViewById(R.id.FeedTable);

        TableRow tableRow = new TableRow(this);     // tablerow 생성
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ToggleButton waterToggle =new ToggleButton(this);
        waterToggle.setTextOn("water on");
        waterToggle.setTextOff("water off");
        waterToggle.setText(0);
        tableRow.addView(waterToggle);

        ToggleButton feedToggle = new ToggleButton(this);
        feedToggle.setTextOn("feed on");
        feedToggle.setTextOff("feed off");
        feedToggle.setText(0);
        tableRow.addView(feedToggle);

        Spinner minSpinner =new Spinner(this);

        /**
         *  Todo spinner 공부하기 꼭
         */
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                R.layout.activity_feed_time,
//                min);

        minSpinner.setAdapter(adapter);
        minSpinner.setSelection(0);
        tableRow.addView(minSpinner);

//        NumberPicker hourPicker = new NumberPicker(this);
//        hourPicker.setMaxValue(23);
//        hourPicker.setMinValue(0);
//        hourPicker.setValue(12);
//        tableRow.addView(hourPicker);
//
//        NumberPicker minPicker = new NumberPicker(this);
//        minPicker.setDisplayedValues(min);
//        tableRow.addView(hourPicker);

        feedTableLayout.addView(tableRow);
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