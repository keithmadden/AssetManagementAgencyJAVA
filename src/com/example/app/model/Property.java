package com.example.app.model;

public class Property {

    private int id;
    private int customer_id;
    private String address;
    private String price;
    private String date;

    public Property(int id, int Cid, String a, String p, String d) {
        this.id = id;
        this.customer_id = Cid;
        this.address = a;
        this.price = p;
        this.date = d;
    }
    
    public Property(int Cid, String a, String p, String d) {
        this(-1, Cid, a, p, d);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
