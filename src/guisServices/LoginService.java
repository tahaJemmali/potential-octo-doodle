/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Produit;
import entities.User;
import entities.Images;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import services.ProduitService;
import velofahd.VeloFahd;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author USER
 */
public class LoginService implements Initializable {

        @FXML
	private JFXTextField Username ;
	@FXML
	private JFXPasswordField Password ;
        @FXML 
        private Button cancel ;
        
        public static Stage stage ;
        
        private UserService userService ;
        private List<User> ListUsers;
        
        private static User user;
        static boolean Lbtest = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       user=null;
       Lbtest=false; //n r a l
       userService = new UserService();
       ListUsers = userService.getAllUser();
       
        //Connecting.setVisible(false);
        
        Username.setText("fahd");
        Password.setText("123");
        
        System.out.println("Login Controller");
        
    }
    
     private void closeStage() {
        ((Stage) Username.getScene().getWindow()).close();
    }
     
    @FXML
    public void Login () throws IOException, InterruptedException{      
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
                        LoadLogin();
                        Lbtest = true;
                    }
		}
        if(!Lbtest)
        {   System.out.println("wrong-credentials");
           // username.getStyleClass().add("wrong-credentials");
            Username.getStyleClass().add("wrong-credentials");
            Password.getStyleClass().add("wrong-credentials");
        }

    }
    
    public static User getUser()
    {
        return user;
    }

    private void LoadLogin() throws IOException {
               
        //change 2 here
//                VeloFahd.stage.initStyle(StageStyle.UNDECORATED);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/Main.fxml"));
                 Parent MainView2 = (Parent)loader.load();
                 Scene MainScene2 = new Scene(MainView2);
                 //MainController mc  = (MainController) loader.getController();
                 //mc.setUser(user);
		
		//Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); 
                Stage stage = new Stage (StageStyle.UNDECORATED);
                //MainWindow2.initStyle(StageStyle.UNDECORATED);
		 
                 stage.setScene(MainScene2);
		 stage.centerOnScreen();
                 this.stage = stage;
                 //this.stage = MainWindow2;
                 //MainWindow2.initStyle(StageStyle.UNDECORATED);
		 stage.show();
                
                //with 2 here
            /*Parent parent = FXMLLoader.load(getClass().getResource("/guis/Main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            this.stage = stage;
            stage.show();*/
            
            //LibraryAssistantUtil.setStageIcon(stage);
                //with 2 here
    }
    
   /* @FXML
    private void LoginButtonAction(ActionEvent event) {
        String uname = StringUtils.trimToEmpty(username.getText());
        String pword = DigestUtils.shaHex(password.getText());

        if (uname.equals(preference.getUsername()) && pword.equals(preference.getPassword())) {
            closeStage();
            loadMain();
            LOGGER.log(Level.INFO, "User successfully logged in {}", uname);
        }
        else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }*/

    @FXML
    private void Cancel(ActionEvent event) {
        System.exit(0);
    }
     
}
