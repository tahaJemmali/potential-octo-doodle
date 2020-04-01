/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.UserService;
import utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import velofahd.VeloFahd;
/**
 *
 * @author USER
 */
public class LoginService implements Initializable {

        @FXML
	private JFXTextField Username ;
	@FXML
	private JFXPasswordField Password ;

        
        public static Stage stage ;
        
        private UserService userService ;
        private List<User> ListUsers;
        private static User user;
        static boolean Lbtest = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       user=null;
       
       userService = new UserService();
       ListUsers = userService.getAllUser();
               
        Username.setText("taha");
        Password.setText("123");
        
    }
    
     private void closeStage() {
        ((Stage) Username.getScene().getWindow()).close();
    }
     
    @FXML
    public void Login (ActionEvent event) throws IOException, InterruptedException{      
        String username = Username.getText();
        String password = Password.getText();
        user = new User(username,password);
        for (int i=0;i<ListUsers.size();i++)
		{
                    if (ListUsers.get(i).equals(user))
                    {
                        user = ListUsers.get(i) ;
                        System.out.println(" ROLES : "+user.getRoles());
                        //test.setText("Login Success");
                        closeStage();
                        LoadLogin(event);
                        Lbtest = true;
                    }
		}
        if(!Lbtest)
        {   
            Username.getStyleClass().add("wrong-credentials");
            Password.getStyleClass().add("wrong-credentials");
        }

    }
    
    public static User getUser()
    {
        return user;
    }

    private void LoadLogin(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/Main.fxml"));
                 Parent MainView2 = (Parent)loader.load();
                 Scene MainScene2 = new Scene(MainView2);
		
                Stage stage = new Stage (StageStyle.UNDECORATED);
		 
                 stage.setScene(MainScene2);
		 stage.centerOnScreen();
                 this.stage = stage;
		 stage.show();
    }
    

    @FXML
    private void Cancel(ActionEvent event) {
        System.exit(0);
    }
     
}
