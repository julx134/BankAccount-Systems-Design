/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
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
    private ArrayList<Users> customers = new ArrayList<Users>();
    private Users user;
    private String username,password,role;
    
    @Override
    public void init() throws Exception {
        boolean username = false, password = false;
        String usernameString="",passwordString="";
        
        File dir = new File("src\\project\\CustomerInformation");
        File[] files = dir.listFiles();
      
        for (File file: files) {
            try {
                Scanner input = new Scanner(file);
                
                while (input.hasNextLine()) {
                String data = input.nextLine();
                
                    switch (data) {
                        case "Username:":
                            username = true;
                            break;
                        case "Password:":
                            password = true;
                            break;
                        default:
                            if(username) {
                                username = false;
                                usernameString = data;
                            }
                            if(password) {
                                password = false;
                                passwordString = data;
                            }
                    }
                }   
            input.close();
            System.out.println(usernameString+","+passwordString);
            Manager.getInstance().handleInitAddCustomer(this, usernameString, passwordString,file);
            }catch (Exception e) {
                System.out.println("Not deleted");
            }       
        }
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
            role = "manager";
            menuButton.setText("Manager");
        });
        //when the login button is pressed
        loginButton.setOnAction(e -> {
            if (role == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.setContentText("There is an error with the way you logged in.\n");
                alert.showAndWait();
                usernameTF.deleteText(0, usernameTF.getText().length());
                passwordTF.deleteText(0, passwordTF.getText().length());
            }else {
                username = usernameTF.getText();
                usernameTF.deleteText(0, usernameTF.getText().length());
                password = passwordTF.getText();
                passwordTF.deleteText(0, passwordTF.getText().length());
                
                //passes the request to concrete state class
                requestLogin(loginStage,username,password,role);
            }
        });
       
        
        //creating scene
        Scene scene = new Scene(root);
        //when the enter button is pressed
        scene.setOnKeyPressed(e -> {
            if (role == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error!");
                alert.setContentText("There is an error with the way you logged in.\n");
                alert.showAndWait();
                usernameTF.deleteText(0, usernameTF.getText().length());
                passwordTF.deleteText(0, passwordTF.getText().length());
            }else if (e.getCode() == KeyCode.ENTER) {
                username = usernameTF.getText();
                usernameTF.deleteText(0, usernameTF.getText().length());
                password = passwordTF.getText();
                passwordTF.deleteText(0, passwordTF.getText().length());
                
                //passes the request to concrete state class
                requestLogin(loginStage,username,password,role);
            }
        });
        
        
        //creating stage
        loginStage.setTitle("Bank Account");
        loginStage.setWidth(1024);
        loginStage.setHeight(683);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
        
    }
         
    public void managerPage(Stage loginPage) throws FileNotFoundException {
        //creating stage
        Stage managerStage = new Stage();
        
        //creating nodes
        Image image = new Image(new FileInputStream("src\\project\\Images\\managerbackground.jpg")); 
        Image image1 = new Image(new FileInputStream("src\\project\\Images\\person.png"));
        Image image2 = new Image(new FileInputStream("src\\project\\Images\\personremove.png"));
        Image image3 = new Image(new FileInputStream("src\\project\\Images\\logout.png"));
        ImageView imageV1 = new ImageView(image1);
        ImageView imageV2 = new ImageView(image2);
        ImageView imageV3 = new ImageView(image3);
        BackgroundImage background = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,null);
        Button logoutButton = new Button("Logout",imageV3);
        Button addCustomerButton = new Button("Add Customer",imageV1);
        Button deleteCustomerButton = new Button("Delete Customer",imageV2);
        ToolBar toolBar = new ToolBar(); 
        
        
        //setting node properties
        toolBar.getItems().add(logoutButton);
        toolBar.setStyle("-fx-background-color:#0E67CC");
        toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        logoutButton.setStyle("-fx-font-size:15px");
        logoutButton.setMinHeight(20);
        logoutButton.setMinWidth(40);
        imageV3.setFitHeight(15);
        imageV3.setFitWidth(15);
        addCustomerButton.setMinSize(230, 80);
        addCustomerButton.setStyle("-fx-font-size:30px");
        imageV1.setFitHeight(70);
        imageV1.setFitWidth(70);
        deleteCustomerButton.setMinSize(200, 80);
        deleteCustomerButton.setStyle("-fx-font-size:26px");
        imageV2.setFitHeight(70);
        imageV2.setFitWidth(70);
        
       
        //creating layout
        VBox root = new VBox();
        
        //setting layout properties
        root.getChildren().addAll(toolBar,addCustomerButton,deleteCustomerButton);
        root.setSpacing(100);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setBackground(new Background(background));

        //creating action event handlers
        logoutButton.setOnAction(e -> {
            requestLogout(loginPage,managerStage);
        });
        addCustomerButton.setOnAction(e -> {
            try {
                addCustomerPage(loginPage,managerStage);
                managerStage.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        deleteCustomerButton.setOnAction(e -> {
            try {
                deleteCustomerPage(loginPage,managerStage);
                managerStage.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //creating scene
        Scene scene = new Scene(root);
        
        //setting stage properties  
        managerStage.setTitle("Bank Account");
        managerStage.setWidth(1024);
        managerStage.setHeight(683);
        managerStage.setScene(scene);
        managerStage.setResizable(false);
        managerStage.show();
        
    }
    
    public void addCustomerPage(Stage loginPage, Stage mainPage) throws FileNotFoundException {
        //creating new stage
        Stage addCustomerPage = new Stage();
        
        //creating nodes
        Image image = new Image(new FileInputStream("src\\project\\Images\\managerbackground.jpg")); 
        Image image1 = new Image(new FileInputStream("src\\project\\Images\\logout.png"));
        Image image2 = new Image(new FileInputStream("src\\project\\Images\\mainmenu.png"));
        ImageView imageV1 = new ImageView(image1);
        ImageView imageV2 = new ImageView(image2);
        BackgroundImage background = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,null);
        Button logoutButton = new Button("Logout",imageV1);
        Button exitButton = new Button("Main Menu",imageV2);
        ToolBar toolBar = new ToolBar();
        TextField usernameTF = new TextField();
        TextField passwordTF = new TextField();
        Label usernameLabel = new Label("Username:",usernameTF);
        Label passwordLabel = new Label(" Password:",passwordTF);
        Button addCustomerButton = new Button("Add");
        Label addCustomerLabel = new Label("Add Customer:");
        
        //setting node properties
        toolBar.getItems().addAll(logoutButton,exitButton);
        toolBar.setStyle("-fx-background-color:#0E67CC");
        toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        logoutButton.setStyle("-fx-font-size:15px");
        logoutButton.setMinSize(40,20);
        imageV1.setFitHeight(15);
        imageV1.setFitWidth(15);
        exitButton.setStyle("-fx-font-size:15px");
        exitButton.setMinSize(40,20);
        imageV2.setFitHeight(15);
        imageV2.setFitWidth(15);
        usernameLabel.setContentDisplay(ContentDisplay.RIGHT);
        usernameLabel.setTextFill(Color.BLACK);
        passwordLabel.setContentDisplay(ContentDisplay.RIGHT);
        passwordLabel.setTextFill(Color.BLACK);
        usernameLabel.setFont(new Font("Open Sans",15));
        passwordLabel.setFont(new Font("Open Sans",15));
        usernameTF.setPrefWidth(180);
        passwordTF.setPrefWidth(180);
        addCustomerButton.setMinSize(120,50);
        addCustomerButton.setStyle("-fx-font-size:20px");
        addCustomerLabel.setStyle("-fx-font-size:50px");
        addCustomerLabel.setTextFill(Color.BLACK);

        //creating layout
        VBox root = new VBox();
        
        //setting layout properties
        root.getChildren().addAll(toolBar,addCustomerLabel,usernameLabel,passwordLabel,addCustomerButton);
        root.setSpacing(70);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setBackground(new Background(background));
        VBox.setMargin(usernameLabel,new Insets(0,80,0,0));
        VBox.setMargin(passwordLabel,new Insets(0,80,0,0));
        VBox.setMargin(addCustomerButton,new Insets(0,30,0,0));

        
        
        //creating action event handlers
        logoutButton.setOnAction(e -> {
            requestLogout(loginPage,addCustomerPage);
        });
        exitButton.setOnAction(e -> {
            try {
                managerPage(loginPage);
                addCustomerPage.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        addCustomerButton.setOnAction(e -> {
            requestAddCustomer(usernameTF.getText(),passwordTF.getText());
            usernameTF.deleteText(0, usernameTF.getText().length());
            passwordTF.deleteText(0, passwordTF.getText().length());
                       
        });
                
        //creating scene
        Scene scene = new Scene(root);
        //when the enter button is pressed
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                requestAddCustomer(usernameTF.getText(),passwordTF.getText());
                usernameTF.deleteText(0, usernameTF.getText().length());
                passwordTF.deleteText(0, passwordTF.getText().length());
            }
        });
        
        
        //setting stage properties
        addCustomerPage.setTitle("Bank Account");
        addCustomerPage.setWidth(1024);
        addCustomerPage.setHeight(683);
        addCustomerPage.setScene(scene);
        addCustomerPage.setResizable(false);
        addCustomerPage.show();
        
    }
    
    @SuppressWarnings("empty-statement")
    public void deleteCustomerPage(Stage loginPage, Stage mainPage) throws FileNotFoundException {
        //creating new stage
        Stage deleteCustomerPage = new Stage();
        
        //creating nodes
        Image image = new Image(new FileInputStream("src\\project\\Images\\managerbackground.jpg")); 
        Image image1 = new Image(new FileInputStream("src\\project\\Images\\logout.png"));
        Image image2 = new Image(new FileInputStream("src\\project\\Images\\mainmenu.png"));
        ImageView imageV1 = new ImageView(image1);
        ImageView imageV2 = new ImageView(image2);
        BackgroundImage background = new BackgroundImage(image,null,null,BackgroundPosition.CENTER,null);
        Button logoutButton = new Button("Logout",imageV1);
        Button exitButton = new Button("Main Menu",imageV2);
        ToolBar toolBar = new ToolBar();
        MenuButton menuButton = new MenuButton("Select Customer");
        Button deleteButton = new Button("Delete");
        
        
        //setting node properties
        toolBar.getItems().addAll(logoutButton,exitButton);
        toolBar.setStyle("-fx-background-color:#0E67CC");
        toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        logoutButton.setStyle("-fx-font-size:15px");
        logoutButton.setMinSize(40,20);
        imageV1.setFitHeight(15);
        imageV1.setFitWidth(15);
        exitButton.setStyle("-fx-font-size:15px");
        exitButton.setMinSize(40,20);
        imageV2.setFitHeight(15);
        imageV2.setFitWidth(15);  
        menuButton.setMinSize(330, 70);
        menuButton.setStyle("-fx-font-size:25px");
        deleteButton.setMinSize(120,50);
        deleteButton.setStyle("-fx-font-size:20px");
        
        
        //creating layout
        VBox root = new VBox();
        
        //setting layout properties
        root.getChildren().addAll(toolBar,menuButton);
        root.setSpacing(100);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setBackground(new Background(background));
        
        
        //creating action event handlers
        logoutButton.setOnAction(e -> {
            requestLogout(loginPage,deleteCustomerPage);
        });
        exitButton.setOnAction(e -> {
            try {
                managerPage(loginPage);
                deleteCustomerPage.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //setting customers as labels in the menuButton and deleting that instance from customer list.
        for (Users customer : customers) {
            MenuItem customerItem = new MenuItem(customer.toString());
            customerItem.setStyle("-fx-font-size:25px");
            try {
                menuButton.getItems().add(customerItem);
            }catch (IllegalArgumentException e) {
                e.addSuppressed(e);
            }
            
            customerItem.setOnAction(e -> {
                menuButton.setText(customer.toString());
                root.getChildren().addAll(deleteButton);
                
                deleteButton.setOnAction(x-> {
                    if (requestDeleteCustomer(menuButton.getItems().indexOf(customerItem))) {
                        menuButton.setText("Select Customer");
                        menuButton.getItems().remove(customerItem);
                        root.getChildren().remove(deleteButton);
                    }
                });
            });  
        }
                
        //creating scene
        Scene scene = new Scene(root);

        
        //setting stage properties
        deleteCustomerPage.setTitle("Bank Account");
        deleteCustomerPage.setWidth(1024);
        deleteCustomerPage.setHeight(683);
        deleteCustomerPage.setScene(scene);
        deleteCustomerPage.setResizable(false);
        deleteCustomerPage.show();
    }
    
    //authenticates the user credentials and sets the user state
    public void requestLogin (Stage loginPage,String username, String password, String role)  {
        
        //try-catch clause where the state change is handled by the login method of the concrete class
        try {
            if(role.equals("manager")) {
                user = new Manager(username,password,role);
                user.handleLogin(this,username,password);
            }
        }catch(Exception e) {   //concrete class will throw an exception when credentials are incorrect
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("Incorrect username and/or password.\n");
            alert.showAndWait();
            
            user = null;
        }
        
        
        //moves to the corresponding page depending on user state
        if(user instanceof Manager) {
            //close stage
            loginPage.close();
            
            //move to next stage
            try {
                managerPage(loginPage);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    //sets the user state to null
    public void requestLogout(Stage loginPage, Stage currentStage) {
        try {
            user.handleLogout(this); 
            start(loginPage);
            currentStage.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //adds the customer to customer arraylist
    public void requestAddCustomer(String username, String password){
        try {
            user.handleAddCustomer(this,username,password);
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("A new customer has been created");
            alert.showAndWait();
        }catch (Exception x) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error!");
            alert.setContentText("Either there is an account associated with this username or the username/password field is empty");
            alert.showAndWait();
        }             
    }

    //deletes the file and corresponding menuItem
    public boolean requestDeleteCustomer(int i) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete this customer?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            user.handleDeleteCustomer(this, i);
            return true;
        }else {
            return false;
        }
    }
    
    //set the user object type
    public void setUser(Users user) {
        this.user = user;
    }
    
    //returns the ArrayList of customers
    public ArrayList<Users> getCustomers() {
        return customers;
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
