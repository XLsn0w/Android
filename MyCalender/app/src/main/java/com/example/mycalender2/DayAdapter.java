package com.example.mycalender2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DayAdapter extends BaseAdapter {

    private List<Day> list;
    private Context context;

    public DayAdapter(List<Day> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Day getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView textView;
            textView = new TextView(context);
            textView.setPadding(5, 5, 5, 5);

        Day bean = getItem(position);

        textView.setText(bean.getDay() + "");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        if (bean.isCurrentDay()) {
            textView.setBackgroundColor(Color.RED);//当天设为红底白字
            textView.setTextColor(Color.WHITE);
        } else if (bean.isCurrentMonth()) {
            textView.setBackgroundColor(Color.WHITE);//当月白底黑字
            textView.setTextColor(Color.BLACK);
        } else {
            textView.setBackgroundColor(Color.GRAY);//非当月灰底黑字
            textView.setTextColor(Color.BLACK);
        }
        return textView;
    }
}

