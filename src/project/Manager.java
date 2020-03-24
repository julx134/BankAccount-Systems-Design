/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jules
 */
public class Manager extends Users{
    private static Manager instance;

    
    public Manager(String username, String password, String role) {
        super(username, password, role);
    }
    
    //singleton pattern
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager("admin","admin","manager");
        return instance;
    }

    @Override
    public void handleAddCustomer(Client c, String username, String password) throws Exception {
        File newCustomerFile = new File("src\\project\\CustomerInformation\\"+username+".txt");
        
        if(newCustomerFile.createNewFile()) {
            FileWriter writeFile = new FileWriter(newCustomerFile);
            writeFile.write(username+"\n");
            writeFile.write(password+"\n");
            writeFile.write("customer");
            writeFile.close();
        }else {
            System.out.println("test");
        }
            
    }
    
    @Override
    public void handleLogin(Client c, String username, String password) throws Exception{
        boolean usernameCheck = false, passwordCheck = false;
         
        try {
            
            File credentials = new File("src\\project\\ManagerInformation\\admin.txt");
  
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