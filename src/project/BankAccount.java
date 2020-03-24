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
    private int fundsAmount;
    
    public BankAccount() {
        fundsAmount = 100;
    }
    
    public int showFunds() {
        return fundsAmount;
    }
    
    public void withdrawMoney(int amount) {
        if (amount > fundsAmount) {
            
        }else {
            fundsAmount -= amount;
        }
    }
    
    
   
}
