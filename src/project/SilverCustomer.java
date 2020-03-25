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
public class SilverCustomer extends Users{
   
    public SilverCustomer(String username, String password, String role, BankAccount bankAccount,File customerFile) {
        super(username, password, role, bankAccount,customerFile);
        level = "Silver";
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
            }
            input.close();
        } catch (FileNotFoundException e) {
            //
        }
        if (usernameCheck && passwordCheck) {
            c.setUser(getInstance());
        }else {
            throw new Exception();
        }   
    }
    
    @Override
    public void handleLogout(Client c) {
        c.setUser(null);
    }    
    
    
}
