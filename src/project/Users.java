/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Jules
 */
public abstract class Users {
    //rep variables for abstract class
    protected String username,password,role;
    protected BankAccount bankAccount;
    
    //constructor implementation for super in subclass
    public Users (String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;     
    }
    
    //overloaded constructor for customer user type
    public Users (String username, String password, String role, BankAccount bankAccount) {
        this.username = username;
        this.password = password;
        this.role = role;   
        this.bankAccount = bankAccount;
    }
    
    //abstract methods to be implemented by concrete sub-classes
    public abstract void handleLogin(Client c,String username,String password) throws Exception;
    public abstract void handleLogout(Client c);  
    
    //normal methods to be overridden by specific sub-class
    public void handleAddCustomer(Client c, String username, String password) throws Exception {}
    
    
}
