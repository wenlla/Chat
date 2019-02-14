package com.example.yaya.chatli.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yaya.chatli.R;
import com.example.yaya.chatli.adapter.MsgSimple;
import com.example.yaya.chatli.adapter.MsgSimpleAdapter;
import com.example.yaya.chatli.db.ConnectDB;
import com.example.yaya.chatli.db.chatbot;
import com.example.yaya.chatli.db.conversacion;
import com.example.yaya.chatli.util.stringFilterUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ChatActivity extends AppCompatActivity {
    //页面组件

    private List<MsgSimple> msgList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText editText;
    private RelativeLayout addBtn;
    private MsgSimpleAdapter Adapter;

    //Full-Text-Search 数据库
    private ConnectDB connectDB;
    private List<chatbot> listinfo;
    private List<conversacion> listcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        //FTS 连接
        connectDB = ConnectDB.getInstance(this);

        importSheetChat();
        importSheetConver();

        initView();
        initMsgs();

        //发送按钮点击事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pregunta = editText.getText().toString();

                String preguntaFilter = stringFilterUtil.tokenize(pregunta).toString();

                List<String> respuestaList = new ArrayList();

                String respuesta = null;
                String url = null;

                if (pregunta == null || pregunta.equals("")) {
                    //输入不能为空
                    Toast.makeText(ChatActivity.this, "La entrada no se puede vacía", Toast.LENGTH_SHORT).show();
                } else {

                    MsgSimple msg2 = new MsgSimple(pregunta, MsgSimple.TYPE_SENT);
                    msgList.add(msg2);

                    listcon = connectDB.searchCONVERSACION(preguntaFilter);
                    listinfo = connectDB.searchCHATBOT(preguntaFilter);

                    if (listinfo.isEmpty() && listcon.isEmpty()) {
                        Intent intent = new Intent(ChatActivity.this, SearchInternetActivity.class);
                        startActivity(intent);
                    }
                    else if (listcon.size() > 0) {
                        for (int i = 0; i < listcon.size(); i++) {
                            respuesta = listcon.get(i).getRespuestas() + "\n";
                            respuestaList.add(respuesta);
                            //Collections.shuffle(respuestaList);
                        }
                    } else {
                        for (int i = 0; i < listinfo.size(); i++) {
                            respuesta = listinfo.get(i).getRespuesta() + "\n";
                            for (int j = 0; j < listinfo.size(); j++) {
                                url = listinfo.get(j).getUrl() + "\n\n";
                            }
                            respuestaList.add(respuesta + url);
                        }
                    }
                    respuesta = "" + respuestaList.toString().replace("[", "").
                            replace("]", "").replace(",", "").trim();

                    MsgSimple msg1 = new MsgSimple(respuesta, MsgSimple.TYPE_RECEIVED);
                    msgList.add(msg1);

                    Adapter.notifyItemInserted(msgList.size() - 1);
                    //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
                    if (editText.length() > 0) {
                        TextKeyListener.clear(editText.getText());
                    }
                    //调用EditText的setText()方法将输入的内容清空
                }
            }
        });
    }

    //初始化消息数据
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText = (EditText) findViewById(R.id.editText);
        addBtn = (RelativeLayout) findViewById(R.id.addBtn);

        //LinearLayoutLayout即线性布局，创建对象后把它设置到RecyclerView当中
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        //创建Adapter的实例并将数据传入到Adapter的构造函数中
        Adapter = new MsgSimpleAdapter(msgList);
        recyclerView.setAdapter(Adapter);
    }

    private void initMsgs() {
        MsgSimple msgR = new MsgSimple("Hola bienvenido a Chatbot! ", MsgSimple.TYPE_RECEIVED);
        msgList.add(msgR);

    }
    // 将 chat excel 导入 sqlite 数据库
    private void importSheetChat(){
        try {
            InputStream is = getResources().getAssets().open("infoDemo.xls");

            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");

            Workbook book = Workbook.getWorkbook(is,workbookSettings);
            Sheet sheet = book.getSheet(0);
            for (int j = 0; j < sheet.getRows(); ++j) {
                connectDB.initDataChat(sheet.getCell(0, j).getContents(),
                        sheet.getCell(1, j).getContents());
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

    }

    // 将 conversacion excel 导入 sqlite 数据库
    private void importSheetConver(){
        try {
            InputStream is = getResources().getAssets().open("conversation.xls");

            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");

            Workbook book = Workbook.getWorkbook(is,workbookSettings);
            Sheet sheet = book.getSheet(0);
            for (int j = 0; j < sheet.getRows(); ++j) {
                connectDB.initDataConver(sheet.getCell(0, j).getContents(),
                        sheet.getCell(1, j).getContents());
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

    }
}
