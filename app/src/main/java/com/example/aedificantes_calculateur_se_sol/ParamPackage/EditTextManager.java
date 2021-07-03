package com.example.aedificantes_calculateur_se_sol.ParamPackage;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;

public class EditTextManager implements VerificateObserver {
    private float value_PieuParam =0f;
    private String nameOfElement;
    private VerificateObservable verificator; //Pour cette classe on ne s'enregistre pas, c'est le PieuParamManager qui en aura la responsabilité
    private EditText ET;

    public EditTextManager(EditText ET, String nameOfElement, final VerificateObservable verificator) {
        this.verificator = verificator;
        this.nameOfElement = nameOfElement;
        this.ET = ET;
        ET.addTextChangedListener(new TextWatcher() {

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
                    value_PieuParam = Float.parseFloat(s.toString());
                } else {
                    value_PieuParam = 0;
                }
                verificator.notifyDataChange();
            }
        });
    }

    public void setEnabled(boolean value){
        this.ET.setEnabled(value);
    }

    public void setValue(float value){
        this.value_PieuParam = value;
        ET.setText(String.valueOf(value));
    }

    public float value(){
        return this.value_PieuParam;
    }
    @Override
    public boolean isFill(){
        return value_PieuParam != 0f;
    }

    public Error generateError(){
        return new Error(this.nameOfElement+" n'a pas de valeur");
    }

}
