package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;







public class Model {

	private static Model instance = null;

	public static synchronized Model getInstance() throws DataAccessException {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	private CustomerTableGateway gateway;
	private List < Customer > customers;
        private BranchTableGateway Branchgateway;
	private List < Branch > branches;
        
	private Model() throws DataAccessException {

		try {
			Connection conn = DBConnection.getInstance();
			gateway = new CustomerTableGateway(conn);
                        Branchgateway = new BranchTableGateway(conn);

			this.customers = gateway.getCustomers();
                        this.branches = Branchgateway.getBranches();
                        
		} catch (ClassNotFoundException ex) {
			throw new DataAccessException("Exception initilising Model object " + ex.getMessage());
		} catch (SQLException ex) {
			throw new DataAccessException("Exception initilising Model object " + ex.getMessage());
		}

	}

	public List <Customer> getCustomers() {
		return new ArrayList <Customer> (this.customers);
	}
        
        public List <Customer> getCustomersByBranchId( int  branchId) {
            List<Customer> list = new ArrayList<Customer>();
            for (Customer c : this.customers) {
                if (c.getBranchId() == branchId) {
                    list.add(c);
                }
            }
            return list;
	}
        
        public List <Branch> getBranches() {
		return new ArrayList <Branch> (this.branches);
	}

	public void addCustomer(Customer c) throws DataAccessException {
		try {
			int id = this.gateway.insertCustomer(c);
			c.setId(id);
			this.customers.add(c);
		} catch (SQLException ex) {
			throw new DataAccessException("Exception adding customer " + ex.getMessage());
		}
	}
        
        public void addBranch(Branch b) throws DataAccessException {
		try {
			int id = this.Branchgateway.insertBranch(b);
			b.setBranchId(id);
			this.branches.add(b);
		} catch (SQLException ex) {
			throw new DataAccessException("Exception adding branch " + ex.getMessage());
		}
	}

	public boolean removeCustomer(Customer c) throws DataAccessException {
		boolean removed = false;

		try {
			removed = this.gateway.deleteCustomer(c.getId());
			if (removed) {
				removed = this.customers.remove(c);
			}
			return removed;
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}
		return removed;
	}
        
        public boolean removeBranch(Branch b) throws DataAccessException {
		boolean removed = false;

		try {
			removed = this.Branchgateway.deleteBranch(b.getBranchId());
			if (removed) {
				removed = this.branches.remove(b);
			}
			return removed;
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}
		return removed;
	}

	Customer findCustomerById(int id) {
		Customer c = null;
		int i = 0;
		boolean found = false;
		while (i < this.customers.size() && !found) {
			c = this.customers.get(i);
			if (c.getId() == id) {
				found = true;
			} else {
				i++;
			}
		}
		if (!found) {
			c = null;
		}
		return c;
	}
        
	Branch findBranchById(int id) {
		Branch b = null;
		int i = 0;
		boolean found = false;
		while (i < this.branches.size() && !found) {
			b = this.branches.get(i);
			if (b.getBranchId() == id) {
				found = true;
			} else {
				i++;
			}
		}
		if (!found) {
			b = null;
		}
		return b;
	}

	boolean updateCustomer(Customer c) throws DataAccessException {
		boolean updated = false;

		try {
			updated = this.gateway.updateCustomer(c);
		} catch (SQLException ex) {
			throw new DataAccessException("Exception updating customer " + ex.getMessage());
		}

		return updated;
	}
        
        boolean updateBranch(Branch b) throws DataAccessException {
		boolean updated = false;

		try {
			updated = this.Branchgateway.updateBranch(b);
		} catch (SQLException ex) {
			throw new DataAccessException("Exception updating branch " + ex.getMessage());
		}

		return updated;
	}

}