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
public class BankAccount{
    private double fundsAmount;
    
    public BankAccount() {
        fundsAmount = 100.00;
    }
    
    public BankAccount(double initialFunds) {
        fundsAmount = initialFunds;
    }
    
    public double getFunds() {
        return fundsAmount;
    }
    
    public void withdrawMoney(int amount) {
        if (amount > fundsAmount) {
            
        }else {
            fundsAmount -= amount;
        }
    }
    
    
   
}
