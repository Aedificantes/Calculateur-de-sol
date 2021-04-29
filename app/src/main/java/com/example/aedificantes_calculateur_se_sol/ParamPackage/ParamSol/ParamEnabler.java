package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

public class ParamEnabler {
    private LineProfilSolAdaptater.LineProfilSolViewHolder holder;
    private ParamSol paramSol;
    private Boolean isLast = true;

    public ParamEnabler(ParamSol current) {
        paramSol = current;

    }

    public void manage(LineProfilSolAdaptater.LineProfilSolViewHolder holder) {
        this.holder = holder;
        this.paramSol = paramSol;
        enableElement();
    }

    public void update(){
        enableElement();
    }

    private void enableElement(){

        if (paramSol.getTypeSol() == TypeSol.SABLEUX) {
            holder.SP_Granularite.setEnabled(true);
            holder.SP_Compacite.setEnabled(true);
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

        }

    public void setLast(Boolean last) {
        isLast = last;
    }
}
