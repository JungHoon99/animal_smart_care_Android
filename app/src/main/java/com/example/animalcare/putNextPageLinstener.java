package com.example.animalcare;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @apiNote This calss is Next Page Listener and new create ex) new putNextPageLinstener(this, Activity.class, String[], String[])
 */
public class putNextPageLinstener implements View.OnClickListener{
    private final AppCompatActivity activ;
    private final Class nextPage;
    private final String[] key;
    private final String[] value;

    /**
     * @param activ : AppCompatActivity
     * @param nextPage : Class
     * @param key : String[]
     * @param value :   String[]
     */
    public putNextPageLinstener(AppCompatActivity activ, Class nextPage, String[] key, String[] value){
        this.activ = activ;
        this.nextPage = nextPage;
        this.key = key;
        this.value = value;
    }

    @Override
    public void onClick(View view){
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