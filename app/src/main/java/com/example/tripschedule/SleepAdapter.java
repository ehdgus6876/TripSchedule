package com.example.tripschedule;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepViewHolder> implements Filterable {
    private ArrayList<SleepItem> filteredItemList;
    private ArrayList<SleepItem> unFilteredList;
    private Context context;

    public SleepAdapter(ArrayList<SleepItem> arrayList,Context context){
        super();
        this.context=context;
        this.unFilteredList=arrayList;
        this.filteredItemList=arrayList;
    }

    @NonNull
    @Override
    public SleepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        SleepViewHolder holder=new SleepViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SleepAdapter.SleepViewHolder holder, final int position) {
        holder.tv_title.setText(filteredItemList.get(position).getTitle());
        holder.tv_phone.setText(filteredItemList.get(position).getTel());
        holder.tv_Address.setText(filteredItemList.get(position).getAddress());


        if(!filteredItemList.get(position).getImage().isEmpty()){
            Glide.with(holder.itemView)
                    .load(filteredItemList.get(position).getImage())
                    .into(holder.image);

        }
        holder.btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodAdapter.selectItems.add(new SelectItem(filteredItemList.get(position).getTitle(),
                        filteredItemList.get(position).getTel(),
                        filteredItemList.get(position).getAddress(),
                        filteredItemList.get(position).getDetail(),
                        filteredItemList.get(position).getImage(),
                        filteredItemList.get(position).getMapx(),
                        filteredItemList.get(position).getMapy(),
                        filteredItemList.get(position).getCode()));
                Log.d("코드",String.valueOf(filteredItemList.get(position).getCode()));
                Toast.makeText(context,"장바구니에 담겼습니다",Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebView.class);
                intent.putExtra("url",filteredItemList.get(position).getDetail());

                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });



    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    public class SleepViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_phone;
        TextView tv_Address;
        Button btn_select;
        Button btn_detail;
        ImageView image;

        public SleepViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_Address=itemView.findViewById(R.id.tv_Address);
            btn_select=itemView.findViewById(R.id.btn_select);
            btn_detail=itemView.findViewById(R.id.btn_detail);
            image=itemView.findViewById(R.id.image);


        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();
                if (charString == null || charString.length() == 0) {

                    filteredItemList = unFilteredList;
                } else {

                    ArrayList<SleepItem> filteringList = new ArrayList<>();
                    for (SleepItem item : unFilteredList) {
                        if (item.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    filteredItemList = filteringList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItemList;
                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList = (ArrayList<SleepItem>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}


