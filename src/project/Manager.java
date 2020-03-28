/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.Exceptions.*;

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
    
    //for init function in client - reads already existing files
    public void handleInitAddCustomer(Client c, String username, String password,String level,String funds, File customerFile) {
        //creates a new SilverCustomer and adds it to array list of customers
        c.getCustomers().add(new Customer(username,password,"customer",level,new BankAccount(Double.parseDouble(funds)),customerFile));
    }
        
    @Override
    public void handleAddCustomer(Client c, String username, String password) throws UndefinedInputException,SameUsernameException {    
        checkInput(username,password);
        
        File newCustomerFile = new File("src\\project\\CustomerInformation\\"+username+".txt");  

        try {

            if (newCustomerFile.createNewFile()){
                FileWriter writeFile = new FileWriter(newCustomerFile);
                writeFile.write("Username:\n"+username+"\n");
                writeFile.write("Password:\n"+password+"\n");
                writeFile.write("Funds:\n100.00\n");
                writeFile.write("Level:\nSilver\n");
                writeFile.write("Role:\nCustomer");
                writeFile.close();
            }else {
                throw new SameUsernameException();
            }

        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //creates a new SilverCustomer and adds it to array list of customers
        c.getCustomers().add(new Customer(username,password,"customer","Silver",new BankAccount(),newCustomerFile));
    }   
    
    
    @Override
    public void handleDeleteCustomer(Client c,int i) {
        c.getCustomers().get(i).getCredentials().delete();
        c.getCustomers().remove(i);    
    }
   
    @Override
    public void handleLogin(Client c, String username, String password) throws UndefinedInputException,IncorrectLoginAttemptException{
        checkInput(username,password);
        
        if(username.equals("admin") && password.equals("admin"))
            c.setUser(getInstance());
        else
            throw new IncorrectLoginAttemptException();
    }
    
    @Override
    public void handleLogout(Client c) {
        c.setUser(null);
    }    
}
