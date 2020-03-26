/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static project.Manager.getInstance;

/**
 *
 * @author Jules
 */
public class Customer extends Users{
    private static Customer instance;
   
    public Customer(String username, String password, String role, String level, BankAccount bankAccount,File customerFile) {
        super(username, password, role, level, bankAccount,customerFile);
    }
    
     //singleton pattern
    public static Customer getInstance() {
        if (instance == null)
            instance = new Customer("","","","",null,null);
        return instance;
    }

    @Override
    public void handleLogin(Client c, String username, String password) throws Exception{
        boolean usernameCheck = false, passwordCheck = false;
         
        try {
            
           credentials = new File("src\\project\\CustomerInformation\\"+username+".txt");
  
            Scanner input = new Scanner(credentials);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                
                if(data.equals(username))
                    usernameCheck = true;
                
                if(data.equals(password))
                    passwordCheck = true;
                
                if(data.equals("Silver"))
                    level = "Silver";
                
                if(data.equals("Gold"))
                    level = "Gold";
                
                if(data.equals("Platinum"))
                    level = "Platinum";
                    
            }
            input.close();
        } catch (FileNotFoundException e) {
            throw new Exception();
        }
        
        if (usernameCheck && passwordCheck) {
            for(Users customer : c.getCustomers()) {
                if (customer.getUsername().equals(username))    
                    c.setUser(c.getCustomers().get(c.getCustomers().indexOf(customer)));
            }
        }else {
            throw new Exception();
        }   
    }
    
    
    
    @Override
    public void handleLogout(Client c) {
        c.setUser(null);
    }    
    
    
}
