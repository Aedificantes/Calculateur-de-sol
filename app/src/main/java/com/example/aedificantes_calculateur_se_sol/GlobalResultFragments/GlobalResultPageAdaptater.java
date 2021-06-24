package com.example.aedificantes_calculateur_se_sol.GlobalResultFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GlobalResultPageAdaptater extends FragmentPagerAdapter {
    private ArrayList<ParamSolData> solData;
    private PieuManagerData pieuManagerData;
    public GlobalResultPageAdaptater(@NonNull @NotNull FragmentManager fm, ArrayList<ParamSolData> SolData, PieuManagerData pieuManagerData) {
        super(fm);
        this.solData = SolData;
        this.pieuManagerData =pieuManagerData;
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return ResultFragment.newInstance(solData,pieuManagerData);
            case 1: //Page number 2
                return DrawingFragment.newInstance(solData,pieuManagerData);
            case 2: //Page number 3
                return DetailFragment.newInstance(solData,pieuManagerData);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Tableaux";
            case 1: //Page number 2
                return "Schémas";
            case 2: //Page number 3
                return "Details";
            default:
                return null;
        }
    }
}
