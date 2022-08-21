package com.example.pt_tugasakhir_agenda.model;

public class Category {
    private int idcategory;
    private String categoryname;

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Category(int idcategory, String categoryname) {
        this.idcategory = idcategory;
        this.categoryname = categoryname;
    }
}
