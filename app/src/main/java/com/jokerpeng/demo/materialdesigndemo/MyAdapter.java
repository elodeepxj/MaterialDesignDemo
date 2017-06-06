package com.jokerpeng.demo.materialdesigndemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PengXiaoJie on 2017/5/25.13 29..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private List<String> mList;
    private onRecyclerViewItemClickListerner recyclerViewItemClickListerner;

    public void setRecyclerViewItemClickListerner(onRecyclerViewItemClickListerner listerner) {
        this.recyclerViewItemClickListerner = listerner;
    }

    public MyAdapter(List<String> list) {
        this.mList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if(null != v && recyclerViewItemClickListerner != null){
            recyclerViewItemClickListerner.onItemClick(v, (int) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    public interface onRecyclerViewItemClickListerner{
        void onItemClick(View view, int position);
    }
    public void addItem(int position){
        mList.add(position,"新数据");
        notifyItemInserted(position);
    }

}
