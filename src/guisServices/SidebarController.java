package guisServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import guisServices.MainController ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SidebarController implements Initializable {

    @FXML
    private VBox sidebar;      
    @FXML 
    private Label label ;
    
    private MainController mc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("intittt : "+mc);
        System.out.println(" this "+this);
        
    }   
    
    public void init(MainController mainController) {
		mc = mainController;
	}
    
    @FXML
    private void Logout(ActionEvent event) throws IOException {  
        mc.Logout(event);           
    }
    
    @FXML
    private void open_ContentHome(ActionEvent event) {
        mc.HOME();           
    }
    
    @FXML
    private void open_ContentUsers(ActionEvent event) {
        mc.USERS();  	              
    }
        @FXML
    private void open_ContentEvent(ActionEvent event) {
        mc.EVENT();  	              
    }
    
}
