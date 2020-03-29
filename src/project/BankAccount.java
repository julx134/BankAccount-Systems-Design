/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Jules
 */
public class BankAccount{
    
    /*OVERVIEW: 
    *   BankAccount is a mutable class where you can deposit, withdraw and see funds.
    *   Users of type Customers owns a BankAccount.
    */
    
    /*Abstraction Function:
    *   AF(c) = A BankAccount b such that
    *   b.fundsAmount = c.fundsAmount, &&
    *   c.funsAmount is a member of all positive real numbers
    */
    
    /*The rep invariant is:
    *   c.fundsAmount is a member of all positive real numbers
    */
    //rep variable
    private double fundsAmount;
    
    //MODIFIES: fundsAmount
    //EFFECTS: Initializes this to have a fundsAmount of 100.00
    public BankAccount() {
        fundsAmount = 100.00;
    }
    
    //MODIFIES: fundsAmount
    //EFFECTS: Initializes fundsAmount to the value of initialFunds
    public BankAccount(double initialFunds) {
        fundsAmount = initialFunds;
    }
    
    //EFFECTS: Returns fundsAmount
    public double getFunds() {
        return fundsAmount;
    }
    
    
    //MODIFIES: fundsAmount
    //EFFECTS: adds depositAmount to the value of fundsAmount
    public void deposit(double depositAmount) {
        fundsAmount += depositAmount;
    }
    
    //MODIFIES: fundsAmount
    //EFFECTS: substracts withdrawAmount to fundsAmount and returns true if rep invariant holds
    //otherwise, add withdrawAmount to fundsAmount and return false;
    //
    public boolean withdraw(double withdrawAmount) {
        fundsAmount -= withdrawAmount;
        
        if(repOK()) {
            return true;
        }else {
            fundsAmount += withdrawAmount;
            return false;
        }
    }
    
    //EFFECTS: Returns true if the rep invariant holds for this
    //otherwise returns false
    public boolean repOK() {
        if (fundsAmount >= 0.00)
            return true;
        return false;
    }
   
    //EFFECTS: returns a string containing the fundsAmount in accounting number format
    @Override
    public String toString(){
        NumberFormat formatter = new DecimalFormat("$#,###,###.00");
        return("Bank Account Funds: $" + formatter.format(fundsAmount));
    }
}
