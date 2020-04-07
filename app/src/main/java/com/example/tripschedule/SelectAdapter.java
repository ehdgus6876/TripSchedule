package com.example.tripschedule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    private ArrayList<SelectItem> dataList;
    public static List<String> delete = new ArrayList<>();

    SelectAdapter(){
        dataList=FoodAdapter.selectItems;
    }

    @NonNull
    @Override
    public SelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        holder.tv_phone.setText(dataList.get(position).getTel());
        holder.tv_Address.setText(dataList.get(position).getAddress());


        if(!dataList.get(position).getImage().isEmpty()){
            Glide.with(holder.itemView)
                    .load(dataList.get(position).getImage())
                    .into(holder.image);

        }
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.add(FoodAdapter.selectItems.get(position).getTitle());
                FoodAdapter.selectItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
                for(int i =0;i<delete.size();i++){
                    Log.d("위시리스트삭제",delete.get(i));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null!=dataList?dataList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_phone;
        TextView tv_Address;
        Button btn_delete;
        Button btn_detail;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_Address=itemView.findViewById(R.id.tv_Address);
            btn_delete=itemView.findViewById(R.id.btn_select);
            btn_detail=itemView.findViewById(R.id.btn_detail);
            image=itemView.findViewById(R.id.image);
        }
    }
}
