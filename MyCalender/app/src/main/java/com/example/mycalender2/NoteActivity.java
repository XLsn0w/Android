package com.example.mycalender2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private ListView noteListView;
    private Button add;
    private Button back;

    private List<NoteInfo> noteList = new ArrayList<>();
    private ListAdapter mListAdapter;

    private static NoteDataBaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();

        dbHelper = new NoteDataBaseHelper(this,"MyNote.db",null,1);

        initView();

        if (intent != null){
            getNoteList();
            mListAdapter.refreshDataSet();
        }

    }
    private void initView(){
        noteListView = findViewById(R.id.note_list);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        getNoteList();
        mListAdapter = new ListAdapter(NoteActivity.this,noteList);
        noteListView.setAdapter(mListAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//点击备忘录转向修改
                NoteInfo noteInfo = noteList.get(position);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("noteInfo",(Serializable)noteInfo);
                intent.putExtras(bundle);
                intent.setClass(NoteActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//长按备忘录删除
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final NoteInfo noteInfo = noteList.get(position);
                new AlertDialog.Builder(NoteActivity.this)
                        .setMessage("确定要删除吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Note.deleteNote(dbHelper,Integer.parseInt(noteInfo.getId()));
                                noteList.remove(position);
                                mListAdapter.refreshDataSet();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                return true;
            }
        });
    }


    private void getNoteList(){//显示当前备忘录
        noteList.clear();
        Cursor allNotes = Note.getAllNotes(dbHelper);
        for (allNotes.moveToFirst(); !allNotes.isAfterLast(); allNotes.moveToNext()){
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setId(allNotes.getString(allNotes.getColumnIndex(Note._id)));
            noteInfo.setTitle(allNotes.getString(allNotes.getColumnIndex(Note.title)));
            noteInfo.setContent(allNotes.getString(allNotes.getColumnIndex(Note.content)));
            noteInfo.setDate(allNotes.getString(allNotes.getColumnIndex(Note.time)));
            noteList.add(noteInfo);
        }
    }

    public static NoteDataBaseHelper getDbHelper() {
        return dbHelper;
    }


}