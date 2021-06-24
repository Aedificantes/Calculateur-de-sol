package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aedificantes_calculateur_se_sol.Calculator.GoodValuesInterfaceDisplayer;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultButtonLoader;
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

import java.util.ArrayList;
/*
public class MainActivity extends AppCompatActivity implements ResultUpdatable, ResultButtonLoader {

    public static Context MAIN_CONTEXT;

    private RelativeLayout layout_Const_Result;
    private LinearLayout global_LL_activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<ParamSol> listParams =  new ArrayList<>();

    public static ResultManager resultManager;
    private PieuParamManager pieuParamManager;
    private ErrorDisplayer errorDisplayer;
    private GoodValuesInterfaceDisplayer goodValueDisplayer;
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

        //generate title of each param for param sol recyclerView
        setTitle();


        // place default params to test
        listParams.add(new ParamSol(TypeSol.LOAM_SABLEUX, Granularite.FIN, Compacite.FRIABLE,0.85f,0.65f,0.5f,23f,1.6f,0f,1.55f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0f,0f,0.5f,28f,2f,0.75f,5.4f));
        listParams.add(new ParamSol(TypeSol.LIMONEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0.65f,0.5f,0f,22f,1.8f,0f,8.5f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.GRAVELEUX, Compacite.DENSE_SANS_SOND_ST,0f,0f,0f,30f,2f,0.25f,0f));
        //listParams.add(new ParamSol());

        //generate all needed class
        errorDisplayer = new ErrorDisplayer(this, layout_Const_Result);
        goodValueDisplayer = new GoodValuesInterfaceDisplayer(this, layout_Const_Result);
        verificator = new Verificator(this);
        pieuParamManager = new PieuParamManager(verificator, global_LL_activity);
        resultManager = new ResultManager(listOfDataParamSol(),pieuParamManager.generate_pieuParamData());

        //place default value for PieuParamManager
        pieuParamManager.setValues(new float[]{88.9f,250f,3000f,100f,2900f});

        mAdapter = new LineProfilSolAdaptater(listParams,verificator, pieuParamManager);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(listParams.size()-1);
                listParams.add(new ParamSol());
                mAdapter.notifyItemInserted(listParams.size()-1);
                mRecyclerView.smoothScrollToPosition(listParams.size());
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



    private void setTitle(){
        ArrayList<TextView> title_list =new ArrayList<>();
        title_list.add(findViewById(R.id.TV_title_P1));
        title_list.add(findViewById(R.id.TV_title_P2));
        title_list.add(findViewById(R.id.TV_title_P3));
        title_list.add(findViewById(R.id.TV_title_P4));
        title_list.add(findViewById(R.id.TV_title_P5));
        title_list.add(findViewById(R.id.TV_title_P6));
        title_list.add(findViewById(R.id.TV_title_P7));

        title_list.get(0).setText(Html.fromHtml("I<sub>l</sub>"));
        setHoverListener(title_list.get(0),"Facteur de plasticité des sols argileux");
        title_list.get(1).setText(Html.fromHtml("e"));
        setHoverListener(title_list.get(1),"Coefficient de porosité - indices des vides");
        title_list.get(2).setText(Html.fromHtml("c,T/m<sup>2</sup>"));
        setHoverListener(title_list.get(2),"Cohésion du sol");
        title_list.get(3).setText(Html.fromHtml("φ"));
        setHoverListener(title_list.get(3),"Angle de frottement interne de l'horizon");
        title_list.get(4).setText(Html.fromHtml("γ,T/m<sup>3</sup>"));
        setHoverListener(title_list.get(4),"Masse volumique de l'horizon");
        title_list.get(5).setText(Html.fromHtml("S<sub>r</sub>"));
        setHoverListener(title_list.get(5),"Indice d'humidité");
        title_list.get(6).setText(Html.fromHtml("h,m"));
        setHoverListener(title_list.get(5),"Epaisseur de l'horizon");

    }

    private void setHoverListener(View view, String text){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast notif = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                notif.setGravity(Gravity.LEFT,0,0);
                notif.show();
            }
        });
    }



    @Override
    public void allValuesAreSet() {
        System.out.println("SET CORRECTLY SO CALCULATE");
        errorDisplayer.hide();
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
        //resultDisplayer.updateData(resultManager);
        goodValueDisplayer.show();
    }
    @Override
    public void allValuesAreNotSet() {
        System.out.println("NOT SET CORRECTLY SO PRINT ERROR");
        goodValueDisplayer.hide();
        errorDisplayer.generateAndDisplay(listParams,pieuParamManager);
    }

    @Override
    public void updateCalculator() {
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
    }

    @Override
    public void launchResultsView() {
        //Intent intent = new Intent(this, DetailsActivity.class);
        Intent intent = new Intent(this, GlobalResultActivity.class);
        intent.putExtra("listParamSolData",listOfDataParamSol() );
        intent.putExtra("pieuManagerData",pieuParamManager.generate_pieuParamData() );
        this.startActivity(intent);
    }
}


*/