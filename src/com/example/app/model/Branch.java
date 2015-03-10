package com.example.app.model;

public class Branch {

    private int id;
    private String address;
    private String phone;
    private String manager;
    private String hours;

    public Branch(int id, String a, String p, String m, String h) {
        this.id = id;
        this.address = a;
        this.phone = p;
        this.manager = m;
        this.hours = h;
    }
    
    public Branch(String a, String p, String m, String h) {
        this(-1, a, p, m, h);
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
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
    
    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

}
