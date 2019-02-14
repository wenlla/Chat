package com.example.yaya.chatli.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaya.chatli.R;

import java.util.List;

public class MsgSimpleAdapter extends RecyclerView.Adapter<MsgSimpleAdapter.ViewHolder> {

    private List<MsgSimple> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewL, textViewR;

        public ViewHolder(View view) {
            super(view);
            textViewL = (TextView) itemView.findViewById(R.id.leftTextS);
            textViewR = (TextView) itemView.findViewById(R.id.rightTextS);
        }
    }
    public MsgSimpleAdapter(List<MsgSimple> msgLists){
        msgList=msgLists;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //onCreateViewHolder()用于创建ViewHolder实例
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_list_simple,parent,false);
        return new ViewHolder(view);
        //把加载出来的布局传到构造函数中，再返回
    }
    public void onBindViewHolder(ViewHolder Holder, int position){
        //onBindViewHolder()用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
        MsgSimple msg = msgList.get(position);

        if(msg.getType()== MsgSimple.TYPE_SENT) {
            Holder.textViewR.setVisibility(View.VISIBLE);
            Holder.textViewL.setVisibility(View.GONE);
            Holder.textViewR.setText(msg.getContent());
        } else if(msg.getType()== MsgSimple.TYPE_RECEIVED){
            //增加对消息类的判断，如果这条消息是收到的，显示左边布局，是发出的，显示右边布局
            Holder.textViewL.setVisibility(View.VISIBLE);
            Holder.textViewR.setVisibility(View.GONE);
            Holder.textViewL.setText(msg.getContent());
        }
    }
    @Override
    public int getItemCount(){
        return msgList.size();
    }

}


