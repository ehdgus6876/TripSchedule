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

public class FoodFragment extends Fragment implements TextWatcher {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ArrayList<FoodItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    private ArrayList<String> arrayListSpinner;
    private ArrayAdapter<String> arrayAdapterSpinner;
    private FoodAdapter adapter;

    public FoodFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_food,container,false);
        recyclerView=v.findViewById(R.id.rv_food);
        editText=v.findViewById(R.id.editTextFilter);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        editText.addTextChangedListener(this);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        database=FirebaseDatabase.getInstance();
        arrayListSpinner=new ArrayList<>();

        arrayListSpinner.add("카페");
        arrayListSpinner.add("중식");
        arrayListSpinner.add("한식");
        arrayListSpinner.add("국밥");
        arrayListSpinner.add("고깃집");
        arrayListSpinner.add("국수");
        arrayListSpinner.add("양식");
        arrayListSpinner.add("일식");
        arrayListSpinner.add("술집");
        arrayListSpinner.add("분식");

         spinner=v.findViewById(R.id.spinner);

         arrayAdapterSpinner=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayListSpinner);

         spinner.setAdapter(arrayAdapterSpinner);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position==0){
                     databaseReference=database.getReference("food/cafe");
                     databaseReference();
                 }
                 else if(position==1){
                     databaseReference=database.getReference("food/china");
                     databaseReference();
                 }
                 else if(position==2){
                     databaseReference=database.getReference("food/Hansik");
                     databaseReference();
                 }
                 else if(position==3){
                     databaseReference=database.getReference("food/gook");
                     databaseReference();
                 }
                 else if(position==4){
                     databaseReference=database.getReference("food/meat");
                     databaseReference();
                 }
                 else if(position==5){
                     databaseReference=database.getReference("food/noodle");
                     databaseReference();
                 }
                 else if(position==6){
                     databaseReference=database.getReference("food/pizza");
                     databaseReference();
                 }
                 else if(position==7){
                     databaseReference=database.getReference("food/sashimi");
                     databaseReference();
                 }
                 else if(position==8){
                     databaseReference=database.getReference("food/Sul");
                     databaseReference();
                 }
                 else if(position==9){
                     databaseReference=database.getReference("food/Boonsik");
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FoodItem foodItem=snapshot.getValue(FoodItem.class);
                    arrayList.add(foodItem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FoodFragment", String.valueOf(databaseError.toException()));
            }
        });
        adapter=new FoodAdapter(arrayList,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
