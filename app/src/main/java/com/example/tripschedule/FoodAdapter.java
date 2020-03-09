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
import java.util.concurrent.ExecutionException;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {
    public static final ArrayList<SelectItem> selectItems=new ArrayList<>();
    private ArrayList<FoodItem> filteredItemList;
    private ArrayList<FoodItem> unFilteredList;
    private Context context;

    public FoodAdapter(ArrayList<FoodItem> arrayList,Context context){
        super();
        this.context=context;
        this.unFilteredList=arrayList;
        this.filteredItemList=arrayList;


    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        FoodViewHolder holder=new FoodViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, final int position) {
        holder.tv_title.setText(filteredItemList.get(position).getTitle());
        holder.tv_phone.setText(filteredItemList.get(position).getTel());
        holder.tv_Address.setText(filteredItemList.get(position).getAddress());

        if(!filteredItemList.get(position).getImage().isEmpty()){
            Glide.with(holder.itemView)
                    .load(filteredItemList.get(position).getImage())
                    .override(540,402)
                    .fitCenter()
                    .error(R.drawable.error)
                    .into(holder.image);

        }

        holder.btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItems.add(new SelectItem(filteredItemList.get(position).getTitle(),
                        filteredItemList.get(position).getTel(),
                        filteredItemList.get(position).getAddress(),
                        filteredItemList.get(position).getDetail(),
                        filteredItemList.get(position).getImage(),
                        filteredItemList.get(position).getMapx(),
                        filteredItemList.get(position).getMapy(),
                        filteredItemList.get(position).getCode()));
                Log.d("dong", String.valueOf(filteredItemList.get(position).getCode()));

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

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_phone;
        TextView tv_Address;
        Button btn_select;
        Button btn_detail;
        ImageView image;

        public FoodViewHolder(@NonNull View itemView) {
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

                    ArrayList<FoodItem> filteringList = new ArrayList<>();
                    for (FoodItem item : unFilteredList) {
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
                filteredItemList = (ArrayList<FoodItem>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}


