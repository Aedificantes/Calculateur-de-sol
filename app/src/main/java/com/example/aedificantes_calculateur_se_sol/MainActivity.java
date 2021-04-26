package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.ParamSol;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Context MAIN_CONTEXT;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAIN_CONTEXT = this;

        mRecyclerView = findViewById(R.id.RV_params_sol);
        ArrayList<ParamSol> listParams =  new ArrayList<>();
        listParams.add(new ParamSol());

        mAdapter = new LineProfilSolAdaptater(listParams);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}