package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.aedificantes_calculateur_se_sol.Details.DetailsDisplayer;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabLauncher;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements DetailTabLauncher {

    private ConstraintLayout CL_ConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        CL_ConstraintLayout = findViewById(R.id.CL_detailsMain);

        ArrayList<ParamSolData> listParamSolData =  (ArrayList<ParamSolData>)getIntent().getSerializableExtra("listParamSolData");
        PieuManagerData pieuManagerData =  (PieuManagerData) getIntent().getSerializableExtra("pieuManagerData");
        //DetailResultManager calculator = new DetailResultManager(listParamSolData,pieuManagerData);
        DetailsDisplayer displayer = new DetailsDisplayer(this,CL_ConstraintLayout, this);

        displayer.generateAndDisplay(listParamSolData,pieuManagerData);




    }

    @Override
    public void openTabLayout(TabBlockManager tabManager) {
        //TODO Change DetailTabDrawer by a TabManager
        Intent intent = new Intent(this, DetailTabActivity.class);
        intent.putExtra("tabManager",tabManager );
        this.startActivity(intent);
    }
}