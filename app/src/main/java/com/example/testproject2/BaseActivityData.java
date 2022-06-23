package com.example.testproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaseActivityData extends AppCompatActivity {
RecyclerView rv;

Button btn;
TextView txt;
RecyclerView rv1;
RbAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_data);
        //rv=findViewById(R.id.RVDATA);
        btn=findViewById(R.id.btn1);
        rv1= findViewById(R.id.recycleview);
        final ArrayList<Data> dta=new ArrayList<>();

        //dta = (ArrayList<Data>) getIntent().getSerializableExtra("m");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dta.clear();
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    dta.add(snapshot1.getValue(Data.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new RbAdapter(BaseActivityData.this, dta);
                RecyclerView.LayoutManager lm =new LinearLayoutManager(BaseActivityData.this);
                rv1.setLayoutManager(lm);
                rv1.setAdapter(adapter);
            }
        });
    }
}