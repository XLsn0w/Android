package com.example.mycalender2;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class ViewHolder{
    public ImageView itemIcon;
    public TextView itemNoteTitle;
    public TextView itemNoteDate;

    View itemView;

    public ViewHolder(View itemView) {
        if (itemView == null){
            throw new IllegalArgumentException("item View can not be null!");
        }
        this.itemView = itemView;
        itemIcon = itemView.findViewById(R.id.rand_icon);
        itemNoteTitle = itemView.findViewById(R.id.item_note_title);
        itemNoteDate = itemView.findViewById(R.id.item_note_date);

    }
}


