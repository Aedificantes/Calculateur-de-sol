package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.aedificantes_calculateur_se_sol.GlobalResultFragments.GlobalResultPageAdaptater;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity permettant d'afficher différents onglets de résultats sous forme de fragment (tableau, detail, dessin)
 */
public class GlobalResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_result);
        configureViewPagerAndTabs();
    }

    private void configureViewPagerAndTabs(){


        ParamContainerData paramContainerData =  (ParamContainerData) getIntent().getSerializableExtra("paramContainerData");

        //Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new GlobalResultPageAdaptater(getSupportFragmentManager(), paramContainerData));
        // 1 - Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);

        tabs.getTabAt(0).setIcon(R.drawable.ic_cells);
        tabs.getTabAt(1).setIcon(R.drawable.ic_plan);
        tabs.getTabAt(2).setIcon(R.drawable.ic_loupe);

    }
}