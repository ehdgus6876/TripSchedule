package com.example.tripschedule;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.tripschedule.Schedule.ItemTouchHelperListener;
import com.example.tripschedule.SelectLocation.SelectItem;
import com.example.tripschedule.SelectLocation.Weather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class MyScheduleadapter extends RecyclerView.Adapter<MyScheduleadapter.ItemViewHolder> implements ItemTouchHelperListener {
    ArrayList<SelectItem> items = new ArrayList<>();
    ArrayList<Weather> weathers = new ArrayList<>();


    public MyScheduleadapter() {


    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.myschedule_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) { //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.list_name.setText(items.get(position).getTitle());

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


        public ItemViewHolder(View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);

        }

    }



}

