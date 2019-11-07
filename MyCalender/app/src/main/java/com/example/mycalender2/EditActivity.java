package com.example.mycalender2;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private Button save;
    private Button back;
    private TextView now;

    private EditText title;
    private EditText content;
    private NoteInfo currentNote;
    private boolean insertFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        setListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            currentNote = (NoteInfo) bundle.getSerializable("noteInfo");
            title.setText(currentNote.getTitle());
            content.setText(currentNote.getContent());
            insertFlag = false;
        }
    }

    private void initView(){
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        now = findViewById(R.id.nowtext);
        content = findViewById(R.id.edit_content);
        title = findViewById(R.id.edit_title);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        now.setText(sdf.format(date));

    }

    private void setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( title.getText().toString().equals("") ||
                        content.getText().toString().equals("")){
                    Toast.makeText(EditActivity.this,"保存失败", Toast.LENGTH_LONG).show();
                }else {
                    saveNote();
                    Intent intent = new Intent(EditActivity.this,NoteActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void saveNote(){
        NoteDataBaseHelper dbHelper = NoteActivity.getDbHelper();

        ContentValues values = new ContentValues();
        values.put(Note.title,title.getText().toString());
        values.put(Note.content,content.getText().toString());
        values.put(Note.time,now.getText().toString());
        if (insertFlag){
            Note.insertNote(dbHelper,values);
        }else{
            Note.updateNote(dbHelper, Integer.parseInt(currentNote.getId()),values);
        }
    }

}
