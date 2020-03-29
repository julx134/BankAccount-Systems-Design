/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import project.Exceptions.*;

/**
 *
 * @author Jules
 */
public abstract class Users {
    //rep variables for abstract class
    protected String username,password,role,level;
    protected BankAccount bankAccount;
    protected File credentials;
    
    //constructor implementation for manager user type
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
     
    /*
    *normal methods to be used by both types of sub-classes
    */
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
    protected void checkInput(String ...input) throws UndefinedInputException{
        for(String checkString : input) {
            if(checkString.equals(""))
                throw new UndefinedInputException();     
        }
    }
    
    //abstract methods to be implemented by both of the concrete sub-classes
    protected abstract void handleLogin(Client c,String username,String password) throws UndefinedInputException,IncorrectLoginAttemptException;
    protected abstract void handleLogout(Client c);  
    
    //normal methods to be overridden by manager sub-class
    protected void handleAddCustomer(Client c, String username, String password) throws UndefinedInputException,SameUsernameException {}
    protected void handleDeleteCustomer(Client c, int customerIndex) {}
        
    //normal methods to be overriden by customer sub-class
    @Override
    public String toString() {
        return (""); 
    }
    protected void handleDeposit (Client c, String depositAmount) throws UndefinedInputException{}
    protected void handleWithdraw (Client c, String withdrawAmount) throws UndefinedInputException,InsufficientFundsException {}
    protected void handlePurchase (Client c, String purchaseAmount) throws InsufficientFundsException,LimitNotReachedException {}
    protected String getTotalPurchase(Client c, String initialAmount){return null;}
    protected boolean isLevelChanged (Client c) {return false;}
    
    
}
