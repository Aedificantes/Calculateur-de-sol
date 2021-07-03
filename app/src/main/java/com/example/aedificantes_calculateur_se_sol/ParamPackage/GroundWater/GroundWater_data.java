package com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Like every other param classes GroundWater has his own data classes that can be serializable to be place in ParamContainerData and transfert to other Activity
 */
public class GroundWater_data implements Serializable {
    private boolean isChecked;
    private float nes;
    private float aquifere;

    public GroundWater_data(boolean isChecked, float nes, float aquifere) {
        this.isChecked = isChecked;
        this.nes = nes;
        this.aquifere = aquifere;
    }

    public GroundWater_data() {
        this.isChecked = false;
        this.aquifere = 0f;
        this.nes = 0f;
    }

    public JSONObject convert_to_JSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("IS_CHECKED",isChecked);
        obj.put("NES",nes);
        obj.put("AQUIFERE",aquifere);
        return obj;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public float getNes() {
        return nes;
    }

    public float getAquifere() {
        return aquifere;
    }
}
