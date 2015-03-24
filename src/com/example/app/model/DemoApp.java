package com.example.app.model;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        
        Model model;
        Customer c;
        Branch b;
        int opt = 11;
        
        do {
            try {
                model = Model.getInstance();
                System.out.println("1. Create new Customer");
                System.out.println("2. Update existing Customer");
                System.out.println("3. Delete existing Customer");
                System.out.println("4. View all Customers");
                System.out.println("5. View a single Customers");
                System.out.println();
                System.out.println("6. Create new Branch");
                System.out.println("7. Update existing Branch");
                System.out.println("8. Delete existing Branch");
                System.out.println("9. View all Branches");
                System.out.println("10. View a single Branch");
                System.out.println();
                System.out.println("11. Exit");
                System.out.println();


                opt = getInt(keyboard, "Enter option: ", 11);

                System.out.println("You chose option " + opt);
                switch (opt) {
                    case 1: {
                        System.out.println("Creating customer");
                        c = createCustomer(keyboard, model);
                        model.addCustomer(c);

                        break;
                    }
                    case 2: {
                        System.out.println("Updating customer");
                        updateCustomer(keyboard, model);     
                        break;
                    }
                    case 3: {
                        System.out.println("Deleting customer");
                        deleteCustomer(keyboard, model);
                        break;
                    }
                    case 4: {
                        System.out.println("Viewing customer");
                        viewCustomers(model);
                        break;
                    }
                    case 5: {
                        System.out.println("Viewing a single customer");
                        viewCustomer(keyboard, model);
                        break;
                    }
                    case 6: {
                        System.out.println("Creating branch");
                        b = createBranch(keyboard);
                        model.addBranch(b);
                        break;
                    }
                    case 7: {
                        System.out.println("Updating branch");
                        updateBranch(keyboard, model);     
                        break;
                    }
                    case 8: {
                        System.out.println("Deleting branch");
                        deleteBranch(keyboard, model);
                        break;
                    }
                    case 9: {
                        System.out.println("Viewing branch");
                        viewBranches(model);
                        break;
                    }
                    case 10: {
                        System.out.println("Viewing branch");
                        viewBranch(keyboard, model);
                        break;
                    }

                }
            }
            catch(DataAccessException e) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
        while (opt != 11);
        System.out.println("Goodbye");
        System.out.println();
    } 
    
    private static Customer createCustomer(Scanner keyb, Model model) {
        String name, address, mobile, email;
        int branchId;

        name = getString(keyb, "Enter name: ");
        address = getString(keyb, "Enter address: ");
        mobile = getString(keyb, "Enter mobile: ");
        email = getString(keyb, "Enter email: ");
        viewBranches(model);
        branchId = getInt(keyb, "Enter Branch ID: ", -1);
        System.out.println();

        Customer c = 
                new Customer(name, address, mobile, email, branchId);
        
        return c;
    }
    
    private static Branch createBranch(Scanner keyb) {
        String address, phone, manager, hours;
        int id;

        id = getInt(keyb, "Enter ID: ", -1);
        address = getString(keyb, "Enter address: ");
        phone = getString(keyb, "Enter phone: ");
        manager = getString(keyb, "Enter manager: ");
        hours = getString(keyb, "Enter hours: ");
        System.out.println();

        Branch b = 
                new Branch(id, address, phone, manager, hours);
        
        return b;
    }
    
    private static void updateCustomer(Scanner keyb, Model m) throws DataAccessException {
        int id = getInt(keyb, "Enter the ID of the customer to edit :", -1);
        System.out.println();
        Customer c;

    c = m.findCustomerById(id);
        if (c != null) {
            updateCustomerDetails(keyb, c, m);
            if (m.updateCustomer(c)) {
                System.out.println("Customer updated");
                System.out.println();
            }
            else {
                System.out.println("Customer not updated");
                System.out.println();
            }
        }
        else {
            System.out.println("Customer not found");
            System.out.println();
        }
    }
    
    private static void updateBranch(Scanner keyb, Model m) throws DataAccessException {
        int id = getInt(keyb, "Enter the ID of the branch to update:", -1);
        System.out.println();
        Branch b;

    b = m.findBranchById(id);
        if (b != null) {
            updateBranchDetails(keyb, b);
            if (m.updateBranch(b)) {
                System.out.println("Branch updated");
                System.out.println();
            }
            else {
                System.out.println("Branch not updated");
                System.out.println();
            }
        }
        else {
            System.out.println("Branch not found");
            System.out.println();
        }
    }
    
    private static void deleteCustomer(Scanner keyboard, Model m) throws DataAccessException {
        int id = getInt(keyboard, "Enter the ID of the customer to delete:", -1);
        System.out.println();
        Customer c;

        c = m.findCustomerById(id);
        if (c != null) {
            if (m.removeCustomer(c)) {
                System.out.println("Customer deleted");
                System.out.println();
            }
            else {
                System.out.println("Customer not deleted");
                System.out.println();
            }
        }
        else {
            System.out.println("Customer not found");
            System.out.println();
        }
    }
    
    private static void deleteBranch(Scanner keyboard, Model m) throws DataAccessException {
        int id = getInt(keyboard, "Enter the ID of the branch to delete:", -1);  
        System.out.println();
        Branch b;

        b = m.findBranchById(id);
        if (b != null) {
            if (m.removeBranch(b)) {
                System.out.println("Branch deleted");
                System.out.println();
            }
            else {
                System.out.println("Branch not deleted");
                System.out.println();
            }
        }
        else {
            System.out.println("Branch not found");
            System.out.println();
        }
    }
    
    private static void viewCustomers(Model model) {
        List<Customer> customers = model.getCustomers();
        System.out.println();
        if (customers.isEmpty()) {
            System.out.println("There are no customers in the database");
            System.out.println();
        }
        else {
            displayCustomers(customers, model);
        }
        System.out.println();
    }
    
    private static void displayCustomers(List<Customer> customers, Model model) {
        
        System.out.printf("%5s %20s %20s %15s %30s %20s\n", "Id", "Name", "Address", "Mobile", "Email", "Branch Address", -1);
            for (Customer c : customers) {
                Branch b = model.findBranchById(c.getBranchId());
                System.out.printf("%5d %20s %20s %15s %30s %20s\n",
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getMobile(),
                        c.getEmail(),
                        (b != null) ? b.getAddress() : "");
                System.out.println();
            }
    }
    
    private static void viewBranches(Model model) {
        List<Branch> branches = model.getBranches();
        System.out.println();
        if (branches.isEmpty()) {
            System.out.println("There are no branches in the database");
        }
        else {
            System.out.printf("%5s %20s %20s %15s %12s\n", "Id", "Address", "Phone", "Manager", "Hours", -1);
            for (Branch b : branches) {
                System.out.printf("%5d %20s %20s %15s %12s\n",
                        b.getBranchId(),
                        b.getAddress(),
                        b.getPhone(),
                        b.getManager(),
                        b.getHours());
            }
            System.out.println();
        }
    }
    
    private static void viewCustomer(Scanner keyboard, Model m) throws DataAccessException {
        int id = getInt(keyboard, "Enter the ID of the customer to view:", -1);
        System.out.println();
        Customer c;

        c = m.findCustomerById(id);
        if (c != null) {
            Branch b = m.findBranchById(c.getBranchId());
            System.out.println("Name            : " + c.getName());
            System.out.println("Address         : " + c.getAddress());
            System.out.println("Mobile          : " + c.getMobile());
            System.out.println("Email           : " + c.getEmail());
            System.out.println("Branch Address  : " + ((b != null) ? b.getAddress() : ""));
            System.out.println();
        }
        else {
            System.out.println();
            System.out.println("Customer not found");
        }
    }
    
    private static void viewBranch(Scanner keyboard, Model m) throws DataAccessException {
        int id = getInt(keyboard, "Enter the ID of the branch to view:", -1);  
        Branch b;

        b = m.findBranchById(id);
        if (b != null) {
            System.out.println();
            System.out.println("Address      : " + b.getAddress());
            System.out.println("Phone        : " + b.getPhone());
            System.out.println("Manager      : " + b.getManager());
            System.out.println("Hours        : " + b.getHours());
            System.out.println();
            
            List<Customer> customerList = m.getCustomersByBranchId(b.getBranchId());
            
            if(customerList.isEmpty()) {
                System.out.println();
                System.out.println("This Branch no customers");
                System.out.println();
            }
            else {
                System.out.println("This Branch has the following customers : ");
                System.out.println();
                displayCustomers(customerList, m);
            }
        }
        else {
            System.out.println("Branch not found");
        }
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void updateCustomerDetails(Scanner keyb, Customer c, Model model) {
        String name, address, mobile, email;
        int id, branchId;
        
        name = getString(keyb, "Enter name [" + c.getName() + "]: ");
        address = getString(keyb, "Enter address [" + c.getAddress() + "]: ");
        mobile = getString(keyb, "Enter mobile [" + c.getMobile() + "]: ");
        email = getString(keyb, "Enter email [" + c.getEmail() + "]: ");
        viewBranches(model);
        branchId = getInt(keyb, "Enter branch id (enter -1 for no manager)[" + c.getBranchId() + "]: ", -1);
        
        if (name.length() != 0) {
            c.setName(name);
        }
        if (address.length() != 0) {
            c.setAddress(address);
        }
        if (mobile.length() != 0) {
            c.setMoblie(mobile);
        }
        if (email.length() != 0) {
            c.setEmail(email);
        }
        if (branchId != c.getBranchId()) {
            c.setBranchId(branchId);
        }
    }
    
    private static void updateBranchDetails(Scanner keyb, Branch b) {
        String address, phone, manager, hours;
        int id;
        
        id = getInt(keyb, "Enter id [" + b.getBranchId() + "]: " , -1);
        address = getString(keyb, "Enter address [" + b.getAddress() + "]: ");
        phone = getString(keyb, "Enter phone [" + b.getPhone() + "]: ");
        manager = getString(keyb, "Enter manager [" + b.getManager() + "]: ");
        hours = getString(keyb, "Enter hours [" + b.getHours() + "]: ");
        
        if (id != b.getBranchId()) {
            b.setBranchId(id);
        }
        if (address.length() != 0) {
            b.setAddress(address);
        }
        if (phone.length() != 0) {
            b.setPhone(phone);
        }
        if (manager.length() != 0) {
            b.setManager(manager);
        }
        if (hours.length() != 0) {
            b.setHours(hours);
        }
    }
    
    private static int getInt(Scanner keyb, String prompt, int defaultVlue ) {
        
        int opt = defaultVlue;
        boolean finished = false;
        
        do {
            try {
                System.out.print(prompt);
                String line = keyb.nextLine();
                if (line.length() > 0) {
                opt = Integer.parseInt(line);
                }
                finished = true; 
            }
            catch(NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while (!finished);
            
        return opt;
    }

}


