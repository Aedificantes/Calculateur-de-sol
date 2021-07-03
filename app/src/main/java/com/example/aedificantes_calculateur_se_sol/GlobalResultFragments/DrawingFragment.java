package com.example.aedificantes_calculateur_se_sol.GlobalResultFragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.example.aedificantes_calculateur_se_sol.R;
import com.example.aedificantes_calculateur_se_sol.Schema.LayerDrawer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawingFragment extends Fragment {


    private ConstraintLayout CL_ConstraintLayout;

    private ParamContainerData paramContainerData;


    public DrawingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrawingFragment.
     */
    public static DrawingFragment newInstance(ParamContainerData data) {
        DrawingFragment fragment = new DrawingFragment();
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
        View inflateView = inflater.inflate(R.layout.fragment_drawing, container, false);
        CL_ConstraintLayout = inflateView.findViewById(R.id.CL_Frag_drawing);

        ResultManager calculator = new ResultManager(paramContainerData);


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap bg = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        //dessin du sol fixe
        LayerDrawer layerDrawer = new LayerDrawer(paramContainerData, canvas, getActivity().getWindowManager());

        layerDrawer.toDrawDrawing(0.70f,0.20f,0.05f);
        layerDrawer.toDrawChart(0.10f,0.40f,0.05f);


        CL_ConstraintLayout.setBackground(new BitmapDrawable(bg));

        return inflateView;
    }
}