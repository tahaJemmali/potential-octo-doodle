/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utils.Mail;
//import utils.SendMail;

/**
 *
 * @author USER
 */
public class LoginService_mdpo_1 implements Initializable {

        @FXML
        private AnchorPane anch;
        @FXML
        private StackPane stack;
        @FXML
        private JFXButton suivant;
        @FXML
        private JFXButton retour;
        @FXML
        private JFXTextField email;
        
        private static String code;
        
        private static User user = new User() ;
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        email.setText("fahd.larayedh@gmail.com");
        code=getAlphaNumericString();
        System.out.println(code);
    }
    
    @FXML 
    public void Suivant() throws Exception{
        //TO DO verification mail 
        user.setEmail(email.getText());
        
        if(LoginService.getListUsers().stream().map(x->x.getEmail()).anyMatch(x->x.equals(email.getText())))
        {   
            System.out.println("Correct credentials");
            email.getStyleClass().add("Correct-credentials");
            new Thread(this::myBackgroundTask).start();
            //Mail.sendMail(email.getText(),code);
        //TO DO
        LoadMpd2();
        }
        else
        {   System.out.println("wrong-credentials");
            email.getStyleClass().add("wrong-credentials");
        }
        
         
    }
    
    public void myBackgroundTask(){
        Mail.sendMail(email.getText(),code);
    }
    public static User getUser(){
        return user ;
    }
    
    public static String getCode(){
        return code ;
    }
    
    @FXML 
    public void Retour() throws IOException{
        LoadLogin();
    }
    
    private void LoadLogin() throws IOException {
               
       Parent root = FXMLLoader.load(getClass().getResource("/guis/Login.fxml"));
       Scene scene = suivant.getScene();
       root.translateXProperty().set(scene.getHeight());
       stack.getChildren().add(root);
       Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            stack.getChildren().remove(anch);
        });
        timeline.play();
    }
    public String getAlphaNumericString() 
    { int n=6;
          String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    /*+ "abcdefghijklmnopqrstuvxyz"*/; 
  
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
    
    private void LoadMpd2() throws IOException {
               
       Parent root = FXMLLoader.load(getClass().getResource("/guis/Login_mdpo_2.fxml"));
       Scene scene = suivant.getScene();
       root.translateXProperty().set(scene.getHeight());
       stack.getChildren().add(root);
       Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            stack.getChildren().remove(anch);
        });
        timeline.play();
    }
}
