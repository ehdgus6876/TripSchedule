package com.example.tripschedule;

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
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SleepFragment extends Fragment implements TextWatcher {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ArrayList<SleepItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    private ArrayList<String> arrayListSpinner;
    private ArrayAdapter<String> arrayAdapterSpinner;
    private SleepAdapter adapter;
    private int tmpcode;

    public SleepFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sleep,container,false);
        recyclerView=v.findViewById(R.id.rv_Sleep);
        editText=v.findViewById(R.id.editTextFilter);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        editText.addTextChangedListener(this);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        database=FirebaseDatabase.getInstance();
        arrayListSpinner=new ArrayList<>();

        arrayListSpinner.add("호텔");
        arrayListSpinner.add("게스트하우스");




        spinner=v.findViewById(R.id.spinner);

        arrayAdapterSpinner=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayListSpinner);

        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    tmpcode=1000;
                    databaseReference=database.getReference("Rooms/hotel");
                    databaseReference();
                }
                else if(position==1){
                    tmpcode=1000;
                    databaseReference=database.getReference("Rooms/guests");
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
                    SleepItem sleepItem=snapshot.getValue(SleepItem.class);
                    arrayList.add(sleepItem);
                    arrayList.get(i).setCode(tmpcode);
                    i++;
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SleepFragment", String.valueOf(databaseError.toException()));
            }
        });
        adapter=new SleepAdapter(arrayList,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
