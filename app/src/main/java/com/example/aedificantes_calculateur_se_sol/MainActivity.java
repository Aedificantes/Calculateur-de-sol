package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultUpdatable;
import com.example.aedificantes_calculateur_se_sol.Error.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorDisplayer;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorListAdpatator;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.ParamSol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResultUpdatable {

    public static Context MAIN_CONTEXT;

    private ConstraintLayout layout_Const_Result;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<ParamSol> listParams =  new ArrayList<>();

    ResultManager resultManager = new ResultManager();
    ErrorDisplayer errorDisplayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.BT_add);
        layout_Const_Result = findViewById(R.id.Layout_Const_Result);
        mRecyclerView = findViewById(R.id.RV_params_sol);

        MAIN_CONTEXT = this;

        errorDisplayer = new ErrorDisplayer(this, layout_Const_Result);

        listParams.add(new ParamSol());
        mAdapter = new LineProfilSolAdaptater(listParams,this);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listParams.add(new ParamSol());
                mAdapter.notifyItemInserted(listParams.size()-1);
                allValuesAreNotSet();
            }
        });






    }

    @Override
    public void allValuesAreSet() {
        System.out.println("SET CORRECTLY SO CALCULATE");
        errorDisplayer.hide();
    }
    @Override
    public void allValuesAreNotSet() {
        System.out.println("NOT SET CORRECTLY SO PRINT ERROR");
        errorDisplayer.generateAndDisplay(listParams);
    }
}