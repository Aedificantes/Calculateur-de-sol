package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import android.util.Log;

/**
 * Class that manage parameter activation in ParamLayer, element to activate are give in a list os value in ParamLayerData.
 * ParamLayer then read this list and know what index of EditTextList need to be activate
 */
public class ParamEnabler {
    private LineProfilSolAdaptater.LineProfilSolViewHolder holder;
    private ParamLayer paramLayer;
    private Boolean isLast = true;

    public ParamEnabler(ParamLayer current) {
        paramLayer = current;
    }

    public void manage(LineProfilSolAdaptater.LineProfilSolViewHolder holder) {
        this.holder = holder;
        enableElement();
    }

    /**
     * notify value as change params to be enable need to be reload
     */
    public void update(){
        if(holder != null) {
            enableElement();
        }else{
            Log.d("ParamEnabler","if  holder is  null update is  cancel because object isn't visible");
        }
    }

    /**
     * Tree of decision to find params to enable
     */
    private void enableElement(){

        if (paramLayer.getTypeSol() == TypeSol.SABLEUX) {
            holder.SP_Granularite.setEnabled(true);
            holder.SP_Compacite.setEnabled(true);
            set_ET_Enable(3,4,5);
        } else {
            holder.SP_Granularite.setEnabled(false);
            holder.SP_Compacite.setEnabled(false);
            switch (paramLayer.getTypeSol()){
                case REMBLAI:
                    set_ET_Enable(4);
                    break;
                case ARGILEUX:
                case LOAM_SABLEUX:
                case LIMONEUX:
                    set_ET_Enable(0,1,3,4);
                    break;
            }

        }
    }

    private void set_ET_Enable(int... toEnableETIndex){
        holder.set_All_ET_Desable();
        this.paramLayer.setParamToSet(toEnableETIndex);
        for(int each_ET_index : toEnableETIndex){
            holder.get_ET_index(each_ET_index).setEnabled(true);
        }
        holder.get_ET_index(6).setEnabled(!isLast);
        if(!isLast){ this.paramLayer.addParamToSet(6); }
        holder.get_ET_index(2).setEnabled(paramLayer.isLoadLayer());
        if(paramLayer.isLoadLayer()){ this.paramLayer.addParamToSet(2); }
    }

    public void setLast(Boolean last) {
        isLast = last;
    }
}
