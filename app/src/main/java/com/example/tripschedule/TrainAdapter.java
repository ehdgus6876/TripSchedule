package com.example.tripschedule;

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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> implements Filterable  {

    private ArrayList<TrainStation> filteredItemList;
    private ArrayList<TrainStation> unFilteredList;
    private Context context;




    public TrainAdapter(ArrayList<TrainStation> arrayList, Context context) {
        super();
        this.unFilteredList = arrayList;
        this.filteredItemList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainstation,parent,false);
        TrainViewHolder holder=new TrainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        holder.tv_terminal.setText(filteredItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자 (arrayList !=null 이 참이면 왼쪽이 실행 거짓이면 오른쪽 실행)
        return filteredItemList.size();
    }


    public class TrainViewHolder extends RecyclerView.ViewHolder {
        TextView tv_terminal;

        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_terminal=itemView.findViewById(R.id.tv_terminal1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();
                    String code=filteredItemList.get(pos).getCode();

                    Intent intent= new Intent(context,TrainTimeActivity.class);
                    intent.putExtra("code",code);
                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

        }
    }
    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString= constraint.toString();
                if(charString == null || charString.length() == 0){
                    filteredItemList=unFilteredList;
                } else{
                    ArrayList<TrainStation> filteringList = new ArrayList<>();
                    for(TrainStation name : unFilteredList){
                        if(name.getName().toLowerCase().contains(charString.toLowerCase())){
                            filteringList.add(name);
                        }
                    }
                    filteredItemList=filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList=(ArrayList<TrainStation>)results.values;
                notifyDataSetChanged();

            }
        };
    }

}
