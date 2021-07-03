package com.example.aedificantes_calculateur_se_sol.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.LineProfilSolAdaptater;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving.FileExporter;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving.FileLoader;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater;
import com.example.aedificantes_calculateur_se_sol.R;
import com.example.aedificantes_calculateur_se_sol.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Activity générant l'ensemble des éléments affichable pour les paramètres necéssaire aux calculs
 */
public class HomeFragment extends Fragment  implements ResultUpdatable, ResultButtonLoader {

    private static final String LOG_TAG = "Frag_01";
    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;
    private static final int MY_WRITE_REQUEST_CODE_PERMISSION = 3000;
    private static final int MY_WRITE_RESULT_CODE_FILECHOOSER = 4000;

    private FragmentHomeBinding binding;
    private View globalContainer;

    private RelativeLayout layout_Const_Result;
    private LinearLayout global_LL_activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<ParamLayer> listParams =  new ArrayList<>();
    private ParamContainer paramContainer;

    public static ResultManager resultManager;
    private ScrewPileParamManager screwPileParamManager;
    private GroundWater groundWater;
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
        listParams.add(new ParamLayer(TypeSol.LOAM_SABLEUX, Granularite.FIN, Compacite.FRIABLE,0.85f,0.65f,0.5f,23f,1.6f,0f,1.55f));
        listParams.add(new ParamLayer(TypeSol.SABLEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0f,0f,0.5f,28f,2f,0.75f,5.4f));
        listParams.add(new ParamLayer(TypeSol.LIMONEUX, Granularite.MOYEN, Compacite.MOYEN_DENSE,0.65f,0.5f,0f,22f,1.8f,0f,8.5f));
        listParams.add(new ParamLayer(TypeSol.SABLEUX, Granularite.GRAVELEUX, Compacite.DENSE_SANS_SOND_ST,0f,0f,0f,30f,2f,0.25f,0f));
        //listParams.add(new ParamSol());

        verificator = new Verificator(this);
        screwPileParamManager = new ScrewPileParamManager(verificator, global_LL_activity);
        groundWater = new GroundWater(verificator,global_LL_activity);


        //generate all needed class
        paramContainer = new ParamContainer(this.listParams, this.screwPileParamManager, this.groundWater);
        errorDisplayer = new ErrorDisplayer(this.getActivity().getApplicationContext(), layout_Const_Result);
        goodValueDisplayer = new GoodValuesInterfaceDisplayer(this, layout_Const_Result);
        resultManager = new ResultManager(paramContainer.get_ParamContainerData());

        // place default params to test
        screwPileParamManager.setValues(new float[]{88.9f,250f,3000f,100f,2900f});

        //manage recyclerView
        mAdapter = new LineProfilSolAdaptater(this.getActivity().getApplicationContext(),listParams,verificator, screwPileParamManager);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //button that add line to recyclerView
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(listParams.size()-1);
                listParams.add(new ParamLayer());
                mAdapter.notifyItemInserted(listParams.size()-1);
                mRecyclerView.smoothScrollToPosition(listParams.size());
                allValuesAreNotSet();

            }
        });
        //launch result activity
        FAB_param.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchResultsView();
            }
        });

        return root;
    }

    //allow to write HTML title for parameter
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


    /**
     * @see ResultUpdatable
     */
    @Override
    public void allValuesAreSet() {
        System.out.println("SET CORRECTLY SO CALCULATE");
        errorDisplayer.hide();
        resultManager.updateData(paramContainer.get_ParamContainerData());
        //goodValueDisplayer.show();
        FAB_param.setVisibility(View.VISIBLE);
    }
    /**
     * @see ResultUpdatable
     */
    @Override
    public void allValuesAreNotSet() {
        System.out.println("NOT SET CORRECTLY SO PRINT ERROR");
        //goodValueDisplayer.hide();
        errorDisplayer.generateAndDisplay(paramContainer);
        FAB_param.setVisibility(View.INVISIBLE);
    }
    /**
     * @see ResultUpdatable
     */
    @Override
    public void updateCalculator() {
        resultManager.updateData(paramContainer.get_ParamContainerData());
    }
    /**
     * @see ResultButtonLoader
     */
    @Override
    public void launchResultsView() {
        //Intent intent = new Intent(this, DetailsActivity.class);
        Intent intent = new Intent(this.getContext(), GlobalResultActivity.class);
        /*
        intent.putExtra("listParamSolData",listOfDataParamSol() );
        intent.putExtra("pieuManagerData",pieuParamManager.generate_pieuParamData() );
         */
        intent.putExtra("paramContainerData",paramContainer.get_ParamContainerData() );
        this.startActivity(intent);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//place special menu for this page
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for(int i=0; i < menu.size(); i++){
            MenuItem item = menu.getItem(i);
            if(item.getTitle() == getString(R.string.action_loadFile)){
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d("subMenu","open of IMPORT menu from homeFragment");
                        askPermissionAndBrowseFile();
                        return false;
                    }
                });
            }
            if(item.getTitle() == getString(R.string.action_exportFile)){
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d(LOG_TAG,"open of EXPORT menu from homeFragment");
                        askPermissionWrite();
                        return false;
                    }
                });
            }
        }
    }

    //method used to launch file browser
    private void askPermissionAndBrowseFile()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
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

    //method used to launch file browser
    private void askPermissionWrite()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_WRITE_REQUEST_CODE_PERMISSION
                );
                return;
            }
        }
        FileExporter exporter = new FileExporter(paramContainer.get_ParamContainerData());
        Log.d(LOG_TAG, "string of json create:\n"+exporter.prettyPrint_JSON(exporter.generate()) );
        exporter.save();
    }

    //method used to launch file browser
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(getActivity().getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                    this.doBrowseFile();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG,"Permission denied!");
                    Toast.makeText(getActivity().getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case MY_WRITE_REQUEST_CODE_PERMISSION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(getActivity().getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();

                    FileExporter exporter = new FileExporter(paramContainer.get_ParamContainerData());
                    Log.d("MAINACTIVITY", "string of json create:\n"+exporter.prettyPrint_JSON(exporter.generate()) );
                    exporter.save();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG,"Permission denied!");
                    Toast.makeText(getActivity().getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    //Catch when the browse activity (generate by android) is close to get file
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_RESULT_CODE_FILECHOOSER:
                if (resultCode == Activity.RESULT_OK ) {
                    if(data != null)  {
                        Uri fileUri = data.getData();
                        Log.i(LOG_TAG, "Uri: " + fileUri.getPath()+"\n"+fileUri.toString());
                        Log.i(LOG_TAG, "Uri info: "
                        +"\n"+fileUri.getAuthority()
                                +"\n"+fileUri.getLastPathSegment()
                                +"\n"+fileUri.getQuery()
                                +"\n"+fileUri.getEncodedPath());
                        //fileUri.getLastPathSegment().split(":")[1]
                        FileLoader loader2 = new FileLoader(fileUri.getLastPathSegment().split(":")[1]);
                        ParamContainerData container = loader2.loadAndParse();
                        Log.i(LOG_TAG, "ParamContainer after Parse: \n"+container.toString());

                        listParams.clear();
                        for(ParamLayerData each : container.getSol_data_list()){
                            listParams.add(new ParamLayer(each));
                        }

                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(listParams.size());
                        screwPileParamManager.setValues(container.getScrewPileManagerData());
                        groundWater.setValues(container.getGroundWater_data());
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}