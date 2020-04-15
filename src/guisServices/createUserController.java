package guisServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entities.Reparation;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.UserService;
import utils.BCrypt;
import utils.Mail;

public class createUserController implements Initializable {
    
   
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXButton btnCreateUser;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    public String generateRandomUsername() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
 
    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
 
    return generatedString;
    
    }
    
    public String generateRandomPassword() 
    { int n=8;
          String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 

        StringBuilder sb = new StringBuilder(n); 
        for (int i = 0; i < n; i++) { 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
              sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
        return sb.toString(); 
    }
    
    @FXML
    public void createUser(){
        
        //String text = id_client.getText();
        //int id = Integer.parseInt(text);
        
        UserService us = new UserService();
        User u = new User();
        
        u.setUsername(generateRandomUsername());
        //u.setUsername("yesmlouhnogy");
        System.out.println(u.getUsername());
        u.setEmail(email.getText());
        u.setAddress(address.getText());
        u.setPhone(Integer.parseInt(phone.getText()));
        String password = generateRandomPassword();
        Mail.sendMailOnUserCreation(u.getEmail(), u.getUsername(), password);
        u.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
        u.setEnabled(1);
        u.setRoles("a:0:{}");
        us.addUser(u);
        
    }
    
    

}
