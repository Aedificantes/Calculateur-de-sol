package com.example.aedificantes_calculateur_se_sol.GlobalResultFragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultDisplayer;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    private ConstraintLayout CL_result;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<ParamSolData> solData;
    private PieuManagerData pieuManagerData;
    // TODO: Rename and change types of parameters

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(ArrayList<ParamSolData> SolData, PieuManagerData pieuManagerData) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable("solData",SolData);
        args.putSerializable("pieuManagerData",pieuManagerData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.solData = (ArrayList<ParamSolData>) getArguments().getSerializable("solData");
            this.pieuManagerData = (PieuManagerData) getArguments().getSerializable("pieuManagerData");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        this.CL_result = view.findViewById(R.id.CL_result);
        ResultDisplayer displayer = new ResultDisplayer(CL_result);

        if(this.solData == null || this.pieuManagerData == null){
            Log.e("NullException", "soldata ou pieumanager are null");
        }

        ResultManager resultManager = new ResultManager(this.solData,this.pieuManagerData);
        displayer.updateData(resultManager);
        displayer.show();

        return view;
    }
}