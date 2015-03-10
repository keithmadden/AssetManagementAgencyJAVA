package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;







public class Model {

	private static Model instance = null;

	public static synchronized Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	private CustomerTableGateway gateway;
	private List < Customer > customers;
        private BranchTableGateway Branchgateway;
	private List < Branch > branches;
        
	private Model() {

		try {
			Connection conn = DBConnection.getInstance();
			gateway = new CustomerTableGateway(conn);

			this.customers = gateway.getCustomers();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}
                
                try {
			Connection conn = DBConnection.getInstance();
			Branchgateway = new BranchTableGateway(conn);

			this.branches = Branchgateway.getBranches();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public List < Customer > getCustomers() {
		return new ArrayList < Customer > (this.customers);
	}
        
        public List < Branch > getBranches() {
		return new ArrayList < Branch > (this.branches);
	}

	public void addCustomer(Customer c) {
		try {
			int id = this.gateway.insertCustomer(c);
			c.setId(id);
			this.customers.add(c);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
        
        public void addBranch(Branch b) {
		try {
			int id = this.Branchgateway.insertBranch(b);
			b.setId(id);
			this.branches.add(b);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean removeCustomer(Customer c) {
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
        
        public boolean removeBranch(Branch b) {
		boolean removed = false;

		try {
			removed = this.Branchgateway.deleteBranch(b.getId());
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
			if (b.getId() == id) {
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

	boolean updateCustomer(Customer c) {
		boolean updated = false;

		try {
			updated = this.gateway.updateCustomer(c);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}

		return updated;
	}
        
        boolean updateBranch(Branch b) {
		boolean updated = false;

		try {
			updated = this.Branchgateway.updateBranch(b);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}

		return updated;
	}

}