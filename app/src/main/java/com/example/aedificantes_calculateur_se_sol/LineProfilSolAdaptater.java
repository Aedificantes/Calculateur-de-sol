package com.example.aedificantes_calculateur_se_sol;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultUpdatable;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.TypeSol;

import java.util.ArrayList;

public class LineProfilSolAdaptater extends RecyclerView.Adapter<LineProfilSolAdaptater.LineProfilSolViewHolder> {
    private ArrayList<ParamSol> mListparamSols;
    public OnItemClickListener mListener;
    private ResultUpdatable updater;

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }


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
    public LineProfilSolAdaptater(ArrayList<ParamSol> listParams, ResultUpdatable updater){
        mListparamSols = listParams;
        this.updater =  updater;
    }
    public LineProfilSolAdaptater(ResultUpdatable updater){
        mListparamSols = new ArrayList<>();
        this.updater =  updater;
    }
    @Override
    public void onBindViewHolder(@NonNull final LineProfilSolViewHolder holder, final int position) {
        final ParamSol currentItem = mListparamSols.get(position);

        setParamSolLastElement();
        currentItem.getParamEnabler().manage(holder);


        holder.TV_number.setText(String.valueOf(position));
        holder.ET_List.get(0).setText(String.valueOf(currentItem.params.get(0)));
        holder.ET_List.get(1).setText(String.valueOf(currentItem.params.get(1)));
        holder.ET_List.get(2).setText(String.valueOf(currentItem.params.get(2)));
        holder.ET_List.get(3).setText(String.valueOf(currentItem.params.get(3)));
        holder.ET_List.get(4).setText(String.valueOf(currentItem.params.get(4)));
        holder.ET_List.get(5).setText(String.valueOf(currentItem.params.get(5)));
        holder.ET_List.get(6).setText(String.valueOf(currentItem.params.get(6)));

        holder.BT_delete_line.setEnabled(true);
        holder.BT_delete_line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListparamSols.size()>1) {
                        mListparamSols.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mListparamSols.size() - position);
                        System.out.println(currentItem.toString());
                        setParamSolLastElement();
                        updateAll_ET_Element();
                        verifyEverythingIsFill();
                    }else{
                        Toast.makeText(MainActivity.MAIN_CONTEXT, "Au moins 1 élément est requis.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        setEditTextListener(holder, currentItem);

        holder.SP_TypeSol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setTypeSol(TypeSol.values()[position]);
                currentItem.getParamEnabler().update();
                verifyEverythingIsFill();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        holder.SP_Compacite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setCompacite(Compacite.values()[position]);
                currentItem.getParamEnabler().update();
                verifyEverythingIsFill();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        holder.SP_Granularite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentItem.setGranularite(Granularite.values()[position]);
                currentItem.getParamEnabler().update();
                verifyEverythingIsFill();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        updateAll_ET_Element();

    }


    private void setEditTextListener(LineProfilSolViewHolder holder, final ParamSol paramSol){
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
                        paramSol.setValueByIndex(finalI, Float.parseFloat(s.toString()));
                    } else {
                        paramSol.setValueByIndex(finalI, 0);
                    }
                    verifyEverythingIsFill();
                }
            });
        }
    }

    private void setParamSolLastElement(){
        for(ParamSol each: mListparamSols){
            each.getParamEnabler().setLast(false);
        }
        mListparamSols.get(mListparamSols.size()-1).getParamEnabler().setLast(true);
    }

    private void updateAll_ET_Element(){
        for(ParamSol each: mListparamSols){
            each.getParamEnabler().update();
        }
    }

    private void verifyEverythingIsFill(){
        for(ParamSol each_PS : mListparamSols){
            if(!each_PS.isAllFill()) {
                updater.allValuesAreNotSet();
                return;
            }
        }
        updater.allValuesAreSet();
    }

    @Override
    public int getItemCount() {
        return mListparamSols.size();
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
}
