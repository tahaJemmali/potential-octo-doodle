package guisServices;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.application.ConditionalFeature;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.UserService;

/**
 *
 * @author Benny In
 */
public class MainController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    
    static boolean flag = true;
    
    final String web_path = "http://localhost/integrationvelo/web/";
    
    @FXML
    private BorderPane border_pane;
    @FXML
    private TextField Searsh ;
    /*@FXML 
    private ImageView image_profile ;*/
    @FXML
    private Button btnClose ;
    @FXML
    private Button btnMin ;
    @FXML
    private  Label label ;
    @FXML
    private JFXButton open_sidebarbtn ;
    private static User CurrentUser ;
    
    //@FXML
    SidebarController sidebarController;
    
    @FXML
    private MenuButton menuBtn ;
    
    public BorderPane getBorder_pane()
    {
        return border_pane ;
    }
    
    public static void setCurrentUser(User u){
        CurrentUser=u;
    }
    
    public static User getCurrentUser(){
        return CurrentUser;
    }
    
    private static ContentHomeController chc ;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initBtns();    
        ContentProduitsModificationController.init(this);
        ContentProduitsAjoutController.init(this);
        
        //profil
        ProfilService.init(this);
        
        //sidebarController.init(this);
        /* login time update */
        CurrentUser = LoginService.getUser();
        UserService us = new UserService();
        us.UserLoggedIn(CurrentUser);
        /* */
        
        /* */
      //  UserService us = new UserService();
       // CurrentUser = us.getUser1(CurrentUser);
        /* */
        
        //image_profile.setImage(new Image(web_path+CurrentUser.getPhoto(),true));
        System.out.println(web_path+CurrentUser.getPhoto());
        initPp();
        makeStageDrageable();
        //Searsh.setText("Searsh...");
        try {
            Parent contentarea = FXMLLoader.load(getClass().getResource("/guis/ContentHome.fxml"));
            border_pane.setCenter(contentarea);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }        
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
            
            //*resize */
           // System.out.println(" Content Home Controller : "+chc);
            //System.out.println();
            //* */
            
           System.out.println("SBC : "+sbc);
            sbc.init(this);
            flag = false;
        } else {//sidebarController.init(this);
            border_pane.setLeft(null);
            flag = true;
        } 
        //*resize */
        chc.Resize(flag);
        //*resize */
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
        //
        open_sidebarbtn.fire();
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
    private void minimizeStage(ActionEvent evt) {
        ((Stage) border_pane.getScene().getWindow()).setIconified(true);
   
    }
    
    public void initBtns(){
        btnClose.setStyle(
  "-fx-background-radius: 5em; "+
           "-fx-min-width: 25px; " +
                "-fx-min-height: 25px; " +
                "-fx-max-width: 25px; " +
                "-fx-max-height: 25px;"
  );   btnMin.setStyle(
  "-fx-background-radius: 5em; "+
           "-fx-min-width: 25px; " +
                "-fx-min-height: 25px; " +
                "-fx-max-width: 25px; " +
                "-fx-max-height: 25px;"
  );
         FontAwesomeIconView min_icon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
        min_icon.setTranslateY(2);
         //view_icon.setStyle("-fx-fill : GREEN");
         min_icon.setSize("14px");

        FontAwesomeIconView delete_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        //delete_icon.setStyle("-fx-fill : #FF2800");
        delete_icon.setSize("14px");


        btnClose.setGraphic(delete_icon);
        btnMin.setGraphic(min_icon);
        
         btnClose.setOnAction(event->{
                         closeStage();                       
        });
         
          
         btnMin.setOnAction(event->{
                         minimizeStage(event);                       
        });

    }
    public void initPp (){
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //label.setStyle("-fx-font-weight: bold");
            //System.out.println("fzergfsgdf");
            menuBtn.fire();
        }
    });
        label.setText("  "+CurrentUser.getUsername());
        //MenuBtn
        ImageView imv = new ImageView(web_path+CurrentUser.getPhoto());
        imv.setFitWidth(50);
        imv.setFitHeight(50);
        imv.setTranslateX(-7);
        Rectangle clip = new Rectangle(
                imv.getFitWidth(), imv.getFitHeight()
            );
            clip.setArcWidth(50);
            clip.setArcHeight(50);
            imv.setClip(clip);

            // snapshot the rounded image.
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imv.snapshot(parameters, null);

            // remove the rounding clip so that our effect can show through.
            imv.setClip(null);

            // apply a shadow effect.
            imv.setEffect(new DropShadow(2, Color.BLACK));

            // store the rounded image in the imageView.
            imv.setImage(image);
    
menuBtn.setStyle(
  "-fx-background-radius: 5em; "+
  "-fx-background-repeat: no-repeat;"+
                "-fx-min-width: 53px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 53px; " +
                "-fx-max-height: 40px;"
  );
        menuBtn.setGraphic(imv);
        intiMenuItems();
    }
    
    public void intiMenuItems(){
        MenuItem profilItem = new MenuItem("Profil");
        MenuItem logoutItem = new MenuItem("Log out");
        
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        userIcon.setSize("14px");
        
        FontAwesomeIconView logoutIcon = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
        logoutIcon.setSize("14px");
        
        profilItem.setGraphic(userIcon);
        logoutItem.setGraphic(logoutIcon);
        
        menuBtn.getItems().addAll(profilItem,logoutItem);
        
        profilItem.setOnAction(event -> {
            try {
                openProfil();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
   
    
});
         logoutItem.setOnAction(event -> {
             closeStage();
});
        
    }
    
    public void openProfil() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/guis/Profil.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void profilUpdate(User user){
        
        label.setText("  "+user.getUsername());
        
        this.CurrentUser.setUsername(user.getUsername());
        this.CurrentUser.setPhoto(user.getPhoto());
        this.CurrentUser.setEmail(user.getEmail());
        this.CurrentUser.setPhone(user.getPhone());
        this.CurrentUser.setAddress(user.getAddress());
        
        //this.CurrentUser.setPassword(user.getPassword());
        
        ImageView imv = new ImageView(web_path+CurrentUser.getPhoto());
        imv.setFitWidth(50);
        imv.setFitHeight(50);
        imv.setTranslateX(-7);
        Rectangle clip = new Rectangle(
                imv.getFitWidth(), imv.getFitHeight()
            );
            clip.setArcWidth(50);
            clip.setArcHeight(50);
            imv.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imv.snapshot(parameters, null);
            imv.setClip(null);
            imv.setEffect(new DropShadow(2, Color.BLACK));
            imv.setImage(image);
        menuBtn.setGraphic(imv);
    }
    
    public static void init(ContentHomeController c){
        chc =c ;
    }

    public void EVENT() {
                try {
            Parent contentusers = FXMLLoader.load(getClass().getResource("/guis/event.fxml"));
            border_pane.setCenter(contentusers);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
