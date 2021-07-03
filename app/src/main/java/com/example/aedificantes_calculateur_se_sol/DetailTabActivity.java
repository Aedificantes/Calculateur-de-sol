package com.example.aedificantes_calculateur_se_sol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabDrawer;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;

/**
 * Activity qui s'affiche lorsque l'on clique sur une des lignes du fragment détail qui se base sur un tableau de référence.
 * Ce tableau est construit dynamiquement.
 */
public class DetailTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tab);

        TabBlockManager tabManager =  (TabBlockManager) getIntent().getSerializableExtra("tabManager");

        GridLayout gridLayout = findViewById(R.id.GL_TAB_detail);
        DetailTabDrawer drawer = new DetailTabDrawer(tabManager);
        drawer.toDrawGridLayout(gridLayout);
    }
}