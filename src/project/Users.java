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
public abstract class Users {
    //rep variables for abstract class
    protected String username,password,role;
    
    //constructor implementation for super in subclass
    public Users (String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;     
    }
    
    //methods to be implemented by concrete sub-classes
    public abstract void login(Client c,String username,String password) throws Exception;
    public abstract void logout(Client c);  
}
