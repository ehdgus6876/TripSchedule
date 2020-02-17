package com.example.tripschedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> implements Filterable {

    private ArrayList<BusStation> filteredItemList;
    private ArrayList<BusStation> unFilteredList;
    private Context context;



    public BusAdapter(ArrayList<BusStation> arrayList, Context context) {
        super();
        this.context = context;
        this.unFilteredList=arrayList;
        this.filteredItemList=arrayList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_busstation,parent,false);
        BusViewHolder holder=new BusViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        holder.tv_terminal.setText(filteredItemList.get(position).getName());


    }

    @Override
    public int getItemCount() {

        return filteredItemList!=null? filteredItemList.size():unFilteredList.size();
    }




    public class BusViewHolder extends RecyclerView.ViewHolder {
        TextView tv_terminal;

        public BusViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_terminal=itemView.findViewById(R.id.tv_terminal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    int code=filteredItemList.get(pos).getCode();
                    boolean check_b=BusFragment.check_bus;
                    boolean check_sb=BusFragment.check_speedbus;


                    if(check_b){
                        Intent intent= new Intent(context,BusTimeActivity.class);
                        intent.putExtra("code",code);
                        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    else if(check_sb){
                        Intent intent= new Intent(context,SpeedBusTimeActivity.class);
                        intent.putExtra("code",code);
                        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }


                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //FilterResults results = new FilterResults();
                String charString=constraint.toString();
                if (charString == null || charString.length() == 0) {
                    //results.values = arrayList;
                    //results.count = arrayList.size();
                    filteredItemList=unFilteredList;
                } else {
                    //ArrayList<BusStation> itemList = new ArrayList<>();
                    ArrayList<BusStation> filteringList = new ArrayList<>();
                    for (BusStation item : unFilteredList) {
                        if (item.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    filteredItemList=filteringList;
                    //results.values = itemList;
                    //results.count = itemList.size();
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItemList;
                return filterResults;
                //return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList = (ArrayList<BusStation>)results.values;
                notifyDataSetChanged();

            }
        };
    }

}
