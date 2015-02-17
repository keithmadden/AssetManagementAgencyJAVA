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

	}

	public List < Customer > getCustomers() {
		return new ArrayList < Customer > (this.customers);
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

	boolean updateCustomer(Customer c) {
		boolean updated = false;

		try {
			updated = this.gateway.updateCustomer(c);
		} catch (SQLException ex) {
			Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
		}

		return updated;
	}

}