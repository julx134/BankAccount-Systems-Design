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
    public Users (String username, String password, String role, String level, BankAccount bankAccount, File customerFile) {
        this.username = username;
        this.password = password;
        this.role = role;   
        this.bankAccount = bankAccount;
        this.level = level;
        credentials = customerFile;
    }
    
    @Override
    public String toString() {
        return ("Username: "+username+"\t\t"+"Password: "+password+"\t\t"+"Level: "+level); 
    }
    
    //normal methods to be used by both types of sub-classes
    protected BankAccount getBankAccount() {
        return bankAccount;
    }
    protected String getLevel() {
        return level;
    }
    protected String getUsername() {
        return username;
    }
    protected File getCredentials() {
        return credentials;
    }
    
    //abstract methods to be implemented by concrete sub-classes
    protected abstract void handleLogin(Client c,String username,String password) throws Exception;
    protected abstract void handleLogout(Client c);  
    
    //normal methods to be overridden by manager sub-class
    protected void handleAddCustomer(Client c, String username, String password) throws Exception {}
    protected void handleDeleteCustomer(Client c, int i) {}
        
    //normal methods to be overriden by customer sub-class
    protected void handleDeposit (Client c, String depositAmount) throws Exception{}
    protected void handleWithdraw (Client c, String withdrawAmount) throws Exception {}
    protected boolean isLevelChanged (Client c) {return false;}
}
