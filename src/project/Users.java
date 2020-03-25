/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;

/**
 *
 * @author Jules
 */
public abstract class Users {
    //rep variables for abstract class
    protected String username,password,role,level;
    protected BankAccount bankAccount;
    protected File credentials;
    
    //constructor implementation for super in subclass
    public Users (String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;     
    }
    
    //overloaded constructor for customer user type
    public Users (String username, String password, String role, BankAccount bankAccount, File customerFile) {
        this.username = username;
        this.password = password;
        this.role = role;   
        this.bankAccount = bankAccount;
        credentials = customerFile;
    }
    
    //abstract methods to be implemented by concrete sub-classes
    public abstract void handleLogin(Client c,String username,String password) throws Exception;
    public abstract void handleLogout(Client c);  
    
    //normal methods to be overridden by manager sub-class
    public void handleAddCustomer(Client c, String username, String password) throws Exception {}
    public void handleDeleteCustomer(Client c, int i) {}
    
    //normal methods to be overriden by customer sub-class
    public void checkLevel() {}

    @Override
    public String toString() {
        return ("Username: "+username+"     "+"Level: "+level+"\n"); 
    }
    
    //normal methods to be overriden by both types of sub-classes
    public String getUsername() {
        return username;
    }
    
    public File getCredentials() {
        return credentials;
    }
    
}
