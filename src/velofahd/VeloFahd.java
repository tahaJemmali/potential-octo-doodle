/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velofahd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.MyConnection;

/**
 *
 * @author USER
 */
public class VeloFahd extends Application {
 
    public static Stage stage = null;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        //changed here 1
        /*Parent root = FXMLLoader.load(getClass().getResource("/guis/Login.fxml"));
		Scene scene = new Scene (root);
		primaryStage.setTitle("Velo.tn");
		primaryStage.centerOnScreen();
		//primaryStage.getIcons().add(new Image("/application/biat.jpg"));        
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
                this.stage = primaryStage;
               
		primaryStage.show();*/
        
        //with here 1
        //System.out.println("^(?=.{0,4}$)^[1-9]\\d*$");
        Parent root = FXMLLoader.load(getClass().getResource("/guis/Login.fxml"));

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        this.stage = primaryStage;
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //with here 1    
        
        /*Parent root = FXMLLoader.load(getClass().getResource("/guis/Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.stage = primaryStage;
        primaryStage.show();*/
	
        /**/
       /* List<Integer> dl = new ArrayList<>(Arrays.asList(5,7,8,9,13,15,16,17));
       
        for (int i=0;i<dl.size();i++)
        {
           double a =   Math.ceil(dl.get(i) / 8);
           
           System.out.println(dl.get(i)+" "+a);
        }*/
        
        /**/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
