package com.example.aedificantes_calculateur_se_sol.Calculator;

import android.text.Html;
import android.text.Spanned;

import com.example.aedificantes_calculateur_se_sol.Details.Detail;
import com.example.aedificantes_calculateur_se_sol.Details.DetailTitle;
import com.example.aedificantes_calculateur_se_sol.Error.Error;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DetailResultManager extends ResultManager{
    public DetailResultManager(List<ParamSolData> paramSolDataList, PieuManagerData pieuParamManagerData) {
        super(paramSolDataList, pieuParamManagerData);
    }

    public Detail detail_capacite_portante(){
        Detail detail= new Detail("La capacité portante est calculée suivant la formule");
        detail.add_ln_String("<b>F<sub>d</sub> = γ<sub>c</sub> · [ F<sub>d0</sub> + F<sub>df</sub> ]</b>");
        return detail;
    }
    public Detail detail_capacite_portante_helice(){
        Detail detail= new Detail("La capacité portante de la partie hélice du pieu vissé se calcule suivant la formule\n");
        detail.add_ln_String("<b>F<sub>d0</sub> = ( a<sub>1</sub> · c<sub>1</sub> + a<sub>2</sub> · γ<sub>1</sub> · h<sub>1</sub> ) · A</b>");
        return detail;
    }
    public Detail detail_capacite_portante_fut(){
        Detail detail= new Detail("Pour les charges de compression");
        detail.add_ln_String("<b>F<sub>df</sub> = u · f<sub>i</sub> · ( h - d<sub>hel</sub> )</b>");
        return detail;
    }
    public Detail detail_Acomp(){
        Detail detail= new Detail("Pour les charges de compression");
        detail.add_ln_String("<b>A<sub>comp</sub></b> = π · ( d<sub>hel</sub> /2 )<sup>2</sup> =");
        detail.add_String(" π · ( "+getPieuParamManagerData().Dhel_val()+"/2 )<sup>2</sup> =");
        detail.add_String(" "+fd0_Acomp()*1000000+" mm<sup>2</sup> = <b>"+fd0_Acomp()+"</b> m<sup>2</sup>");

        return detail;
    }
    public Detail detail_Atrac(){
        Detail detail= new Detail("Pour les charges de traction");
        detail.add_ln_String("<b>A<sub>tr</sub></b> = π · ( d<sub>hel</sub> / 2 )<sup>2</sup> - π · ( d<sub>fut</sub> / 2 )<sup>2</sup> =");
        detail.add_String(" π · ( "+getPieuParamManagerData().Dhel_val()+"/2 )<sup>2</sup> - π · ("+getPieuParamManagerData().Dfut_val()+"/2 )<sup>2</sup>=");
        detail.add_String(" "+fd0_ATrac()*1000000+" mm<sup>2</sup> = <b>"+fd0_ATrac()+"</b> m<sup>2</sup>");

        return detail;
    }
    public Detail detail_perimetre_section_transfersale_fut(){
        Detail detail= new Detail("<b>u</b> = π · d<sub>fut</sub> =");
        detail.add_String(" π · "+getPieuParamManagerData().Dfut_val()+" = "+perimetre_section_transfersale_fut()+" mm =");
        detail.add_String(" <b>"+perimetre_section_transfersale_fut_toDisplay()+"</b> m");

        return detail;
    }
    public Detail detail_profondeur_enfoncemement(){
        Detail detail= new Detail("profondeur enfoncement <b>h<sub>1</sub></b> =");
        detail.add_String(" H + h<sub>k</sub> =");
        detail.add_String(" "+getPieuParamManagerData().H_val()+" + "+getPieuParamManagerData().Hk_val()+" =");
        detail.add_String(" "+getLayerCalculator().profondeurPieu()*1000+"mm = <b>"+getLayerCalculator().profondeurPieu()+"</b> m");

        return detail;
    }
    public Detail detail_Longueur_fut_enfoncement_sol(){
        Detail detail= new Detail("Longueur du fut enfoncé dans le sol <b>h</b> = H =");
        detail.add_String(" "+getPieuParamManagerData().H_val()+"mm = <b>"+getPieuParamManagerData().H_val()/1000+"</b> m");

        return detail;
    }
    public Detail detail_alpha(){

        Detail detail= new Detail(alpha_detail_tab());
        detail.add_String("Les coefficients α pour horizon du sol "+getLayerCalculator().index_couchePortante()+" pour tout type de charge:");
        detail.add_ln_String("<b>α<sub>1</sub></b> = <b>"+getAlpha1()+"</b>");
        detail.add_ln_String("<b>α<sub>2</sub></b> = <b>"+getAlpha2()+"</b>");
        detail.add_ln_String("les coefficients sont définies par le tableau: ");
        detail.add_String("--PLACER LIEN TABLEAU--");

        return detail;
    }
    public Detail detail_AVG_masse_volumique_sols_superieurs(){
        Detail detail= new Detail("Valeur moyenne de masse volumique des sols se trouvant au dessus de la pointe du pieu:");
        detail.add_ln_String("<b>γ<sub>1</sub></b> = (");
        String  calc_str ="";
        float  calc =0;
        for(int i=0; i< getLayerCalculator().index_couchePortante(); i++){
            ParamSolData data = getParamSolDataList().get(i);
            detail.add_String("γ<sub>"+(i+1)+"</sub>  · h<sub>"+(i+1)+"</sub>");
            calc_str += data.yT()+" · "+enfoncement_couche_index_ToDisplay(i);
            calc += data.yT() * getLayerCalculator().enfoncement_couche_index(i)/1000;
            if(i != getLayerCalculator().index_couchePortante()-1){
                detail.add_String(" + ");
                calc_str += " + ";
            }
        }
        detail.add_String(") / h");
        float accumulation_profondeur  = (getLayerCalculator().accumulation_enfoncement_couche_index(getLayerCalculator().index_couchePortante()))/1000;
        detail.add_ln_String("= ("+calc_str+") / "+accumulation_profondeur);
        detail.add_String(" = "+round(calc,2)+"/"+accumulation_profondeur +" = <b>"+AVG_masse_volumique_sols_supérieurs()+"</b> т/m <sup>3</sup>");
        return detail;
    }

    public Detail detail_fd0_comp(){
        Detail detail= new Detail("pour les charges de compression");
        ParamSolData data = getParamSolDataList().get(getLayerCalculator().index_couchePortante()-1);
        detail.add_ln_String(" = <b>F<sub>d0,comp</sub></b> = ( α<sub>1</sub> · cT + α<sub>2</sub> · y1 · (H+Hk) ) · A<sub>comp</sub>");
        detail.add_ln_String(" = ("+getAlpha1()+" · "+data.cT()+" + "+getAlpha2()+" · "+AVG_masse_volumique_sols_supérieurs()+" · "+(getPieuParamManagerData().H_val()+getPieuParamManagerData().Hk_val())/1000+") · "+fd0_Acomp());
        detail.add_ln_String(" = "+fd0_comp());
        return detail;
    }
    public Detail detail_fd0_trac(){
        Detail detail= new Detail("pour les charges de traction");
        ParamSolData data = getParamSolDataList().get(getLayerCalculator().index_couchePortante()-1);
        detail.add_ln_String(" = <b>F<sub>d0,trac</sub></b> = ( α<sub>1</sub> · cT + α<sub>2</sub> · y1 · (H+Hk) ) · A<sub>trac</sub>");
        detail.add_ln_String(" = ("+getAlpha1()+" · "+data.cT()+" + "+getAlpha2()+" · "+AVG_masse_volumique_sols_supérieurs()+" · "+(getPieuParamManagerData().H_val()+getPieuParamManagerData().Hk_val())/1000+") · "+fd0_ATrac());
        detail.add_ln_String(" = "+fd0_trac());
        return detail;
    }
    public Detail detail_Resistance_sol_par_couche(int index ){
        ParamSolData data = getParamSolDataList().get(index);
        Detail detail= new Detail(getResistanceSolCalculator().constructTab(round(getLayerCalculator().profondeur_couche_index(index),2),data));
        detail.add_ln_String("pour les charges de traction");

        float hauteur_de_toit=0;
        if(data.getTypeSol() != TypeSol.REMBLAI)
        if(index == 0){
            hauteur_de_toit = getPieuParamManagerData().Hk_val()/1000;
        }else{
            hauteur_de_toit = getLayerCalculator().accumulation_h_couche_index(index-1)/1000;
        }
        detail.add_ln_String("<u> Horizon numéro "+(index+1)+", sol "+data.getTypeSol().getNameLowerCase()+", toit à la profondeur "+hauteur_de_toit+" m , puissance "+round(getLayerCalculator().profondeur_couche_index(index),3)+" m </u>");
        detail.add_ln_String("Profondeur de calcul de l horizon l"+(index+1)+" =");
        if(index == 0){ //calcul différent si c'est la première couche ou non.
            detail.add_String(" "+getPieuParamManagerData().Hk_val()/1000+" + "+getLayerCalculator().enfoncement_couche_index(index)/1000+" / 2 =");
        }else{
            detail.add_String(" "+getLayerCalculator().accumulation_h_couche_index(index-1)/1000+" + "+getLayerCalculator().enfoncement_couche_index(index)/1000+" / 2 =");
        }

        detail.add_String(" <b>"+round(getLayerCalculator().profondeur_couche_index(index),3)+"</b> m");
        if(getLayerCalculator().profondeur_couche_index(index) < 1){//si la hauteur est inférieur à 1m
            detail.add_String(" -> <b>1</b>m");
            detail.add_ln_String("<b>On borne la hauteur à 1m minimum</b>");
        }
        detail.add_ln_String("Suivant le tableau 7.3 est trouvée la valeur de résistance latérale du sol");
        detail.add_ln_String("<b>f"+(index+1)+"</b> = ");
        detail.add_String(round(resistanceSol_couche_Kpa(index),2)+" kPa = "+resistanceSol_couche_Tm(index)+" T/m²");
        detail.add_LineReturn();
        return detail;
    }//capacité portante:2.39 -> kn.n

    public Detail detail_resistance_AVG(){
        Detail detail= new Detail("Résistance moyenne du sol le long de la surface du fut du pieu:");
        detail.add_ln_String("<b>f</b>=(");
        String  calc_str ="";
        float  calc =0;
        for(int i=0; i< getLayerCalculator().index_couchePortante(); i++){
            ParamSolData data = getParamSolDataList().get(i);
            detail.add_String("f<sub>"+(i+1)+"</sub>  · h<sub>"+(i+1)+"</sub>");
            calc_str += resistanceSol_couche_Tm(i)+" · "+enfoncement_couche_index_ToDisplay(i);
            calc += resistanceSol_couche_Tm(i) * getLayerCalculator().enfoncement_couche_index(i)/1000;
            if(i != getLayerCalculator().index_couchePortante()-1){
                detail.add_String(" + ");
                calc_str += " + ";
            }
        }
        detail.add_String(") / h");
        float accumulation_profondeur  = (getLayerCalculator().accumulation_enfoncement_couche_index(getLayerCalculator().index_couchePortante()))/1000;
        detail.add_ln_String("= ("+calc_str+") / "+accumulation_profondeur);
        detail.add_ln_String(" = "+round(calc,2)+"/"+accumulation_profondeur +" = <b>"+resistance_AVG_long_du_fut()+"</b> т/m <sup>3</sup>");

        return detail;
    }
    public Detail detail_fdf(){
        Detail detail= new Detail("");
        detail.add_String("<b>F<sub>df</sub></b> = u · f · (I<sub>p</sub> - D<sub>hel</sub>)");
        detail.add_ln_String("<b>F<sub>df</sub></b> = ");
        detail.add_String(round(perimetre_section_transfersale_fut(),2)+" · "+round(resistance_AVG_long_du_fut(),2)+" · ");
        detail.add_String("("+getPieuParamManagerData().Ip_val()/1000+" - "+getPieuParamManagerData().Dhel_val()/1000+")");
        detail.add_String(" = <b>"+fdf_toDisplay()+"</b>T");

        return detail;
    }

    public TreeMap<DetailTitle, List<Detail>> generateDetails() {
        TreeMap<DetailTitle,List<Detail>> tampHash = new TreeMap<DetailTitle,List<Detail>>();
        ArrayList<Detail> detailsList;

        detailsList = new ArrayList<>();
        detailsList.add(detail_capacite_portante());
        detailsList.add(detail_capacite_portante_helice());
        detailsList.add(detail_capacite_portante_fut());
        tampHash.put(new DetailTitle(1,"Fonction capacité portante"),detailsList);

        detailsList = new ArrayList<>();
        tampHash.put(new DetailTitle(2,"Coefficient de travail du pieu dans le sol"),detailsList);

        detailsList = new ArrayList<>();
        detailsList.add(detail_Acomp());
        detailsList.add(detail_Atrac());
        tampHash.put(new DetailTitle(3,"Projection de surface helice du pieu"),detailsList);

        detailsList = new ArrayList<>();
        detailsList.add(detail_perimetre_section_transfersale_fut());
        tampHash.put(new DetailTitle(4,"Périmètre de la section transversale du fut de pieu"),detailsList);

        detailsList = new ArrayList<>();
        detailsList.add(detail_profondeur_enfoncemement());
        detailsList.add(detail_Longueur_fut_enfoncement_sol());
        tampHash.put(new DetailTitle(5,"Profondeur de la position/profondeur de hélice et longueur du fut, enfoncé dans le sol"),detailsList);

        detailsList = new ArrayList<>();
        detailsList.add(detail_alpha());
        detailsList.add(detail_AVG_masse_volumique_sols_superieurs());
        detailsList.add(detail_fd0_comp());
        detailsList.add(detail_fd0_trac());
        tampHash.put(new DetailTitle(6,"Capacité portante de hélice du pieu vissé"),detailsList);

        detailsList = new ArrayList<>();
        for(int i=0; i< getLayerCalculator().index_couchePortante(); i++) {
            detailsList.add(detail_Resistance_sol_par_couche(i));
        }
        detailsList.add(detail_resistance_AVG());
        tampHash.put(new DetailTitle(7,"Résistance du sol le long de la surface du fut"),detailsList);

        detailsList = new ArrayList<>();
        detailsList.add(detail_fdf());
        tampHash.put(new DetailTitle(8,"Capacité portante du fut du pieu vissé"),detailsList);

        detailsList = new ArrayList<>();
        tampHash.put(new DetailTitle(9,"Capacité portante du pieu sous charges"),detailsList);



        return tampHash;
    }
}
