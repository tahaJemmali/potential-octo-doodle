package guisServices;

import entities.Produit;
import entities.User;
import guisServices.LoginService;
import java.io.File;
import velofahd.VeloFahd;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Benny In
 */
public class MainController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    
    boolean flag = true;
    
    final String web_path = "http://localhost/integrationvelo/web/";
    
    @FXML
    private BorderPane border_pane;
    @FXML
    private TextField Searsh ;
    @FXML 
    private ImageView image_profile ;
    
    private static User CurrentUser ;
    
    //@FXML
    SidebarController sidebarController;
    
    public BorderPane getBorder_pane()
    {
        return border_pane ;
    }
    
    public static void setCurrentUser(User u){
        CurrentUser=u;
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //sidebarController.init(this);
        CurrentUser = LoginService.getUser();
        image_profile.setImage(new Image(web_path+CurrentUser.getPhoto(),true));
        System.out.println(web_path+CurrentUser.getPhoto());
        makeStageDrageable();
        //Searsh.setText("Searsh...");
        try {
            Parent contentarea = FXMLLoader.load(getClass().getResource("/guis/ContentHome.fxml"));
            border_pane.setCenter(contentarea);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //sidebarController.init(this);
PRODUITS(); // s b d

    }

    private void makeStageDrageable() {
        
        border_pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        border_pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LoginService.stage.setX(event.getScreenX() - xOffset);
                LoginService.stage.setY(event.getScreenY() - yOffset);
                LoginService.stage.setOpacity(0.7f);
            }
        });
        border_pane.setOnDragDone((e) -> {
            LoginService.stage.setOpacity(1.0f);
        });
        border_pane.setOnMouseReleased((e) -> {
            LoginService.stage.setOpacity(1.0f);
        });

    }
    
     @FXML
    private void trytoinit(ActionEvent event)  {
        System.out.println("INTIED : "+this);
        sidebarController.init(this);
    }
    
    @FXML
    private void open_sidebar(ActionEvent event) throws IOException {
        //sidebarController.init(this);
        //sidebarController.init(this);
        //USERS();
        BorderPane border_pane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        if (flag == true) {//sidebarController.init(this);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/Sidebar.fxml"));
            Parent sidebar = loader.load();
            SidebarController sbc  = (SidebarController) loader.getController();
            
            border_pane.setLeft(sidebar);
            
           System.out.println("SBC : "+sbc);
            sbc.init(this);
            flag = false;
        } else {//sidebarController.init(this);
            border_pane.setLeft(null);
            flag = true;
        } 
    }
    
    public String test2()
    {
        return "TEST2";
    }
    
    
    public void USERS()
    {   
        //Searsh.setText("TAHAHAHAHAHAHAH");
        try {
            Parent contentusers = FXMLLoader.load(getClass().getResource("/guis/ContentUsers.fxml"));
            border_pane.setCenter(contentusers);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void HOME()
    {   
        try {
            Parent contentusers = FXMLLoader.load(getClass().getResource("/guis/ContentHome.fxml"));
            border_pane.setCenter(contentusers);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void PRODUITS()
    {   
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/ContentProduits.fxml"));
            Parent contentproduit = loader.load();
            ContentProduitsController cpc = (ContentProduitsController) loader.getController();
            cpc.init(this);
            border_pane.setCenter(contentproduit);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void AJOUTER_PRODUITS()
    {   
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/ContentProduitsAjout.fxml"));
            Parent contentusers = loader.load();
            border_pane.setCenter(contentusers);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void MODIFIER_PRODUIT(Produit p)
    {       //set produit a modifier
            ContentProduitsModificationController.setProduit(p);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/ContentProduitsModification.fxml"));
            Parent contentusers = loader.load();
            

            
            border_pane.setCenter(contentusers);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Logout(ActionEvent event) throws IOException
    {   
        Parent MainView = FXMLLoader.load(getClass().getResource("/guis/Login.fxml"));
        CurrentUser=null;
        closeStage();
        Scene MainScene = new Scene(MainView);
	Stage MainWindow = new Stage();
	MainWindow.setScene(MainScene);
	MainWindow.centerOnScreen();
        
	MainWindow.show();
       
    }
    private void closeStage() {
        ((Stage) border_pane.getScene().getWindow()).close();
    }
    

    
    
}
