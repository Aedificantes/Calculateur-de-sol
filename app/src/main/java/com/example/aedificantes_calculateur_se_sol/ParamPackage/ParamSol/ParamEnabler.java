package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import com.example.aedificantes_calculateur_se_sol.MainActivity;

public class ParamEnabler {
    private LineProfilSolAdaptater.LineProfilSolViewHolder holder;
    private ParamSol paramSol;
    private Boolean isLast = true;

    public ParamEnabler(ParamSol current) {
        paramSol = current;
    }

    public void manage(LineProfilSolAdaptater.LineProfilSolViewHolder holder) {
        this.holder = holder;
        enableElement();
    }

    public void update(){
        enableElement();
    }

    private void enableElement(){

        if (paramSol.getTypeSol() == TypeSol.SABLEUX) {
            holder.SP_Granularite.setEnabled(true);
            holder.SP_Compacite.setEnabled(true);
            set_ET_Enable(3,4,5);
        } else {
            holder.SP_Granularite.setEnabled(false);
            holder.SP_Compacite.setEnabled(false);
            switch (paramSol.getTypeSol()){
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
        this.paramSol.setParamToSet(toEnableETIndex);
        for(int each_ET_index : toEnableETIndex){
            holder.get_ET_index(each_ET_index).setEnabled(true);
        }
        holder.get_ET_index(6).setEnabled(!isLast);
        if(!isLast){ this.paramSol.addParamToSet(6); }
        holder.get_ET_index(2).setEnabled(paramSol.isLoadLayer());
        if(paramSol.isLoadLayer()){ this.paramSol.addParamToSet(2); }
    }

    public void setLast(Boolean last) {
        isLast = last;
    }
}
