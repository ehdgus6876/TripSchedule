package com.example.tripschedule.Schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.tripschedule.R;
import com.example.tripschedule.SelectLocation.SelectItem;
import com.example.tripschedule.SelectLocation.Weather;

import java.util.ArrayList;

import javax.security.auth.callback.UnsupportedCallbackException;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    ArrayList<SelectItem> items = new ArrayList<>();
    ArrayList<Weather> weathers = new ArrayList<>();


    public ScheduleAdapter() {

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.schedule_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) { //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.list_name.setText(items.get(position).getTitle());
        holder.tv_weather.setText(items.get(position).getWeather());
        holder.tv_max.setText(items.get(position).getHighTemp());
        holder.tv_low.setText(items.get(position).getLowTemp());

        try {
            if (items.get(position).getImage() != null){
                Glide.with(holder.itemView)
                        .load(items.get(position).getImage())
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .fitCenter()
                        .error(R.drawable.error)
                        .into(holder.list_image);
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public ArrayList<SelectItem> getArray(){
        return items;
    }
    public void addItem(SelectItem selectItem){
        items.add(selectItem);
    }
    public void addWeather(Weather weather){
        weathers.add(weather);
    }



    @Override
    public boolean onItemMove(int from_position, int to_position) { //이동할 객체 저장
        SelectItem selectItem = items.get(from_position); //이동할 객체 삭제
        items.remove(from_position); //이동하고 싶은 position에 추가
        items.add(to_position,selectItem); //Adapter에 데이터 이동알림
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView list_name;
        ImageView list_image;
        Button list_button;
        TextView tv_low;
        TextView tv_max;
        TextView tv_weather;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_low = itemView.findViewById(R.id.tv_low);
            tv_max = itemView.findViewById(R.id.tv_max);
            tv_weather = itemView.findViewById(R.id.tv_weather);
            list_name = itemView.findViewById(R.id.list_name);
            list_image = itemView.findViewById(R.id.list_image);
            list_button=itemView.findViewById(R.id.btn_map);
        }

    }



}

