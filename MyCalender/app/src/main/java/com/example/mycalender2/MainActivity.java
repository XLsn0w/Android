package com.example.mycalender2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView nowdate;
    private Button preMonth;
    private Button nextMonth;
    private GridView gv;
    private Button setMonth;
    private EditText newMonth;
    private EditText newYear;
    private Button note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        nowdate = (TextView) findViewById(R.id.nowdate);
        preMonth = (Button) findViewById(R.id.preMonth);
        nextMonth = (Button) findViewById(R.id.nextMonth);
        gv = (GridView) findViewById(R.id.gv);
        setMonth = (Button) findViewById(R.id.setMonth);
        newMonth = (EditText) findViewById(R.id.newMonth);
        newYear = (EditText) findViewById(R.id.newYear);
        note = (Button) findViewById(R.id.note);

        initAdapter();
    }


    private void initAdapter() {
        final List<Day> dataList = new ArrayList<>();
        final DayAdapter adapter = new DayAdapter(dataList, this);
        gv.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();// 日历

        updateAdapter(calendar, dataList, adapter);

        newMonth.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));
        newYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        preMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                updateAdapter(calendar, dataList, adapter);
            }
        });

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
                updateAdapter(calendar, dataList, adapter);
            }
        });

        setMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nmouth = Integer.parseInt(newMonth.getText().toString());
                int nyear = Integer.parseInt(newYear.getText().toString());
                if (nmouth>0&&nmouth<13)
                {
                    calendar.set(Calendar.MONTH, nmouth-1);//1月是0
                    calendar.set(calendar.YEAR, nyear);
                    updateAdapter(calendar, dataList, adapter);
                }
            }
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateAdapter(Calendar calendar, List<Day> dataList, DayAdapter adapter) {//显示日历列表
        dataList.clear();
        setCurrentDate(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK)-1;


        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        int preMonthDays = getMonth(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));//上个月月末应显示在本月日历左上
        for (int i = 0; i < weekIndex; i++) {
            Day bean = new Day();
            bean.setYear(calendar.get(Calendar.YEAR));
            bean.setMonth(calendar.get(Calendar.MONTH) + 1);
            bean.setDay(preMonthDays - weekIndex + i + 1);
            bean.setCurrentDay(false);
            bean.setCurrentMonth(false);
            dataList.add(bean);
        }

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        int currentDays = getMonth(calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.YEAR));//显示本月
        for (int i = 0; i < currentDays; i++) {
            Day bean = new Day();
            bean.setYear(calendar.get(Calendar.YEAR));
            bean.setMonth(calendar.get(Calendar.MONTH) + 1);
            bean.setDay(i + 1);
            String nowDate = getFormatTime("yyyy-M-d", Calendar.getInstance().getTime());
            String selectDate = getFormatTime("yyyy-M-", calendar.getTime()) + (i + 1);
            if (nowDate.contentEquals(selectDate)) {
                bean.setCurrentDay(true);
            } else {
                bean.setCurrentDay(false);
            }
            bean.setCurrentMonth(true);
            dataList.add(bean);
        }

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        weekIndex = calendar.get(Calendar.DAY_OF_WEEK)-1;//下个月月初应显示在本月日历右下

        if(weekIndex!=0)
        {for (int i = 0; i < 7 - weekIndex; i++) {
            Day bean = new Day();
            bean.setYear(calendar.get(Calendar.YEAR));
            bean.setMonth(calendar.get(Calendar.MONTH) + 1);
            bean.setDay(i + 1);
            bean.setCurrentDay(false);
            bean.setCurrentMonth(false);
            dataList.add(bean);
        }}


        adapter.notifyDataSetChanged();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
    }

    private void setCurrentDate(Calendar calendar) {
        nowdate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
    }

    public boolean runYear(int y) {
        return y % 4 == 0 && y % 100 != 0 || y % 400 == 0;
    }//闰年判断

    public static String getFormatTime(String p, java.util.Date t) {
        return new SimpleDateFormat(p, Locale.CHINESE).format(t);
    }
    public int getMonth(int m, int y) {//根据月份设置日数
        switch (m) {
            case 2:
                return runYear(y) ? 29 : 28;
            case 4:
                return 30;
            case 6:
                return 30;
            case 9:
                return 30;
            case 11:
                return 30;
            default:
                return 31;
        }
    }


}

