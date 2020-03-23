/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Jules
 */
public class Client extends Application {
    //rep variables
    private Users user;
    private String username,password,role;
    
    
    public void startPage (Stage primaryStage,String username, String password, String role) {
        try {
            if(role.equals("manager")) {
                user = new Manager(username,password,role);
                user.login(this,username,password);
            }
        }catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("Incorrect username and/or password.\n");
            alert.showAndWait();
            
            user = null;
        }
           
        if(user instanceof Manager) {
            System.out.println("Hooray!!!");
        } 
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
   
    
    @Override
    public void start(Stage loginStage) throws FileNotFoundException { 
        
        //creating nodes
        Button loginButton = new Button("Login");
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();
        Label usernameLabel = new Label("Username:",usernameTF);
        Label passwordLabel = new Label(" Password:",passwordTF);
        MenuItem customer = new MenuItem("Customer");
        MenuItem manager = new MenuItem("Manager");
        MenuButton menuButton = new MenuButton("You are a...",null,customer,manager);
        Image image = new Image(new FileInputStream("src\\project\\Images\\bankbackground.jpg"));
        //Image image = new Image("https://www.vpr.org/sites/vpr/files/styles/x_large/public/201902/piggy-bank-split-istock-HighLaZ.jpg");
        BackgroundImage background = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,null);
        
        //setting node properties
        usernameTF.setPrefWidth(180);
        passwordTF.setPrefWidth(180);
        menuButton.setPrefWidth(180);
        menuButton.setPrefHeight(35);
        usernameLabel.setContentDisplay(ContentDisplay.RIGHT);
        usernameLabel.setTextFill(Color.WHITESMOKE);
        passwordLabel.setContentDisplay(ContentDisplay.RIGHT);
        passwordLabel.setTextFill(Color.WHITESMOKE);
        usernameLabel.setFont(new Font("Open Sans",15));
        passwordLabel.setFont(new Font("Open Sans",15));
        loginButton.setPrefWidth(80);
        loginButton.setPrefHeight(40);
        
     
        //creating layout
        VBox root = new VBox();
        
        //setting layout properties
        root.getChildren().addAll(menuButton,usernameLabel,passwordLabel,loginButton);
        root.setSpacing(40);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(background));
        VBox.setMargin(usernameLabel,new Insets(0,80,0,0));
        VBox.setMargin(passwordLabel,new Insets(0,80,0,0));
        
        
       
        //creating action event handlers
        customer.setOnAction(e ->  {
            role = "customer";
            menuButton.setText("Customer");
        });
        manager.setOnAction(e -> {
            role="manager";
            menuButton.setText("Manager");
        });
        loginButton.setOnAction(e -> {
            if (role == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.setContentText("There seems to be an error with the way you logged in.\n");
                alert.showAndWait();
                usernameTF.deleteText(0, usernameTF.getText().length());
                passwordTF.deleteText(0, passwordTF.getText().length());
            }else {
                username = usernameTF.getText();
                usernameTF.deleteText(0, usernameTF.getText().length());
                password = passwordTF.getText();
                passwordTF.deleteText(0, passwordTF.getText().length());
                
                //passing the variables to the next stage
                startPage(loginStage,username,password,role);
            }
        });
        
        //creating scene
        Scene scene = new Scene(root);
        
        //creating stage
        loginStage.setTitle("Bank Account");
        loginStage.setWidth(1024);
        loginStage.setHeight(683);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
        
    }
    
}
