package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerTableGateway {
    private static final String TABLE_NAME = "customer";
    private static final String COLUMN_ID = "Customer ID";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_MOBILE = "Mobile";

    private Connection mConnection;
    private List<Customer> customers;

    public CustomerTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    public List<Customer> getCustomers() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet c;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Customer> customers;   // the java.util.List containing the Programmer objects
                            // created for each row in the result of the query
        int id;             // the id of a programmer
        String name;
        String address; 
        String email;
        String mobile;
        Customer cus; // a Programmer object created from a row in the result of
                            // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        c = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        customers = new ArrayList<Customer>();
        while (c.next()) {
            id = c.getInt(COLUMN_ID);
            name = c.getString(COLUMN_NAME);
            address = c.getString(COLUMN_ADDRESS);
            email = c.getString(COLUMN_MOBILE);
            mobile = c.getString(COLUMN_EMAIL);

            cus = new Customer(id, name, address, email, mobile);
            customers.add(cus);

        }
        return customers;
    }
    
    public int insertCustomer(Customer cus) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        ResultSet c;
        int id;

        id = -1;
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_NAME + ", "
                + COLUMN_ADDRESS + ", "
                + COLUMN_MOBILE + ", "
                + COLUMN_EMAIL 
                + ") VALUES (?, ?, ?, ?)";

        stmt = this.mConnection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setString(1, cus.getName());
        stmt.setString(2, cus.getAddress());
        stmt.setString(3, cus.getMobile());
        stmt.setString(4, cus.getEmail());

        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected != 1) {
            throw new RuntimeException("Could not insert customer.");
        }

        c = stmt.getGeneratedKeys();
        if (c != null && c.next()) {
            id = (int)c.getLong(1);
        }

        return id;
    }
    
    public boolean deleteCustomer(int id) throws SQLException {
    String query;
    PreparedStatement stmt;
    int numRowsAffected;

    query = "DELETE FROM " + TABLE_NAME + " WHERE `" + COLUMN_ID + "` = ?";

    stmt = mConnection.prepareStatement(query);
    stmt.setInt(1, id);

    numRowsAffected = stmt.executeUpdate();
    return (numRowsAffected == 1);
    }
    
    public boolean updateCustomer(Customer c) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME      + " = ?, "
                + COLUMN_ADDRESS + " = ?, "
                + COLUMN_MOBILE  + " = ?, "
                + COLUMN_EMAIL   + " = ? "
                + " WHERE `" + COLUMN_ID + "` = ?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getAddress());
        stmt.setString(3, c.getMobile());
        stmt.setString(4, c.getEmail());
        stmt.setInt(5, c.getId());
        
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
}

