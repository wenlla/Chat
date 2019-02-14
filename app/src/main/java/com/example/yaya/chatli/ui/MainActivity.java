package com.example.yaya.chatli.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.yaya.chatli.R;
import com.example.yaya.chatli.db.ConnectDB;

import java.io.*;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity{

    private ImageButton indexImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        indexImg = (ImageButton) findViewById(R.id.index_button);

        indexImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                   Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                   startActivity(intent);
            }

            });
    }



}