package com.example.animalcare;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * nextPage에 가기위한 버튼 리스너 입니다.
 * goto MainPage ButtonListener
 * 2022.11.03
 */
public class gotoPageListener implements View.OnClickListener{
    private AppCompatActivity activ;
    private Class nextPage;

    public gotoPageListener(AppCompatActivity activ, Class nextPage){
        this.activ = activ;
        this.nextPage = nextPage;
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(this.activ.getApplicationContext() , this.nextPage);
        this.activ.startActivity(intent);
    }
}