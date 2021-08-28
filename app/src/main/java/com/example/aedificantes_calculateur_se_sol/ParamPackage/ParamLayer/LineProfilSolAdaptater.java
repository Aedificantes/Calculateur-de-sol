package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileParamManager;
import com.example.aedificantes_calculateur_se_sol.R;
import com.example.aedificantes_calculateur_se_sol.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Complexe class to create recyclerView and display LayerParam inside
 */
public class LineProfilSolAdaptater extends RecyclerView.Adapter<LineProfilSolAdaptater.LineProfilSolViewHolder> implements VerificateObserver {

    private static final String LOG_TAG = "CLA_1964";

    private Context context;
    private ArrayList<ParamLayer> mListparamLayers;
    public OnItemClickListener mListener;
    private VerificateObservable verificator;
    private ScrewPileParamManager screwPileParamManager;
    private boolean allAreSet = false;

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }


    //manage each element to display line by line
    public static class LineProfilSolViewHolder  extends RecyclerView.ViewHolder {
        public Button BT_delete_line;
        public TextView TV_number;
        public Spinner SP_TypeSol;
        public Spinner SP_Granularite;
        public Spinner SP_Compacite;
        public ArrayList<EditText> ET_List = new ArrayList<>();


        public LineProfilSolViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            BT_delete_line = itemView.findViewById(R.id.BT_delete_line);
            TV_number = itemView.findViewById(R.id.TV_num);
            SP_TypeSol = itemView.findViewById(R.id.SP_TypeSol);
            SP_Granularite = itemView.findViewById(R.id.SP_Granularite);
            SP_Compacite = itemView.findViewById(R.id.SP_Compacite);
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P1));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P2));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P3));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P4));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P5));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P6));
            ET_List.add((EditText) itemView.findViewById(R.id.TN_P7));

            setValues(SP_Granularite,Granularite.values());
            setValues(SP_Compacite, Compacite.values());
            setValues(SP_TypeSol, TypeSol.values());
        }

        private <T> void setValues(Spinner SP, T list[]) {
            ArrayAdapter<T> adapter = new ArrayAdapter<T>(itemView.getContext(), android.R.layout.simple_spinner_item,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SP.setAdapter(adapter);
        }

        public EditText get_ET_index(int indexET){
            return ET_List.get(indexET);
        }
        public void set_All_ET_Desable(){
            for(EditText each_ET: ET_List){
                each_ET.setEnabled(false);
            }
        }
    }

    @NonNull
    @Override
    public LineProfilSolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_profil_sol, parent, false);
        LineProfilSolViewHolder ipvh = new LineProfilSolViewHolder(v, mListener);
        return ipvh;
    }
    public LineProfilSolAdaptater(Context context, ArrayList<ParamLayer> listParams, VerificateObservable verificator, ScrewPileParamManager managerPieu){
        mListparamLayers = listParams;
        this.verificator =  verificator;
        this.screwPileParamManager = managerPieu;
        //verificator.addLikeObserver(this);
        this.context = context;
    }
    public LineProfilSolAdaptater(Context context, VerificateObservable verificator, ScrewPileParamManager managerPieu){
        mListparamLayers = new ArrayList<>();
        this.verificator =  verificator;
        this.screwPileParamManager = managerPieu;
        //verificator.addLikeObserver(this);
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull final LineProfilSolViewHolder holder, final int position) {
        final ParamLayer currentItem = mListparamLayers.get(position);

        setParamSolLastElement();
        currentItem.getParamEnabler().manage(holder);


        holder.TV_number.setText(String.valueOf(position));
        for(IndexColumnName each: IndexColumnName.values()){
            holder.ET_List.get(each.getIndice()).setText(String.valueOf(currentItem.getParams().get(each)));
        }
        /*
        holder.ET_List.get(0).setText(String.valueOf(currentItem.getParams().get(0)));
        holder.ET_List.get(1).setText(String.valueOf(currentItem.getParams().get(1)));
        holder.ET_List.get(2).setText(String.valueOf(currentItem.getParams().get(2)));
        holder.ET_List.get(3).setText(String.valueOf(currentItem.getParams().get(3)));
        holder.ET_List.get(4).setText(String.valueOf(currentItem.getParams().get(4)));
        holder.ET_List.get(5).setText(String.valueOf(currentItem.getParams().get(5)));
        holder.ET_List.get(6).setText(String.valueOf(currentItem.getParams().get(6)));

         */
        holder.SP_TypeSol.setSelection(currentItem.getTypeSol().getIndice());
        holder.SP_Granularite.setSelection(currentItem.getGranularite().getIndice());
        holder.SP_Compacite.setSelection(currentItem.getCompacite().getIndice());

        holder.BT_delete_line.setEnabled(true);
        //on delete line button press
        holder.BT_delete_line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListparamLayers.size()>1) {
                        mListparamLayers.get(position).removeObserver();
                        mListparamLayers.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mListparamLayers.size() - position);
                        setParamSolLastElement();
                        updateAll_ET_Element();
                        verificator.notifyDataChange();
                    }else{
                        Toast.makeText(context, "Au moins 1 élément est requis.", Toast.LENGTH_SHORT).show();
                        Log.d("list ParamSol","Au moins 1 élément est requis.");
                    }
                }
            });

        setEditTextListener(holder, currentItem);

        holder.SP_TypeSol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setTypeSol(TypeSol.values()[position]);
                currentItem.getParamEnabler().update();
                verificator.notifyDataChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        holder.SP_Compacite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setCompacite(Compacite.values()[position]);
                currentItem.getParamEnabler().update();
                verificator.notifyDataChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        holder.SP_Granularite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setGranularite(Granularite.values()[position]);
                currentItem.getParamEnabler().update();
                verificator.notifyDataChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if(!allAreSet){ // permet d'identifier si toutes les lignes ont été crées, autrement, l'appel de updateAll_ET_Element() pose un problème sur les lignes dont le holder n'a pas été placé dans ParamEnabler
            updatePOS_ET_Element(position);
            if(position == mListparamLayers.size()-1)
                allAreSet = true;
        }else{
            updateAll_ET_Element();
        }

    }


    private void setEditTextListener(LineProfilSolViewHolder holder, final ParamLayer paramLayer){
        for (int i = 0; i< holder.ET_List.size(); i++) {
            final int finalI = i;
            holder.ET_List.get(i).addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.toString().length() > 0) {
                        paramLayer.setValueByColumnName(IndexColumnName.getIndexColumnName_byId(finalI), Float.parseFloat(s.toString()));
                        Log.d(LOG_TAG, "new value for "+IndexColumnName.getIndexColumnName_byId(finalI) + " to value : "+Float.parseFloat(s.toString())+" then value of getter is : "+paramLayer.Il());
                    } else {
                        paramLayer.setValueByColumnName(IndexColumnName.getIndexColumnName_byId(finalI), 0);
                        Log.d(LOG_TAG, "onTextChanged of ground param line -> text length of EditText : ["+ IndexColumnName.getIndexColumnName_byId(finalI) +"] is equal to 0 so param si set to 0");
                    }
                    verificator.notifyDataChange();
                }
            });
        }
    }

    private void setParamSolLastElement(){
        for(ParamLayer each: mListparamLayers){
            each.getParamEnabler().setLast(false);
        }
        mListparamLayers.get(mListparamLayers.size()-1).getParamEnabler().setLast(true);
    }

    private void updateAll_ET_Element(){
        for(ParamLayer each: mListparamLayers){
            each.getParamEnabler().update();
        }
    }
    private void updatePOS_ET_Element(int pos){
            mListparamLayers.get(pos).getParamEnabler().update();
    }


    @Override
    public int getItemCount() {
        return mListparamLayers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
        //return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return super.getItemId(position);
    }

    @Override
    public boolean isFill() {
        /*
        if(screwPileParamManager.isFill()){
            for(ParamLayer each: mListparamLayers){
                each.setLoadLayer(false);
            }
            HomeFragment.resultManager.getLayerCalculator().ParamSol_couchePortante().setLoadLayer(true);
            updateAll_ET_Element();
        }

         */
        for(ParamLayer each_PS : mListparamLayers){
            if(!each_PS.isFill())
                return false;
        }
        return true;
    }

    @Override
    public List<Error> generateError() {
        return null;
    }


}
