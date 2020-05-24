package com.example.tripschedule.Transport;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripschedule.Calendar.CalendarActivity;
import com.example.tripschedule.R;
import com.example.tripschedule.SelectLocation.SelectLocationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SpeedAdapter extends RecyclerView.Adapter<SpeedAdapter.ViewHolder> {
    private ArrayList<SpeedBus> dataList;
    String waste;
    String star;
    SimpleDateFormat df;
    Calendar cal,cal1;

    SpeedAdapter(ArrayList<SpeedBus> items){
        this.dataList=items;
    }
    @NonNull
    @Override
    public SpeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bustime,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeedAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getStartTerminal()+"->"+dataList.get(position).getDestTerminal());
        holder.tv_time.setText(dataList.get(position).getWasteTime());
        holder.tv_cost.setText(dataList.get(position).getFare());
        holder.tv_stime.setText(dataList.get(position).getSchedule());

        cal=Calendar.getInstance();
        cal1=Calendar.getInstance();




        holder.btn_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df=new SimpleDateFormat("HHmm");
                waste=dataList.get(position).getWasteTime().replace(":","");
                star=dataList.get(position).getSchedule().replace(":","").replace("(우등)","");
                if(waste.length()==3){
                    waste="0".concat(waste);
                }

                try{
                    cal.setTime(df.parse(star));
                    cal1.setTime(df.parse(waste));
                    cal.add(Calendar.HOUR_OF_DAY,cal1.get(Calendar.HOUR_OF_DAY));
                    cal.add(Calendar.MINUTE,cal1.get(Calendar.MINUTE));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CalendarActivity.arrivaltime=String.valueOf(df.format(cal.getTime()));
                Log.d("dong","W"+waste);
                Log.d("dong","S"+star);
                Log.d("dong", String.valueOf(CalendarActivity.arrivaltime));
                Intent intent= new Intent(v.getContext(), SelectLocationActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null !=dataList?dataList.size():0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_time,tv_cost,tv_stime;
        Button btn_sel;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tv_title=itemView.findViewById(R.id.tv_title);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_cost=itemView.findViewById(R.id.tv_cost);
            this.tv_stime=itemView.findViewById(R.id.tv_stime);
            this.btn_sel=itemView.findViewById(R.id.btn_sel);

        }

    }
}
