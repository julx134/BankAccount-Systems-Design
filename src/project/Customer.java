/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Override
    public boolean isLevelChanged(Client c) {
        String level = c.getUser().getLevel();
        double userFunds = c.getUser().bankAccount.getFunds();
        
        switch (level) {
            case "Silver":
                if( userFunds >= 10000.00 && userFunds < 20000.00) {
                    this.level = "Gold";
                    replaceFileContent(c,"Silver","Gold");
                    return true;
                }else if(userFunds >= 20000.00) {
                    this.level = "Platinum";
                    replaceFileContent(c,"Silver","Platinum");
                    return true;
                }
                break;
            
            case "Gold":
                if(userFunds >= 20000.00) {
                    this.level = "Platinum";
                    replaceFileContent(c,"Gold","Platinum");
                    return true;
                }else if(userFunds < 10000.00) {
                    this.level = "Silver";
                    replaceFileContent(c,"Gold","Silver");
                    return true;
                }
                break;
                
            case "Platinum":
                if(userFunds >= 10000.00 && userFunds < 20000.00) {
                    this.level = "Gold";
                    replaceFileContent(c,"Platinum","Gold");
                    return true;
                }else if (userFunds < 10000.00) {
                    this.level = "Silver";
                    replaceFileContent(c,"Gold","Silver");
                    return true;
                }                    
        }    
        return false;
    }
    
    
    private String getFileContent(Client c) {
        String oldContent ="";
        BufferedReader reader = null;
        
        try {
            //reads all the content of the file
            reader = new BufferedReader(new FileReader(c.getUser().getCredentials()));
            String allContent = reader.readLine();
            while(allContent != null) {
                oldContent = oldContent+allContent+System.lineSeparator();
                allContent = reader.readLine();     
            }
            reader.close();   
        }catch (IOException e) {
            //
        }
        return oldContent;
    }
    
    private void replaceFileContent(Client c, String oldContent, String newContent) {
        try {
            FileWriter writer = null;
            String newFileContent = getFileContent(c).replaceAll(oldContent, newContent);
            writer = new FileWriter(c.getUser().getCredentials());
            writer.write(newFileContent);
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean validateInput(String userInput) {
        userInput = userInput.toLowerCase();
        
        for (int i = 0; i < userInput.length(); i++) {
            if (Character.isLetter(userInput.charAt(i)))
                return false;
        }
        
        return true;
    }
    
    @Override
    public void handleDeposit(Client c, String depositAmount) throws Exception {
       
        if (validateInput(depositAmount)) {
            
            //formats string input so it can be parsed as double
            NumberFormat formatter = new DecimalFormat("#.00");
            String formattedDepositString = depositAmount.replaceAll("\\$", "");
            formattedDepositString = formattedDepositString.replaceAll("\\,", "");
            
            //change type and format of old funds from double to string
            String oldAmountString = formatter.format(c.getUser().getBankAccount().getFunds());
             
            //deposits to bank account
            c.getUser().getBankAccount().deposit(Double.parseDouble(formattedDepositString));

            //gets new funds amount in string type
            String newAmountString = formatter.format(c.getUser().getBankAccount().getFunds());
            
            //write new amount to file
            replaceFileContent(c,oldAmountString, newAmountString);
            
        }else {
            throw new Exception();
        }
    }
    
    @Override
    public void handleWithdraw(Client c, String withdrawAmount) throws Exception {
       
        if (validateInput(withdrawAmount)) {
            
            //formats string input so it can be parsed as double
            NumberFormat formatter = new DecimalFormat("#.00");
            String formattedDepositString = withdrawAmount.replaceAll("\\$", "");
            formattedDepositString = formattedDepositString.replaceAll("\\,", "");
            
            //change type and format of old funds from double to string
            String oldAmountString = formatter.format(c.getUser().getBankAccount().getFunds());
             
            //withdraws to bank account
            if(c.getUser().getBankAccount().withdraw(Double.parseDouble(formattedDepositString))) {
                //gets new funds amount in string type
                String newAmountString = formatter.format(c.getUser().getBankAccount().getFunds());
                //write new amount to file
                replaceFileContent(c,oldAmountString, newAmountString);
            }else {
                throw new Exception();
            }
        }else {
            throw new Exception();
        }
    }
}
