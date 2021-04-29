package com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.aedificantes_calculateur_se_sol.Error.Error;

import java.util.List;

public class PieuParam {
    private EditText ET_PieuParam;
    private float value_PieuParam =0f;
    private String nameOfElement;

    public PieuParam(EditText ET, String nameOfElement) {
        this.nameOfElement = nameOfElement;
        ET_PieuParam = ET;
        ET_PieuParam.addTextChangedListener(new TextWatcher() {

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
            }
        });
    }

    public boolean isFill(){
        return value_PieuParam != 0f;
    }

    public Error generateError(){
        return new Error(this.nameOfElement+" n'a pas de valeur");
    }

}
