package com.example.aedificantes_calculateur_se_sol.GlobalResultFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class GlobalResultPageAdaptater extends FragmentPagerAdapter {
    public GlobalResultPageAdaptater(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return ResultFragment.newInstance();
            case 1: //Page number 2
                return DrawingFragment.newInstance();
            case 2: //Page number 3
                return DetailFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Tableau";
            case 1: //Page number 2
                return "Schéma";
            case 2: //Page number 3
                return "Details";
            default:
                return null;
        }
    }
}
