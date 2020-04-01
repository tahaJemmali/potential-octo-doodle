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
                System.out.println("wew"+this.mc);
                        System.out.println(" this "+this);
                //mc.USERS();
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
    private void open_ContentProduits(ActionEvent event) {
        mc.PRODUITS();           
    }
    
    
    @FXML
    private void open_ContentUsers(ActionEvent event) {
        System.out.println(" btn clicked "+this.mc);
        System.out.println(" this "+this);
        
        mc.USERS();
        
        //mc.getBorder_pane();
        //if (mc.getClass()!=null)
        //System.out.println(" main controller class check ? :" +mc.getClass());
        //try {
           /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/Main.fxml"));
            //BorderPane bp = loader.load(getClass().getResourceAsStream("/guis/Main.fxml"));
            Parent main = (Parent) loader.load();
           MainController mc  = (MainController) loader.getController();
           mc.USERS();*/
            //Parent contentarea = FXMLLoader.load(getClass().getResource("/guis/ContentUsers.fxml"));
           // mc.getBorder_pane().setCenter(contentarea);
//border_pane = mc.test();
            
            //contentusers.getClass();
          //  MainController controller = FXMLloader.getController();
 
        //} catch (IOException ex) {
            //System.out.println(ex);
          //  Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        //}
        	              
    }
    
}


/*

FXMLLoader loader = new FXMLLoader();
            BorderPane bp = loader.load(getClass().getResourceAsStream("/guis/Main.fxml"));
            //Parent main = (Parent) loader.load();
           MainController mc  = (MainController) loader.getController();
           //mc.USERS();

*/