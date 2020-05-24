package com.example.tripschedule.SelectLocation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripschedule.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TourFragment extends Fragment implements TextWatcher {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ArrayList<TourItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    private ArrayList<String> arrayListSpinner;
    private ArrayAdapter<String> arrayAdapterSpinner;
    private TourAdapter adapter;
    private int tmpcode;

    public TourFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tour,container,false);
        recyclerView=v.findViewById(R.id.rv_tour);
        editText=v.findViewById(R.id.editTextFilter);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        editText.addTextChangedListener(this);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        database=FirebaseDatabase.getInstance();
        arrayListSpinner=new ArrayList<>();

        arrayListSpinner.add("관광");
        arrayListSpinner.add("축제");
        arrayListSpinner.add("전시회");
        arrayListSpinner.add("산");
        arrayListSpinner.add("백화점");



        spinner=v.findViewById(R.id.spinner);

        arrayAdapterSpinner=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayListSpinner);

        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    tmpcode=3;
                    databaseReference=database.getReference("GwanGwang/tour");
                    databaseReference();
                }
                else if(position==1){
                    tmpcode=3;
                    databaseReference=database.getReference("GwanGwang/festival");
                    databaseReference();
                }
                else if(position==2){
                    tmpcode=3;
                    databaseReference=database.getReference("GwanGwang/exhibition");
                    databaseReference();
                }
                else if(position==3){
                    tmpcode=3;
                    databaseReference=database.getReference("GwanGwang/mountain");
                    databaseReference();
                }
                else if(position==4){
                    tmpcode=3;
                    databaseReference=database.getReference("GwanGwang/department");
                    databaseReference();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    void databaseReference(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                int i =0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TourItem tourItem=snapshot.getValue(TourItem.class);
                    arrayList.add(tourItem);
                    arrayList.get(i).setCode(tmpcode);
                    i++;
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TourFragment", String.valueOf(databaseError.toException()));
            }
        });
        adapter=new TourAdapter(arrayList,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
