package com.example.app.model;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        
        Model model = Model.getInstance();
        
        Customer c;
        Branch b;
        
        int call;
        do {
            System.out.println("1. Create new Customer");
            System.out.println("2. Update existing Customer");
            System.out.println("3. Delete existing Customer");
            System.out.println("4. View all Customers");
            System.out.println("5. Create new Branch");
            System.out.println("6. Update existing Branch");
            System.out.println("7. Delete existing Branch");
            System.out.println("8. View all Branches");
            System.out.println("9. Exit");
            System.out.println();

            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            call = Integer.parseInt(line);
            
            System.out.println("You chose option " + call);
            switch (call) {
                case 1: {
                    System.out.println("Creating customer");
                    c = createCustomer(keyboard);
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
                    System.out.println("Creating branch");
                    b = createBranch(keyboard);
                    model.addBranch(b);
                    break;
                }
                case 6: {
                    System.out.println("Updating branch");
                    updateBranch(keyboard, model);     
                    break;
                }
                case 7: {
                    System.out.println("Deleting branch");
                    deleteBranch(keyboard, model);
                    break;
                }
                case 8: {
                    System.out.println("Viewing branch");
                    viewBranch(model);
                    break;
                }
                
            }
        }
        while (call != 9);
        System.out.println("Goodbye");
    } 
    
    private static Customer createCustomer(Scanner keyb) {
        String name, address, mobile, email;
        int id;
        String line;

        line = getString(keyb, "Enter ID: ");
        id = Integer.parseInt(line);
        name = getString(keyb, "Enter name: ");
        address = getString(keyb, "Enter address: ");
        mobile = getString(keyb, "Enter mobile: ");
        email = getString(keyb, "Enter email: ");

        Customer c = 
                new Customer(id, name, address, mobile, email);
        
        return c;
    }
    
    private static Branch createBranch(Scanner keyb) {
        String address, phone, manager, hours;
        int id;
        String line;

        line = getString(keyb, "Enter ID: ");
        id = Integer.parseInt(line);
        address = getString(keyb, "Enter address: ");
        phone = getString(keyb, "Enter phone: ");
        manager = getString(keyb, "Enter manager: ");
        hours = getString(keyb, "Enter hours: ");

        Branch b = 
                new Branch(id, address, phone, manager, hours);
        
        return b;
    }
    
    private static void updateCustomer(Scanner keyb, Model m) {
        System.out.print("Enter the ID of the customer to edit:");
        int id = Integer.parseInt(keyb.nextLine());
        Customer c;

    c = m.findCustomerById(id);
        if (c != null) {
            updateCustomerDetails(keyb, c);
            if (m.updateCustomer(c)) {
                System.out.println("Customer updated");
            }
            else {
                System.out.println("Customer not updated");
            }
        }
        else {
            System.out.println("Customer not found");
        }
    }
    
    private static void updateBranch(Scanner keyb, Model m) {
        System.out.print("Enter the ID of the branch to edit:");
        int id = Integer.parseInt(keyb.nextLine());
        Branch b;

    b = m.findBranchById(id);
        if (b != null) {
            updateBranchDetails(keyb, b);
            if (m.updateBranch(b)) {
                System.out.println("Branch updated");
            }
            else {
                System.out.println("Branch not updated");
            }
        }
        else {
            System.out.println("Branch not found");
        }
    }
    
    private static void deleteCustomer(Scanner keyboard, Model m) {
        System.out.println("Enter the ID of the customer to delete:");
        int id = Integer.parseInt(keyboard.nextLine());
        Customer c;

        c = m.findCustomerById(id);
        if (c != null) {
            if (m.removeCustomer(c)) {
                System.out.println("Customer deleted");
            }
            else {
                System.out.println("Customer not deleted");
            }
        }
        else {
            System.out.println("Customer not found");
        }
    }
    
    private static void deleteBranch(Scanner keyboard, Model m) {
        System.out.println("Enter the ID of the branch to delete:");
        int id = Integer.parseInt(keyboard.nextLine());
        Branch b;

        b = m.findBranchById(id);
        if (b != null) {
            if (m.removeBranch(b)) {
                System.out.println("Branch deleted");
            }
            else {
                System.out.println("Branch not deleted");
            }
        }
        else {
            System.out.println("Branch not found");
        }
    }
    
    private static void viewCustomers(Model model) {
        List<Customer> customers = model.getCustomers();
        System.out.println();
        if (customers.isEmpty()) {
            System.out.println("There are no customers in the database");
        }
        else {
            System.out.printf("%5s %20s %20s %15s %8s\n", "Id", "Name", "Address", "Mobile", "Email");
            for (Customer c : customers) {
                System.out.printf("%5d %20s %20s %15s %8s\n",
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getMobile(),
                        c.getEmail());
            }
        }
    }
    
    private static void viewBranch(Model model) {
        List<Branch> branches = model.getBranches();
        System.out.println();
        if (branches.isEmpty()) {
            System.out.println("There are no branches in the database");
        }
        else {
            System.out.printf("%5s %20s %20s %15s %8s\n", "Id", "Address", "Phone", "Manager", "Hours");
            for (Branch b : branches) {
                System.out.printf("%5d %20s %20s %15s %8s\n",
                        b.getId(),
                        b.getAddress(),
                        b.getPhone(),
                        b.getManager(),
                        b.getHours());
            }
        }
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void updateCustomerDetails(Scanner keyb, Customer c) {
        String name, address, mobile, email;
        int id;
        String line1;
        
        line1 = getString(keyb, "Enter id [" + c.getId() + "]: ");
        name = getString(keyb, "Enter name [" + c.getName() + "]: ");
        address = getString(keyb, "Enter address [" + c.getAddress() + "]: ");
        mobile = getString(keyb, "Enter mobile [" + c.getMobile() + "]: ");
        email = getString(keyb, "Enter email [" + c.getEmail() + "]: ");
        
        if (line1.length() != 0) {
            id = Integer.parseInt(line1);
            c.setId(id);
        }
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
    }
    
    private static void updateBranchDetails(Scanner keyb, Branch b) {
        String address, phone, manager, hours;
        int id;
        String line1;
        
        line1 = getString(keyb, "Enter id [" + b.getId() + "]: ");
        address = getString(keyb, "Enter address [" + b.getAddress() + "]: ");
        phone = getString(keyb, "Enter phone [" + b.getPhone() + "]: ");
        manager = getString(keyb, "Enter manager [" + b.getManager() + "]: ");
        hours = getString(keyb, "Enter hours [" + b.getHours() + "]: ");
        
        if (line1.length() != 0) {
            id = Integer.parseInt(line1);
            b.setId(id);
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

}


