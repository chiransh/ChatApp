package com.freedoctorhelpline.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Brands extends AppCompatActivity {
    ArrayList brand_name=new ArrayList();
    ArrayList brand_icon=new ArrayList();
    RecyclerView recyclerView;
    RecyclerBrands mAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        recyclerView=(RecyclerView)findViewById(R.id.brands);
        brand_name.add("Rebbok");
        brand_name.add("Nike");
        mAdaptor=new RecyclerBrands(brand_name,brand_icon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdaptor);
    }
}
