package com.example.yaya.chatli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yaya.chatli.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenya on 2018/8/18.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> msgList;
    private Context mConText;
    private String mChooseContent = "";
    private ChooseListener mListener;
    public interface ChooseListener{
        void getChooseData(String res);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewL, textViewR;
        RadioGroup typeView;

        public ViewHolder(View view) {
            super(view);
            textViewL = (TextView) itemView.findViewById(R.id.leftText);
            textViewR = (TextView) itemView.findViewById(R.id.rightText);
            typeView = (RadioGroup) itemView.findViewById(R.id.chooseType);
        }
    }

    public MsgAdapter(List<Msg> msgLists, Context context,ChooseListener listener) {
        msgList = msgLists;
        mConText = context;
        mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //onCreateViewHolder()用于创建ViewHolder实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_list, parent, false);
        return new ViewHolder(view);
        //把加载出来的布局传到构造函数中，再返回
    }

    public void onBindViewHolder(ViewHolder Holder, int position) {
        //onBindViewHolder()用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
        ViewHolder myViewHolder = Holder;
        Msg msg = msgList.get(position);

        if (msg.getType() == Msg.TYPE_SENT) {
            myViewHolder.textViewR.setVisibility(View.VISIBLE);
            myViewHolder.textViewL.setVisibility(View.GONE);
            myViewHolder.typeView.setVisibility(View.GONE);
            myViewHolder.textViewR.setText(msg.getContent());
        } else if (msg.getType() == Msg.TYPE_RECEIVED) {
            //增加对消息类的判断，如果这条消息是收到的，显示左边布局，是发出的，显示右边布局
            myViewHolder.textViewL.setVisibility(View.VISIBLE);
            myViewHolder.textViewR.setVisibility(View.GONE);
            myViewHolder.typeView.setVisibility(View.GONE);
            myViewHolder.textViewL.setText(msg.getContent());
        } else if (msg.getType() == Msg.TYPE_ARTICLE) {
            myViewHolder.textViewL.setVisibility(View.GONE);
            myViewHolder.textViewR.setVisibility(View.GONE);
            myViewHolder.typeView.setVisibility(View.VISIBLE);
            itemClick(myViewHolder.typeView);
            ArrayList<String> list=  msg.getTypeList();
            for (String str :list){
                RadioButton button = new RadioButton(mConText);
                myViewHolder.typeView.addView(button);
                button.setId(button.hashCode());
                button.setText(str);
            }
        } else if (msg.getType() == Msg.TYPE_FORMAT) {
            myViewHolder.textViewL.setVisibility(View.GONE);
            myViewHolder.textViewR.setVisibility(View.GONE);
            myViewHolder.typeView.setVisibility(View.VISIBLE);
            itemClick(myViewHolder.typeView);
            ArrayList<String> list=  msg.getTypeList();
            for (String str :list){
                RadioButton button = new RadioButton(mConText);
                myViewHolder.typeView.addView(button);
                button.setId(button.hashCode());
                button.setText(str);
            }
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public String getChooseContent(){
        return mChooseContent;
    }

    //处理单选的点击
    private void itemClick(RadioGroup mRadioGroup) {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton_checked= (RadioButton) group.findViewById(checkedId);
                String gender=radioButton_checked.getText().toString();
                mChooseContent = gender;
                mListener.getChooseData(gender);
            }
        });
    }

}


