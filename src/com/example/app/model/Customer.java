package com.example.app.model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private int branchId;

    public Customer(int id, String n, String a, String m, String e, int branchId) {
        this.id = id;
        this.name = n;
        this.address = a;
        this.mobile = m;
        this.email = e;
        this.branchId = branchId;
    }
    
    public Customer(String n, String a, String m, String e, int branchId) {
        this(-1, n, a, m, e, branchId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobile() {
        return mobile;
    }

    public void setMoblie(String mobile) {
        this.mobile = mobile;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    

}
