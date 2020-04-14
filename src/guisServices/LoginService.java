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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
        @FXML
        private Label label_mdpo ;
        @FXML
        private AnchorPane anch;
        @FXML
        private StackPane stack;
        
        public static Stage stage ;
        
        private UserService userService ;
        private static List<User> ListUsers;
        
        private static User user;
        static boolean Lbtest = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       user=null;
       Lbtest=false; //n r a l
       userService = new UserService();
       ListUsers = userService.getAllUser();
       init_mdpo();
        //Connecting.setVisible(false);
        
        Username.setText("admin");
        Password.setText("admin");
        
        System.out.println("Login Controller");

    }
    
    public static List<User> getListUsers(){
        return ListUsers;
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
               
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/Main.fxml"));
                 Parent MainView2 = (Parent)loader.load();
                 Scene MainScene2 = new Scene(MainView2);
		
                Stage stage = new Stage (StageStyle.UNDECORATED);
		 
                 stage.setScene(MainScene2);
		 stage.centerOnScreen();
                 this.stage = stage;
		 stage.show();
    }
    
     private void LoadMpdO() throws IOException {
               
       Parent root = FXMLLoader.load(getClass().getResource("/guis/Login_mdpo_1.fxml"));
       Scene scene = label_mdpo.getScene();
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
    private void Cancel(ActionEvent event) {
        System.exit(0);
    }
    
    public void init_mdpo(){
        label_mdpo.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                LoadMpdO();
                
               /* Parent root = FXMLLoader.load(getClass().getResource("/guis/Login.fxml"));
        Scene scene = new Scene(root);
      //  primaryStage.getIcons().add(new Image("/images/logo.png"));
       // this.stage = primaryStage;
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();*/
        
            } catch (IOException ex) {
                Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        label_mdpo.styleProperty().bind(Bindings.when(label_mdpo.hoverProperty())
                                      .then("-fx-underline: true;")
                                      .otherwise(" -fx-underline: false;"));
    }
     
}
