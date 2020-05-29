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

class OnlyDateAdapter extends RecyclerView.Adapter<OnlyDateAdapter.ItemViewHolder>  {
    ArrayList<OnlyDateItem> items = new ArrayList<>();

    public OnlyDateAdapter() {


    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dateitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) { //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.btn_date.setText(items.get(position).getStartdate().substring(4,6)+"월"+items.get(position).getStartdate().substring(6,8)+"일~"+
                items.get(position).getEnddate().substring(4,6)+"월"+items.get(position).getEnddate().substring(6,8)+"일");
        holder.btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MyScheduleActivity.class);
                intent.putExtra("start",items.get(position).getStartdate());
                intent.putExtra("end",items.get(position).getEnddate());
                intent.putExtra("id",items.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public ArrayList<OnlyDateItem> getArray(){
        return items;
    }
    public void addItem(OnlyDateItem onlyDateItem){
        items.add(onlyDateItem);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        Button btn_date;


        public ItemViewHolder(View itemView) {
            super(itemView);
            btn_date = itemView.findViewById(R.id.dateItem);

        }

    }



}

