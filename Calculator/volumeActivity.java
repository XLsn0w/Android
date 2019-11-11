package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class volumeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_layout);


        Button bt_0=(Button)findViewById(R.id.bt_0);
        bt_0.setOnClickListener(this);
        Button bt_1=(Button)findViewById(R.id.bt_1);
        bt_1.setOnClickListener(this);
        Button bt_2=(Button)findViewById(R.id.bt_2);
        bt_2.setOnClickListener(this);
        Button bt_3=(Button)findViewById(R.id.bt_3);
        bt_3.setOnClickListener(this);
        Button bt_4=(Button)findViewById(R.id.bt_4);
        bt_4.setOnClickListener(this);
        Button bt_5=(Button)findViewById(R.id.bt_5);
        bt_5.setOnClickListener(this);
        Button bt_6=(Button)findViewById(R.id.bt_6);
        bt_6.setOnClickListener(this);
        Button bt_7=(Button)findViewById(R.id.bt_7);
        bt_7.setOnClickListener(this);
        Button bt_8=(Button)findViewById(R.id.bt_8);
        bt_8.setOnClickListener(this);
        Button bt_9=(Button)findViewById(R.id.bt_9);
        bt_9.setOnClickListener(this);
        Button bt_point=(Button)findViewById(R.id.bt_point);
        bt_point.setOnClickListener(this);
        Button bt_AC=(Button)findViewById(R.id.bt_AC);
        bt_AC.setOnClickListener(this);
        Button bt_CE=(Button)findViewById(R.id.bt_CE);
        bt_CE.setOnClickListener(this);
        Button bt_ok=(Button)findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(this);

        //Spinner
        Spinner f_id= findViewById(R.id.f_volume);
        f_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView f_op2=(TextView)findViewById(R.id.f_op2);
                String[] f_vol=getResources().getStringArray(R.array.volume);
                //获取单位，用于换算
                int len=f_vol[position].length();
                if(f_vol[position].charAt(len-2)==' ')
                    f_op2.setText(f_vol[position].substring(len-1));
                else if(f_vol[position].charAt(len-3)==' ')
                    f_op2.setText(f_vol[position].substring(len-2));
                else if(f_vol[position].charAt(len-4)==' ')
                    f_op2.setText(f_vol[position].substring(len-3));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner t_id= findViewById(R.id.t_volume);
        t_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView t_op2=(TextView)findViewById(R.id.t_op2);
                String[] t_vol=getResources().getStringArray(R.array.volume);
                int len=t_vol[position].length();
                if(t_vol[position].charAt(len-2)==' ')
                    t_op2.setText(t_vol[position].substring(len-1));
                else if(t_vol[position].charAt(len-3)==' ')
                    t_op2.setText(t_vol[position].substring(len-2));
                else if(t_vol[position].charAt(len-4)==' ')
                    t_op2.setText(t_vol[position].substring(len-3));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
    }
    @Override
    public void onClick(View v) {
        TextView f_op1=(TextView)findViewById(R.id.f_op1);
        TextView t_op1=(TextView)findViewById(R.id.t_op1);
        switch (v.getId()) {
            case R.id.bt_0: f_op1.append("0");break;
            case R.id.bt_1: f_op1.append("1");break;
            case R.id.bt_2: f_op1.append("2");break;
            case R.id.bt_3: f_op1.append("3");break;
            case R.id.bt_4: f_op1.append("4");break;
            case R.id.bt_5: f_op1.append("5");break;
            case R.id.bt_6: f_op1.append("6");break;
            case R.id.bt_7: f_op1.append("7");break;
            case R.id.bt_8: f_op1.append("8");break;
            case R.id.bt_9: f_op1.append("9");break;
            case R.id.bt_point:f_op1.append(".");break;
            case R.id.bt_AC:f_op1.setText("");t_op1.setText("0");break;
            case R.id.bt_CE:
                String str1=f_op1.getText().toString();
                int len=str1.length();
                if(len!=0){
                    f_op1.setText(str1.substring(0,len-1));
                }
                break;
            case R.id.bt_ok:
                TextView f_op2=(TextView)findViewById(R.id.f_op2);
                TextView t_op2=(TextView)findViewById(R.id.t_op2);
                Double op=Double.parseDouble(f_op1.getText().toString());//text→String→Double
                if(f_op2.getText().toString().equals("m3"))op*=Math.pow(10,3);//dm3,l作为标准
                if(f_op2.getText().toString().equals("cm3"))op*=Math.pow(10,-3);
                if(f_op2.getText().toString().equals("ml"))op*=Math.pow(10,-3);

                if(t_op2.getText().toString().equals("m3"))op*=Math.pow(10,-3);
                if(t_op2.getText().toString().equals("cm3"))op*=Math.pow(10,3);
                if(t_op2.getText().toString().equals("ml"))op*=Math.pow(10,3);
                t_op1.setText(String.valueOf(op));
                break;
        }
    }
}

