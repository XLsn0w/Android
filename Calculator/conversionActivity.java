package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class conversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion_layout);

        //计算、换算的转换
/*        Button conversionToCount=(Button) findViewById(R.id.bt_count);
        conversionToCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(conversionActivity.this,count_mainActivity.class);
                startActivity(intent);
            }
        });*/
        Button toLen=(Button) findViewById(R.id.bt_length);
        toLen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(conversionActivity.this,lengthActivity.class);
                startActivity(intent);
            }
        });
        Button toVol=(Button) findViewById(R.id.bt_volume);
        toVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(conversionActivity.this,volumeActivity.class);
                startActivity(intent);
            }
        });
    }
    //菜单(help,exit)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_help:
                Toast.makeText(this,"This is help",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_exit:
                break;
            default:
                break;
        }
        return true;
    }
}
