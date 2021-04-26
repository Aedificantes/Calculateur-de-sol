package com.example.aedificantes_calculateur_se_sol;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.TypeSol;

import java.util.ArrayList;

public class LineProfilSolAdaptater extends RecyclerView.Adapter<LineProfilSolAdaptater.LineProfilSolViewHolder> {
    private ArrayList<ParamSol> mListparamSols;
    //private ArrayList<int> mListparamSols;
    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }


    public static class LineProfilSolViewHolder  extends RecyclerView.ViewHolder {
        public Button BT_add_line;
        public TextView TV_number;
        public Spinner SP_TypeSol;
        public Spinner SP_Granularite;
        public Spinner SP_Compacite;
        public EditText TN_P1;
        public EditText TN_P2;
        public EditText TN_P3;
        public EditText TN_P4;
        public EditText TN_P5;
        public EditText TN_P6;
        public EditText TN_P7;

        public LineProfilSolViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            BT_add_line = itemView.findViewById(R.id.BT_add_line);
            TV_number = itemView.findViewById(R.id.TV_num);
            SP_TypeSol = itemView.findViewById(R.id.SP_TypeSol);
            SP_Granularite = itemView.findViewById(R.id.SP_Granularite);
            SP_Compacite = itemView.findViewById(R.id.SP_Compacite);
            TN_P1 = itemView.findViewById(R.id.TN_P1);
            TN_P2 = itemView.findViewById(R.id.TN_P2);
            TN_P3 = itemView.findViewById(R.id.TN_P3);
            TN_P4 = itemView.findViewById(R.id.TN_P4);
            TN_P5 = itemView.findViewById(R.id.TN_P5);
            TN_P6 = itemView.findViewById(R.id.TN_P6);
            TN_P7 = itemView.findViewById(R.id.TN_P7);



            setValues(SP_Granularite,Granularite.values());
            setValues(SP_Compacite, Compacite.values());
            setValues(SP_TypeSol, TypeSol.values());
        }

        private <T> void setValues(Spinner SP, T list[]) {
            ArrayAdapter<T> adapter = new ArrayAdapter<T>(itemView.getContext(), android.R.layout.simple_spinner_item,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SP.setAdapter(adapter);
        }
    }

    @NonNull
    @Override
    public LineProfilSolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_profil_sol, parent, false);
        LineProfilSolViewHolder ipvh = new LineProfilSolViewHolder(v, mListener);
        return ipvh;
    }
    public LineProfilSolAdaptater(ArrayList<ParamSol> listParams){
        mListparamSols = listParams;
    }
    public LineProfilSolAdaptater(){
        mListparamSols = new ArrayList<>();
    }
    @Override
    public void onBindViewHolder(@NonNull final LineProfilSolViewHolder holder, final int position) {
        final ParamSol currentItem = mListparamSols.get(position);

        holder.TV_number.setText(String.valueOf(position));
        holder.TN_P1.setText(String.valueOf(currentItem.p1));

        if(position == mListparamSols.size()-1){
            holder.BT_add_line.setText("+");
        }else{
            holder.BT_add_line.setText("-");
        }
        holder.BT_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position +" : "+(mListparamSols.size()-1));
                if(position == mListparamSols.size()-1){
                    mListparamSols.add(new ParamSol());
                    //notifyItemInserted(mListparamSols.size()-1);
                    //
                    ArrayList<ParamSol> dataTamp = new ArrayList<ParamSol>();
                    dataTamp.addAll(mListparamSols.subList(0,position+1));
                    dataTamp.add(new ParamSol());
                    mListparamSols.clear();
                    mListparamSols = dataTamp;
                    for(ParamSol each: mListparamSols){
                        System.out.println("eachLine P1: "+ each.p1);
                    }

                    //mListparamSols.get(position).icon  = "-";
                    notifyDataSetChanged();

                }else{
                    holder.TN_P1.setBackgroundColor(Color.YELLOW);
                    ArrayList<ParamSol> dataTamp = new ArrayList<ParamSol>();
                    if(position != 0){ dataTamp.addAll(mListparamSols.subList(0,position)); }
                    for(int i = position+1; i< mListparamSols.size() ;i++) {
                        dataTamp.add(mListparamSols.get(i));
                    }
                    mListparamSols.clear();
                    mListparamSols = dataTamp;
                    for(ParamSol each: mListparamSols){
                        System.out.println("eachLine P1: "+ each.p1);
                    }

                    notifyDataSetChanged();

                }
                //notifyItemRangeChanged(0, mListparamSols.size());
            }
        });

        holder.TN_P1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length() > 0) {
                    currentItem.p1 = Integer.parseInt(s.toString());
                }else{
                    currentItem.p1 = 0;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListparamSols.size();
    }

}

/*
OnButtonClick:
1. On vérifie si l'élement à qui appartient le bouton, est le dernier de la liste.
2. Si c'est le dernier: on ajoute une ligne avec des attributs de l'élement par défaut et on incrémente le compteur de notre array
3. Si ce n'est pas le dernier:
  - On prend le numéro de la ligne i_actuel
  - De i = i_actuel jusqu'à i = Nombre d'elements dans le massif: on copie les objets Objet (i) = Objet(i+1)

  - On supprime le dernier élement
  - On change le bouton "-"
*/