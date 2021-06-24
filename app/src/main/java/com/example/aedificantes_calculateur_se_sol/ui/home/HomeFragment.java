package com.example.aedificantes_calculateur_se_sol.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.aedificantes_calculateur_se_sol.Calculator.GoodValuesInterfaceDisplayer;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultButtonLoader;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultUpdatable;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorDisplayer;
import com.example.aedificantes_calculateur_se_sol.Error.Verificator;
import com.example.aedificantes_calculateur_se_sol.GlobalResultActivity;
import com.example.aedificantes_calculateur_se_sol.MainNavigationActivity;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.LineProfilSolAdaptater;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving.FileExporter;
import com.example.aedificantes_calculateur_se_sol.R;
import com.example.aedificantes_calculateur_se_sol.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment  implements ResultUpdatable, ResultButtonLoader {

    private static final String LOG_TAG = "Frag_01";
    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;

    private FragmentHomeBinding binding;
    private View globalContainer;

    private RelativeLayout layout_Const_Result;
    private LinearLayout global_LL_activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<ParamSol> listParams =  new ArrayList<>();
    private ParamContainer paramContainer;

    public static ResultManager resultManager;
    private PieuParamManager pieuParamManager;
    private ErrorDisplayer errorDisplayer;
    private GoodValuesInterfaceDisplayer goodValueDisplayer;
    private Verificator verificator;

    private FloatingActionButton FAB_param;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.globalContainer = root;

        setHasOptionsMenu(true);


        global_LL_activity = globalContainer.findViewById(R.id.LL_main_activity);
        Button bt = globalContainer.findViewById(R.id.BT_add);
        layout_Const_Result = globalContainer.findViewById(R.id.Layout_Const_Result);
        mRecyclerView = globalContainer.findViewById(R.id.RV_params_sol);


        //generate title of each param for param sol recyclerView
        setTitle();

        FAB_param = globalContainer.findViewById(R.id.FAB_param);


        // place default params to test
        listParams.add(new ParamSol(TypeSol.LOAM_SABLEUX, Granularite.FIN, Compacite.FRIABLE,0.85f,0.65f,0.5f,23f,1.6f,0f,1.55f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0f,0f,0.5f,28f,2f,0.75f,5.4f));
        listParams.add(new ParamSol(TypeSol.LIMONEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0.65f,0.5f,0f,22f,1.8f,0f,8.5f));
        listParams.add(new ParamSol(TypeSol.SABLEUX, Granularite.GRAVELEUX, Compacite.DENSE_SANS_SOND_ST,0f,0f,0f,30f,2f,0.25f,0f));
        //listParams.add(new ParamSol());

        //generate all needed class
        errorDisplayer = new ErrorDisplayer(this.getActivity().getApplicationContext(), layout_Const_Result);
        goodValueDisplayer = new GoodValuesInterfaceDisplayer(this, layout_Const_Result);
        verificator = new Verificator(this);
        pieuParamManager = new PieuParamManager(verificator, global_LL_activity);
        resultManager = new ResultManager(listOfDataParamSol(),pieuParamManager.generate_pieuParamData());
        paramContainer = new ParamContainer(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());

        //place default value for PieuParamManager
        pieuParamManager.setValues(new float[]{88.9f,250f,3000f,100f,2900f});

    mAdapter = new LineProfilSolAdaptater(this.getActivity().getApplicationContext(),listParams,verificator, pieuParamManager);

        mLayoutManager = new LinearLayoutManager(this.getContext());

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

        FAB_param.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchResultsView();
            }
        });

        return root;
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
        title_list.add(globalContainer.findViewById(R.id.TV_title_P1));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P2));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P3));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P4));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P5));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P6));
        title_list.add(globalContainer.findViewById(R.id.TV_title_P7));

        title_list.get(0).setText(Html.fromHtml("I<sub>l</sub>"));
        setTitleListener_displayText(title_list.get(0),"Facteur de plasticité des sols argileux");
        title_list.get(1).setText(Html.fromHtml("e"));
        setTitleListener_displayText(title_list.get(1),"Coefficient de porosité - indices des vides");
        title_list.get(2).setText(Html.fromHtml("c,T/m<sup>2</sup>"));
        setTitleListener_displayText(title_list.get(2),"Cohésion du sol");
        title_list.get(3).setText(Html.fromHtml("φ"));
        setTitleListener_displayText(title_list.get(3),"Angle de frottement interne de l'horizon");
        title_list.get(4).setText(Html.fromHtml("γ,T/m<sup>3</sup>"));
        setTitleListener_displayText(title_list.get(4),"Masse volumique de l'horizon");
        title_list.get(5).setText(Html.fromHtml("S<sub>r</sub>"));
        setTitleListener_displayText(title_list.get(5),"Indice d'humidité");
        title_list.get(6).setText(Html.fromHtml("h,m"));
        setTitleListener_displayText(title_list.get(5),"Epaisseur de l'horizon");

        title_list.clear();

        title_list.add(globalContainer.findViewById(R.id.TV_title_pieu_dfut));
        title_list.add(globalContainer.findViewById(R.id.TV_title_pieu_dhel));
        title_list.add(globalContainer.findViewById(R.id.TV_title_pieu_ip));
        title_list.add(globalContainer.findViewById(R.id.TV_title_pieu_Hk));
        title_list.add(globalContainer.findViewById(R.id.TV_title_pieu_H));

        title_list.get(0).setText(Html.fromHtml("D<sub>fut</sub>"));
        setTitleListener_displayImage(title_list.get(0),R.drawable.pieu_schema_dfut);
        title_list.get(1).setText(Html.fromHtml("D<sub>hel</sub>"));
        setTitleListener_displayImage(title_list.get(1),R.drawable.pieu_schema_dhel);
        title_list.get(2).setText(Html.fromHtml("I<sub>p</sub>"));
        setTitleListener_displayImage(title_list.get(2),R.drawable.pieu_schema_ip);
        title_list.get(3).setText(Html.fromHtml("H<sub>k</sub>"));
        setTitleListener_displayImage(title_list.get(3),R.drawable.pieu_schema_hk);
        title_list.get(4).setText(Html.fromHtml("H"));
        setTitleListener_displayImage(title_list.get(4),R.drawable.pieu_schema_h);


    }

    private void setTitleListener_displayText(View view, String text){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast notif = Toast.makeText(getActivity().getBaseContext(), text,Toast.LENGTH_SHORT);
                //notif.setGravity(Gravity.LEFT,0,0);
                notif.show();

            }
        });
    }

    private void setTitleListener_displayImage(View view, int ressource){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePopup imagePopup = new ImagePopup(getActivity());
                imagePopup.initiatePopup(getResources().getDrawable(ressource));
                imagePopup.viewPopup();
               // Log.d("setTitleListener_displayImage",);
            }
        });
    }



    @Override
    public void allValuesAreSet() {
        System.out.println("SET CORRECTLY SO CALCULATE");
        errorDisplayer.hide();
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
        //goodValueDisplayer.show();
        FAB_param.setVisibility(View.VISIBLE);
    }
    @Override
    public void allValuesAreNotSet() {
        System.out.println("NOT SET CORRECTLY SO PRINT ERROR");
        //goodValueDisplayer.hide();
        errorDisplayer.generateAndDisplay(listParams,pieuParamManager);
        FAB_param.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateCalculator() {
        resultManager.updateData(listOfDataParamSol(), pieuParamManager.generate_pieuParamData());
    }

    @Override
    public void launchResultsView() {
        //Intent intent = new Intent(this, DetailsActivity.class);
        Intent intent = new Intent(this.getContext(), GlobalResultActivity.class);
        intent.putExtra("listParamSolData",listOfDataParamSol() );
        intent.putExtra("pieuManagerData",pieuParamManager.generate_pieuParamData() );
        this.startActivity(intent);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for(int i=0; i < menu.size(); i++){
            MenuItem item = menu.getItem(i);
            if(item.getTitle() == getString(R.string.action_loadFile)){
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d("subMenu","open of first sub menu from homeFragment");
/*
                final Dialog fbDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                Window window = fbDialogue.getWindow();
                window.setGravity(Gravity.CENTER);
                //window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));


                fbDialogue.setContentView(R.layout.file_chooser);
                fbDialogue.setCancelable(true);
                fbDialogue.show();

 */
                        askPermissionAndBrowseFile();
                        return false;
                    }
                });
            }
            if(item.getTitle() == getString(R.string.action_exportFile)){
                FileExporter fileExp = new FileExporter(paramContainer);
                fileExp.generate();
            }
        }
    }

    private void askPermissionAndBrowseFile()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(this.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_REQUEST_CODE_PERMISSION
                );
                return;
            }
        }
        this.doBrowseFile();
    }

    private void doBrowseFile()  {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
    }

    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_REQUEST_CODE_PERMISSION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this.getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();

                    this.doBrowseFile();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG,"Permission denied!");
                    Toast.makeText(this.getContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_RESULT_CODE_FILECHOOSER:
                if (resultCode == Activity.RESULT_OK ) {
                    if(data != null)  {
                        Uri fileUri = data.getData();
                        Log.i(LOG_TAG, "Uri: " + fileUri);

                        String filePath = null;
                        try {
                           // filePath = FileUtils.getPath(this.getContext(),fileUri);
                        } catch (Exception e) {
                            Log.e(LOG_TAG,"Error: " + e);
                            Toast.makeText(this.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                        //this.editTextPath.setText(filePath);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}