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
public class BankAccount implements Cloneable{
    private int fundsAmount;
    
    public BankAccount() {
        fundsAmount = 100;
    }
    
    public void showFunds() {
        //
    }
    
    public void withdrawMoney(int amount) {
        if (amount > fundsAmount) {
            
        }else {
            fundsAmount -= amount;
        }
    }
    
    
   
}
