package com.example.aedificantes_calculateur_se_sol.Error;

public class Error {
    private String error_string;

    public Error(String error_string) {
        this.error_string = error_string;
    }

    public String getError_string() {
        return error_string;
    }

    public void setError_string(String error_string) {
        this.error_string = error_string;
    }
}
