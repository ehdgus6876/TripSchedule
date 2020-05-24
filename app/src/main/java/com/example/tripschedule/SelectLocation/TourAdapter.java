package com.example.tripschedule.SelectLocation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripschedule.R;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> implements Filterable {
    private ArrayList<TourItem> filteredItemList;
    private ArrayList<TourItem> unFilteredList;
    private Context context;
    private List<String> click = new ArrayList<>();
    private String click_name = null;

    public TourAdapter(ArrayList<TourItem> arrayList,Context context){
        super();
        this.context=context;
        this.unFilteredList=arrayList;
        this.filteredItemList=arrayList;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        TourViewHolder holder=new TourViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TourAdapter.TourViewHolder holder, final int position) {
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
                if (click.size()==0){
                    click_name=null;
                }
                for (int i = 0; i < click.size(); i++) {
                    if (click.get(i).equals(filteredItemList.get(position).getTitle())) {
                        click_name = filteredItemList.get(position).getTitle();
                        break;
                    } else {
                        click_name = null;
                    }
                }

                if (click_name == null) {
                    holder.btn_select.setSelected(true);
                    click.add(filteredItemList.get(position).getTitle());
                    FoodAdapter.selectItems.add(new SelectItem(filteredItemList.get(position).getTitle(),
                            filteredItemList.get(position).getTel(),
                            filteredItemList.get(position).getAddress(),
                            filteredItemList.get(position).getDetail(),
                            filteredItemList.get(position).getImage(),
                            filteredItemList.get(position).getMapx(),
                            filteredItemList.get(position).getMapy(),
                            filteredItemList.get(position).getCode()));
                    Toast.makeText(context, "위시리스트 추가", Toast.LENGTH_SHORT).show();
                } else {
                    int i = 0;
                    holder.btn_select.setSelected(false);
                    click.remove(filteredItemList.get(position).getTitle());
                    for (i = 0; i < FoodAdapter.selectItems.size(); i++) {
                        if (FoodAdapter.selectItems.get(i).getTitle().equals(filteredItemList.get(position).getTitle())) {
                            FoodAdapter.selectItems.remove(i);
                            break;
                        }
                    }
                    Toast.makeText(context, "위시리스트 삭제", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < click.size(); i++) {
                    Log.d("위시리스트", click.get(i));
                }

            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebView.class);
                if(!filteredItemList.get(position).getLink().isEmpty()){
                    intent.putExtra("url",filteredItemList.get(position).getLink());
                }
                else{
                    intent.putExtra("url",filteredItemList.get(position).getDetail());
                }


                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });



    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    public class TourViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_phone;
        TextView tv_Address;
        ImageButton btn_select;
        ImageButton btn_detail;
        ImageView image;

        public TourViewHolder(@NonNull View itemView) {
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

                    ArrayList<TourItem> filteringList = new ArrayList<>();
                    for (TourItem item : unFilteredList) {
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
                filteredItemList = (ArrayList<TourItem>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}


