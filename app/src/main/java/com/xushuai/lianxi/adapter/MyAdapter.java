package com.xushuai.lianxi.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xushuai.lianxi.R;
import com.xushuai.lianxi.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2017/8/20
 * author:徐帅(acer)
 * funcation:RecycleView的适配器
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<UserBean.DataBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    public OnItemClickListener listener;

    public MyAdapter(Context context, List<UserBean.DataBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getUserImg()).into(holder.image);
        holder.userName.setText(list.get(position).getUserName());
        holder.userAge.setText(list.get(position).getUserAge() + "");
        holder.occupation.setText(list.get(position).getOccupation());
        holder.introduction.setText(list.get(position).getIntroduction());
        ObjectAnimator//
                .ofFloat(holder.image, "alpha",0.0F,1f)//
                .setDuration(3000)//
                .start();
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position);
            }
        });
        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClickListener(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //内部类ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView userName, userAge, occupation, introduction;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userAge = (TextView) itemView.findViewById(R.id.userAge);
            occupation = (TextView) itemView.findViewById(R.id.occupation);
            introduction = (TextView) itemView.findViewById(R.id.introduction);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);

        void onItemLongClickListener(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}