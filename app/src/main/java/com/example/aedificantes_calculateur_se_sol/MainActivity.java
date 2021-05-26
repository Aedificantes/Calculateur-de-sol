package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.aedificantes_calculateur_se_sol.Calculator.DetailResultManager;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultButtonLoader;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultDisplayer;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultUpdatable;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorDisplayer;
import com.example.aedificantes_calculateur_se_sol.Error.Verificator;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.LineProfilSolAdaptater;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResultUpdatable, ResultButtonLoader {

    public static Context MAIN_CONTEXT;

    private ConstraintLayout layout_Const_Result;
    private LinearLayout global_LL_activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<ParamSol> listParams =  new ArrayList<>();

    public static ResultManager resultManager;
    private PieuParamManager pieuParamManager;
    private ErrorDisplayer errorDisplayer;
    private ResultDisplayer resultDisplayer;
    private Verificator verificator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global_LL_activity = findViewById(R.id.LL_main_activity);
        Button bt = findViewById(R.id.BT_add);
        layout_Const_Result = findViewById(R.id.Layout_Const_Result);
        mRecyclerView = findViewById(R.id.RV_params_sol);

        MAIN_CONTEXT = this;

        listParams.add(new ParamSol(TypeSol.LOAM_SABLEUX, Granularite.FIN, Compacite.FRIABLE,0.85f,0.65f,0f,23f,1.6f,0f,1.55f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0f,0f,0.5f,28f,2f,0.75f,5.4f));
        listParams.add(new ParamSol(TypeSol.LIMONEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0.65f,0.5f,0f,22f,1.8f,0f,8.5f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.GRAVELEUX, Compacite.DENSE_SANS_SOND_ST,0f,0f,0f,30f,2f,0.25f,0f));
        //listParams.add(new ParamSol());

        errorDisplayer = new ErrorDisplayer(this, layout_Const_Result);
        resultDisplayer = new ResultDisplayer(this, layout_Const_Result);
        verificator = new Verificator(this);
        pieuParamManager = new PieuParamManager(verificator, global_LL_activity);
        resultManager = new ResultManager(listOfDataParamSol(),pieuParamManager.generate_pieuParamData());

        pieuParamManager.setValues(new float[]{88.9f,250f,3000f,100f,2900f});

        mAdapter = new LineProfilSolAdaptater(listParams,verificator, pieuParamManager);

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

    private ArrayList<ParamSolData> listOfDataParamSol(){
        ArrayList<ParamSolData> listDataParamSol = new ArrayList<>();
        for(ParamSol each : listParams){
            listDataParamSol.add(each.getData());
        }
        return listDataParamSol;
    }


    @Override
    public void allValuesAreSet() {
        System.out.println("SET CORRECTLY SO CALCULATE");
        errorDisplayer.hide();
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
        resultDisplayer.updateData(resultManager);
        resultDisplayer.show();
    }
    @Override
    public void allValuesAreNotSet() {
        System.out.println("NOT SET CORRECTLY SO PRINT ERROR");
        resultDisplayer.hide();
        errorDisplayer.generateAndDisplay(listParams,pieuParamManager);
    }

    @Override
    public void updateCalculator() {
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
    }

    @Override
    public void launchDrawing() {
        Intent intent = new Intent(this, CanvaActivty.class);
        intent.putExtra("listParamSolData",listOfDataParamSol() );
        intent.putExtra("pieuManagerData",pieuParamManager.generate_pieuParamData() );
         this.startActivity(intent);
    }

    @Override
    public void launchDetail() {
        //Intent intent = new Intent(this, DetailsActivity.class);
        Intent intent = new Intent(this, GlobalResultActivity.class);
        intent.putExtra("listParamSolData",listOfDataParamSol() );
        intent.putExtra("pieuManagerData",pieuParamManager.generate_pieuParamData() );
        this.startActivity(intent);
    }
}