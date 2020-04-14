/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.User;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import services.UserService;
import utils.BCrypt;

/**
 *
 * @author USER
 */
public class LoginService_mdpo_3 implements Initializable {
        
    @FXML
        private AnchorPane anch;
        @FXML
        private StackPane stack;
        @FXML
        private JFXButton suivant;
        @FXML
        private JFXButton retour;
        @FXML
        private JFXPasswordField mdp;
        @FXML
        private JFXPasswordField mdp2;
        @FXML
        private Label label ;
        @FXML
        private Label labelEr ;
        
        
        private static User user ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = LoginService_mdpo_1.getUser();
        initLabel();
    }
    
    public void initLabel(){
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        userIcon.setSize("25px");
        userIcon.setTranslateX(15);
        label.setGraphic(userIcon);
        label.setText("     "+user.getEmail());
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
    
    @FXML 
    public void Suivant() throws Exception{
        // TO DO champs passwords a verifie
        
        if (mdp.getText().equals(mdp2.getText()) && (mdp.getText().length()>8 && mdp.getText().length()<16) )
        {
        
        user.setPassword(BCrypt.hashpw(mdp.getText(), BCrypt.gensalt()));
        UserService us = new UserService();
        us.Update_UserPassword(user);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(user.getEmail()+" mot de passe changé ! "/*+mdp.getText()*/);
        alert.show();
            
        LoadLogin();
        }
        else{
            mdp.getStyleClass().add("wrong-credentials");
            mdp2.getStyleClass().add("wrong-credentials");
        }
        
    }
    
}
