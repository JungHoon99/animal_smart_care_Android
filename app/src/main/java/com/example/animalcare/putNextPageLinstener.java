package com.example.animalcare;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class putNextPageLinstener implements View.OnClickListener{
    private AppCompatActivity activ;
    private Class nextPage;
    private String key[];
    private String value[];

    public putNextPageLinstener(AppCompatActivity activ, Class nextPage, String key[], String value[]){
        this.activ = activ;
        this.nextPage = nextPage;
        this.key = key;
        this.value = value;
    }

    @Override
    public void onClick(android.view.View view){
        if(this.key.length!=this.value.length){
            System.out.println("Your not give Same Length Data");
            return;
        }

        Intent intent = new Intent(this.activ.getApplicationContext() , this.nextPage);
        for(int i=0; i<this.key.length;i++){
            intent.putExtra(this.key[i], this.value[i]);
        }
        this.activ.startActivity(intent);
    }
}