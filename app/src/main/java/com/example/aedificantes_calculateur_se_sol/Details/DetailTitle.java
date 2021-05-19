package com.example.aedificantes_calculateur_se_sol.Details;

public class DetailTitle implements Comparable<DetailTitle>{
    private String text;
    private int number;

    public DetailTitle(int number,String text) {
        this.text = text;
        this.number = number;
    }

    @Override
    public int compareTo(DetailTitle o) {
        //return Integer.compare(this.number, o.number);
        if(this.getNumber() > o.getNumber()){ return 1;}
        if(this.getNumber() < o.getNumber()){ return -1;}
        return 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
