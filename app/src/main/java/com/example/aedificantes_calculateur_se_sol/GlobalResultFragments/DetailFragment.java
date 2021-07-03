package com.example.aedificantes_calculateur_se_sol.GlobalResultFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aedificantes_calculateur_se_sol.DetailTabActivity;
import com.example.aedificantes_calculateur_se_sol.Details.DetailsDisplayer;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabLauncher;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.example.aedificantes_calculateur_se_sol.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements DetailTabLauncher {

    private ConstraintLayout CL_ConstraintLayout;

    private ParamContainerData paramContainerData;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(ParamContainerData data) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("paramContainerData",data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.paramContainerData = (ParamContainerData) getArguments().getSerializable("paramContainerData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflateView = inflater.inflate(R.layout.fragment_detail, container, false);

        CL_ConstraintLayout = inflateView.findViewById(R.id.CL_Frag_detail);
        DetailsDisplayer displayer = new DetailsDisplayer(this.getContext(),CL_ConstraintLayout, this);
        displayer.generateAndDisplay(paramContainerData);

        return inflateView;
    }

    @Override
    public void openTabLayout(TabBlockManager tabManager) {
        //TODO Change DetailTabDrawer by a TabManager

        Intent intent = new Intent(this.getActivity(), DetailTabActivity.class);
        intent.putExtra("tabManager",tabManager );
        this.startActivity(intent);

    }
}