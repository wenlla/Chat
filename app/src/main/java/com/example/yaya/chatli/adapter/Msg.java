package com.example.yaya.chatli.adapter;

import java.util.ArrayList;

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    public static final int TYPE_ARTICLE = 2;
    public static final int TYPE_FORMAT = 3;
    private String content;
    private int type;
    private ArrayList<String> typeList;

    public Msg(String content, int type,ArrayList<String> list){
        this.content=content;
        this.type=type;
        this.typeList = list;
    }
    public String getContent(){
        return content;
    }

    public int getType(){
        return type;
    }

    public ArrayList<String> getTypeList(){
        return typeList;
    }
}
