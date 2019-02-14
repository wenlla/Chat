package com.example.yaya.chatli.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yaya.chatli.R;
import com.example.yaya.chatli.adapter.Msg;
import com.example.yaya.chatli.adapter.MsgAdapter;
import com.example.yaya.chatli.adapter.MsgSimple;
import com.example.yaya.chatli.util.stringFilterUtil;
import com.example.yaya.chatli.util.trucoMode;

import java.util.ArrayList;
import java.util.List;

public class SearchInternetActivity extends AppCompatActivity {

    //页面组件
    private List<Msg> msgList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText editText;
    private RelativeLayout addBtn;
    private MsgAdapter mAdapter;

    private String keyString = "";
    private String searchContent = "";

    private int guideStep = 0;
    private static String TAG = "yayatag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_internet);

        initView();
        initMsgs();

        //发送按钮点击事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pregunta = editText.getText().toString();
                String preguntaFilter = stringFilterUtil.tokenize(pregunta).toString().replace("[", "").
                        replace("]", "").replace(",", "").trim();

                if (guideStep == 3) {
                    //当进行到第二步后需要判断 关键词 是否加逗号 并保存
                    searchContent = preguntaFilter;
                }

                if (pregunta == null || pregunta.equals("")) {
                    //输入不能为空
                    Toast.makeText(SearchInternetActivity.this, "La entrada no se puede vacía", Toast.LENGTH_SHORT).show();
                }else {

                    Msg msg2 = new Msg(pregunta, Msg.TYPE_SENT, null);
                    msgList.add(msg2);

                    if (guideStep == 0) {
                        guideStep++;
                        Log.d(TAG,"0");
                    }

                    else if (guideStep == 3) {
                        Msg msg = new Msg(getString(R.string.nota_3), Msg.TYPE_RECEIVED, null);
                        msgList.add(msg);
                        ArrayList<String> list = new ArrayList<>();
                        list.add("PDF");
                        list.add("TXT");
                        list.add("HTML");
                        list.add("PPT");
                        Msg msgType = new Msg("", Msg.TYPE_FORMAT, list);
                        msgList.add(msgType);
                        Msg msgTips = new Msg(getString(R.string.tips), Msg.TYPE_RECEIVED, null);
                        msgList.add(msgTips);

                        Log.d(TAG, "2");
                    }
                }

                mAdapter.notifyItemInserted(msgList.size() - 1);
                //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
                recyclerView.scrollToPosition(msgList.size() - 1);
                //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
                if (editText.length() > 0) {
                    TextKeyListener.clear(editText.getText());
                }
                //调用EditText的setText()方法将输入的内容清空
            }
        });
    }

    private MsgAdapter.ChooseListener mListener = new MsgAdapter.ChooseListener() {
        @Override
        public void getChooseData(String res) {
            Log.d(TAG, res);
            if (res.equalsIgnoreCase(getString(R.string.art_type))) {
                keyString = res;
                updataConversation();
            } else if (res.equalsIgnoreCase(getString(R.string.tesis_type))) {
                keyString = res;
                updataConversation();
            } else if (res.equalsIgnoreCase(getString(R.string.guia_type))) {
                keyString = res;
                updataConversation();
            } else if (res.equalsIgnoreCase("PDF")) {
                searchResultFromInternet(searchContent + "pdf");
            } else if (res.equalsIgnoreCase("TXT")) {
                searchResultFromInternet(searchContent + "tet");
            } else if (res.equalsIgnoreCase("PPT")) {
                searchResultFromInternet(searchContent + "ppt");
            } else if (res.equalsIgnoreCase("HTML")) {
                searchResultFromInternet(searchContent + "html");
            }
        }
    };

    private void updataConversation() {
        Msg msg = new Msg(getString(R.string.nota_2), Msg.TYPE_RECEIVED, null);
        msgList.add(msg);
        guideStep = 3;
        mAdapter.notifyItemInserted(msgList.size() - 1);
        recyclerView.scrollToPosition(msgList.size() - 1);
    }

    private void searchResultFromInternet(String content) {
        Log.d(TAG, "搜索：" + keyString + "|内容：" + content);
        ArrayList<String> result = new ArrayList<>();
        if (keyString.equalsIgnoreCase(getString(R.string.art_type))) {
            result = trucoMode.ArticuleMode(content);
        } else if (keyString.equalsIgnoreCase(getString(R.string.tesis_type))) {
            result = trucoMode.ThesisMode(content);
        } else if (keyString.equalsIgnoreCase(getString(R.string.guia_type))) {
            result = trucoMode.guideMode(content);
        }
        for (String str : result) {
            Msg msg = new Msg(str, Msg.TYPE_RECEIVED, null);
            msgList.add(msg);
            mAdapter.notifyItemInserted(msgList.size() - 1);
            recyclerView.scrollToPosition(msgList.size() - 1);
        }
        Msg msgEnd = new Msg(getString(R.string.end_tips), Msg.TYPE_RECEIVED, null);
        msgList.add(msgEnd);
        guideStep = 0;
        mAdapter.notifyItemInserted(msgList.size() - 1);
        recyclerView.scrollToPosition(msgList.size() - 1);
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
        mAdapter = new MsgAdapter(msgList, this,mListener);
        recyclerView.setAdapter(mAdapter);
    }

    private void initMsgs() {
        Msg msg = new Msg(getString(R.string.nota), Msg.TYPE_RECEIVED, null);
        msgList.add(msg);
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.art_type));
        list.add(getString(R.string.tesis_type));
        list.add(getString(R.string.guia_type));
        Msg msgType = new Msg("", Msg.TYPE_ARTICLE, list);
        msgList.add(msgType);

        Log.d(TAG, "1");

        guideStep = 2;

    }

}
