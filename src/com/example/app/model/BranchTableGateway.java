package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BranchTableGateway {
    private static final String TABLE_NAME = "branch";
    private static final String COLUMN_ID = "Branch_id";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_MANAGER = "Manager";
    private static final String COLUMN_HOURS = "Hours";

    private Connection mConnection;
    private List<Branch> branches;

    public BranchTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    public List<Branch> getBranches() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet b;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Branch> branches;   // the java.util.List containing the Programmer objects
                            // created for each row in the result of the query
        int id;             // the id of a programmer
        String address;
        String phone; 
        String manager;
        String hours;
        Branch bra; // a Programmer object created from a row in the result of
                            // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        b = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        branches = new ArrayList<Branch>();
        while (b.next()) {
            id = b.getInt(COLUMN_ID);
            address = b.getString(COLUMN_ADDRESS);
            phone = b.getString(COLUMN_PHONE);
            manager = b.getString(COLUMN_MANAGER);
            hours = b.getString(COLUMN_HOURS);

            bra = new Branch(id, address, phone, manager, hours);
            branches.add(bra);

        }
        return branches;
    }
    
    public int insertBranch(Branch bra) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        ResultSet b;
        int id;

        id = -1;
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_ADDRESS + ", "
                + COLUMN_PHONE + ", "
                + COLUMN_MANAGER + ", "
                + COLUMN_HOURS 
                + ") VALUES (?, ?, ?, ?)";

        stmt = this.mConnection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setString(1, bra.getAddress());
        stmt.setString(2, bra.getPhone());
        stmt.setString(3, bra.getManager());
        stmt.setString(4, bra.getHours());

        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected != 1) {
            throw new RuntimeException("Could not insert branch.");
        }

        b = stmt.getGeneratedKeys();
        if (b != null && b.next()) {
            id = (int)b.getLong(1);
        }

        return id;
    }
    
    public boolean deleteBranch(int id) throws SQLException {
    String query;
    PreparedStatement stmt;
    int numRowsAffected;

    query = "DELETE FROM " + TABLE_NAME + " WHERE `" + COLUMN_ID + "` = ?";

    stmt = mConnection.prepareStatement(query);
    stmt.setInt(1, id);

    numRowsAffected = stmt.executeUpdate();
    return (numRowsAffected == 1);
    }
    
    public boolean updateBranch(Branch b) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_ADDRESS      + " = ?, "
                + COLUMN_PHONE + " = ?, "
                + COLUMN_MANAGER  + " = ?, "
                + COLUMN_HOURS   + " = ? "
                + " WHERE `" + COLUMN_ID + "` = ?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, b.getAddress());
        stmt.setString(2, b.getPhone());
        stmt.setString(3, b.getManager());
        stmt.setString(4, b.getHours());
        stmt.setInt(5, b.getId());
        
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
}

