package com.example.animalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  ToDo :  식수 및 사료 제공 시간 설정 db 가져오기
 *          삭제 기능 만들기
 */
public class FeedTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MakeLine();
    }

    private void MakeLine(){
        LinearLayout Timer = findViewById(R.id.timeView);
        LinearLayout Line = new LinearLayout(this);

        Line.setOrientation(LinearLayout.HORIZONTAL);

        TextInputLayout TestDrop = new TextInputLayout(this);
        TestDrop.setHint("Hour");
        TestDrop.setMinWidth(200);
        TestDrop.setMinimumHeight(50);
        AutoCompleteTextView hourAuto = new AutoCompleteTextView(this);
        TestDrop.addView(hourAuto);

        String[] items = {"item1", "item2", "item3", "item4", "item5"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(FeedTimeActivity.this,
                R.layout.activity_feed_time, items);
        hourAuto.setAdapter(itemAdapter);

        Line.addView(TestDrop);
        Timer.addView(Line);
    }
}