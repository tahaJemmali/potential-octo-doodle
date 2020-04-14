/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Images;
import entities.Produit;
import entities.User;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FilenameUtils;

import org.controlsfx.control.Notifications;
import services.UserService;
import utils.BCrypt;

/**
 *
 * @author USER
 */
public class ProfilService implements Initializable  {

   

    @FXML
    private Circle circleimg ;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField mdp;
    @FXML
    private TextField adress;
    @FXML
    private TextField tel;
    @FXML
    private JFXButton jfxb ;
    @FXML
    private Button usernamebtn;
    @FXML
    private Button emailbtn;
    @FXML
    private Button mdpbtn;
    @FXML
    private Button adressbtn;
    @FXML
    private Button telbtn;
    
    @FXML
    private Label labelusername;
    @FXML
    private Label labelemail;
    @FXML
    private Label labeltel;
    @FXML
    private Label labelmdp;
    @FXML
    private Label labeladress;
    
    private static User user ;
    
    private File file ;
    
    private List<String> extList = Arrays.asList("jpg", "jpeg", "png");

    final String web_path = "http://localhost/integrationvelo/web/";
    
    private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\uploads\\" ;

    private List <TextField> tfs = new ArrayList<>(Arrays.asList(username,email,mdp,adress,tel));
    
    private List <Button> buttons;
    
    private static MainController mc ;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user=MainController.getCurrentUser();
        initProfil();

    }
    
    public static void init(MainController mainController){
        mc=mainController;
    }
   
    
    public void editImageBtn(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Image de profil ");
        fileChooser.setInitialDirectory(new File("C:\\wamp64\\www\\integrationvelo\\web\\uploads"));
        Stage stage = (Stage)telbtn.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null){
            String ext = FilenameUtils.getExtension(file.getName());
            if (extList.contains(ext)){
                circleimg.setFill(new ImagePattern(new Image("file:"+file.getAbsolutePath())));
            }
            else{
            notification("Erreur ","Extension invalide !");
            file=null;
        } 
        }
    }
         
    
    
    public void initProfil(){
        Image imv = new Image(web_path+user.getPhoto());
        circleimg.setFill(new ImagePattern(imv));
        circleimg.setStroke(Color.WHITE); 
        circleimg.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
        ajustlabels();
        initChamps();
        initEditBtn();
        onBlur();
        
    }
    public void initEditBtn(){
        FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
        view_icon.setStyle("-fx-fill : BLACK");
        view_icon.setSize("100px");
        view_icon.setTranslateX(10);
        jfxb.setGraphic(view_icon);
        jfxb.setStyle("-fx-opacity: 0.3;");
        jfxb.setShape(circleimg);
        jfxb.setOnMouseEntered(e -> jfxb.setStyle("-fx-opacity: 0.8;"));
        jfxb.setOnMouseExited(e -> jfxb.setStyle("-fx-opacity: 0.23;"));
    }
    public void initChamps(){
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        mdp.setText("             ");
        adress.setText(user.getAddress());
        tel.setText(String.valueOf(user.getPhone()));
        initButtons();
        //valide();
    }
    public void initButtons(){
        buttons = new ArrayList<>(Arrays.asList(usernamebtn,emailbtn,mdpbtn,adressbtn,telbtn));
        for (int i=0;i<5;i++){
            FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            view_icon.setStyle("-fx-fill : BLACK");
            view_icon.setSize("25px");
            view_icon.setTranslateX(5);
            buttons.get(i).setGraphic(view_icon);
        }
        
        usernamebtn.setOnAction(e->{
        username.setDisable(false);
        //reset(username);
        });
        
        emailbtn.setOnAction(e->{
        email.setDisable(false);
        //reset(email);
        });
        
        mdpbtn.setOnAction(e->{
        mdp.setDisable(false);
        //reset(mdp);
        });
        
        adressbtn.setOnAction(e->{
        adress.setDisable(false);
        //reset(adress);
        });
        
        telbtn.setOnAction(e->{
        tel.setDisable(false);
        //reset(tel);
        });
        
        jfxb.setOnAction(e->editImageBtn());
    }

    
    public void notification (String title,String text)
	{
		Notifications notif  = Notifications.create();
		notif.title(title);
		notif.text(text);
		notif.graphic(null);
		notif.hideAfter(Duration.seconds(2));
		notif.position(Pos.CENTER);
		//notif.showWarning();
                notif.darkStyle();
                notif.showError();
                /*notif.onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("fahd");
                    }
                });*/
	}
    
    /*public void Valide()
	{ 
		username.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (!username.getText().isEmpty()) {
		    		  if(!username.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$")) 
                                  {notification("Erreur de saisie ","Username invalide!");
                                  username.setStyle("-fx-border-color: red;");
                                  }
					else{username.setStyle("-fx-border-color: none;");
                                        username.setDisable(true);
                                  }
		    	  }
		           }
		        });
}*/

    @FXML
    public void Exit(){
        ((Stage) username.getScene().getWindow()).close();
    }
    
    @FXML
    public void Modifier() throws IOException{
        if (!valide()){
        UserService us = new UserService();
        User u = new User();
        
        u.setId(user.getId());
        u.setUsername(username.getText());
        u.setEmail(email.getText());
        
        if (mdp.getText().equals("             "))
            u.setPassword(user.getPassword());
        else
            u.setPassword(BCrypt.hashpw(mdp.getText(),BCrypt.gensalt()));
        
        System.out.println("+"+mdp.getText()+"+");
        System.out.println(u.getPassword());
        
        u.setAddress(adress.getText());
        u.setPhone(Integer.parseInt(tel.getText()));
        
        if (file!=null)
            DeplaceFiles(u);     
        else
            u.setPhoto(user.getPhoto());
        
        us.Update_User(u);
        mc.profilUpdate(u);
        Exit();
        }
    }

        public void DeplaceFiles(User u) throws FileNotFoundException, IOException{
        if (file!=null){
            //for (File file : files){
                //System.out.println(file.getAbsoluteFile());
                //System.out.println(file);
                
                  FileInputStream in = new FileInputStream(file);
                  String ext = "."+FilenameUtils.getExtension(file.getName());
                  String string = getAlphaNumericString();
                  FileOutputStream ou = new FileOutputStream(imagesPath+string+ext);         
                  //System.out.println("IMAGE FEL WAMP : "+imagesPath+string+ext);
                  //Images image = new Images();
                  //image.setImage("Products/"+string+ext);
                  //AjouterImageProduit(p,image);
                     u.setPhoto("uploads/"+string+ext);
                  
  BufferedInputStream bin = new BufferedInputStream(in);
  BufferedOutputStream bou = new BufferedOutputStream(ou);
  int b=0;
  while(b!=-1){
  b=bin.read();
  bou.write(b);
  }
  bin.close();
  bou.close();
           // }
        }
    }
        public String getAlphaNumericString() 
    { int n=30;
          String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
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
        
        public boolean valide(){
UserService us = new UserService();

		    	  if (username.getText().isEmpty())
        {
            labelusername.setText("*Username est vide!");
            username.setStyle("-fx-border-color: red;");return true;
        }
        else if (!username.getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$"))
        {   labelusername.setText("*Username invalide !");
            username.setStyle("-fx-border-color: red;");  return true;
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->u.getUsername().equals(username.getText())))
        {   labelusername.setText("*Username déja existe !");
            username.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            username.setStyle("-fx-border-color: none;");
            labelusername.setText("");
        }
	
		    	  if (email.getText().isEmpty())
        {
            labelemail.setText("*E-mail est vide!");
            email.setStyle("-fx-border-color: red;");return true;
        }
        else if (Valid_Email(email.getText())==false)
        {   labelemail.setText("*E-mail invalide !");
            email.setStyle("-fx-border-color: red;");  return true;
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->u.getEmail().equals(email.getText())))
        {   labelemail.setText("*E-mail déja existe !");
            email.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            email.setStyle("-fx-border-color: none;");
            labelemail.setText("");
        }

         

		    	  if (mdp.getText().isEmpty())
        {
            labelmdp.setText("*Mot de passe est vide!");
            mdp.setStyle("-fx-border-color: red;");return true;
        }
        else if (mdp.getText().length()<8 || mdp.getText().length()>16 )
        {   labelmdp.setText("*Mot de passe invalide !");
            mdp.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            mdp.setStyle("-fx-border-color: none;");
            labelmdp.setText("");
        }

          

		    	  if (adress.getText().isEmpty())
        {
            labeladress.setText("*Adresse est vide!");
            adress.setStyle("-fx-border-color: red;");return true;
        }
        else if (!adress.getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$"))
        {   labeladress.setText("*Adresse invalide !");
            adress.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            adress.setStyle("-fx-border-color: none;");
            labeladress.setText("");
        }

           

		    	  if (tel.getText().isEmpty())
        {
            labeltel.setText("*Numero téléphone est vide!");
            tel.setStyle("-fx-border-color: red;");return true;
        }
        else if (!Valid_8Digit(tel.getText()))
        {   labeltel.setText("*Numero téléphone invalide !");
            tel.setStyle("-fx-border-color: red;");  return true;
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->String.valueOf(u.getPhone()).equals(tel.getText())))
        {   labeltel.setText("*Numero téléphone déja existe !");
            tel.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            tel.setStyle("-fx-border-color: none;");
            labeltel.setText("");
        }

            return false;
    }
    
    public void onBlur(){
        UserService us = new UserService();
        
        username.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (username.getText().isEmpty())
        {
            labelusername.setText("*Username est vide!");
            username.setStyle("-fx-border-color: red;");
        }
        else if (!username.getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$"))
        {   labelusername.setText("*Username invalide !");
            username.setStyle("-fx-border-color: red;");  
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->u.getUsername().equals(username.getText())))
        {   labelusername.setText("*Username déja existe !");
            username.setStyle("-fx-border-color: red;");  
        }
        else {
            username.setStyle("-fx-border-color: none;");
            labelusername.setText("");
        }
	}
	});
        
         email.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (email.getText().isEmpty())
        {
            labelemail.setText("*E-mail est vide!");
            email.setStyle("-fx-border-color: red;");
        }
        else if (Valid_Email(email.getText())==false)
        {   labelemail.setText("*E-mail invalide !");
            email.setStyle("-fx-border-color: red;");  
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->u.getEmail().equals(email.getText())))
        {   labelemail.setText("*E-mail déja existe !");
            email.setStyle("-fx-border-color: red;");  
        }
        else {
            email.setStyle("-fx-border-color: none;");
            labelemail.setText("");
        }
	}
	});
         
          mdp.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (mdp.getText().isEmpty())
        {
            labelmdp.setText("*Mot de passe est vide!");
            mdp.setStyle("-fx-border-color: red;");
        }
        else if (mdp.getText().length()<8 || mdp.getText().length()>16 )
        {   labelmdp.setText("*Mot de passe invalide !");
            mdp.setStyle("-fx-border-color: red;");  
        }
        else {
            mdp.setStyle("-fx-border-color: none;");
            labelmdp.setText("");
        }
	}
	});
          
           adress.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (adress.getText().isEmpty())
        {
            labeladress.setText("*Adresse est vide!");
            adress.setStyle("-fx-border-color: red;");
        }
        else if (!adress.getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){1,24}$"))
        {   labeladress.setText("*Adresse invalide !");
            adress.setStyle("-fx-border-color: red;");  
        }
        else {
            adress.setStyle("-fx-border-color: none;");
            labeladress.setText("");
        }
	}
	});
           
            tel.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	  if (tel.getText().isEmpty())
        {
            labeltel.setText("*Numero téléphone est vide!");
            tel.setStyle("-fx-border-color: red;");
        }
        else if (!Valid_8Digit(tel.getText()))
        {   labeltel.setText("*Numero téléphone invalide !");
            tel.setStyle("-fx-border-color: red;");  
        }
        else if (!us.getAllUser().stream().filter(u->u.getId()!=user.getId()).noneMatch(u->String.valueOf(u.getPhone()).equals(tel.getText())))
        {   labeltel.setText("*Numero téléphone déja existe !");
            tel.setStyle("-fx-border-color: red;");  
        }
        else {
            tel.setStyle("-fx-border-color: none;");
            labeltel.setText("");
        }
	}
	});
        
    }
    public static boolean Valid_Email (String input) {		
		String emailRegex ="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" ;
		Pattern emailPattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(input);
		return matcher.find() ;
	}
    
    	public static boolean Valid_8Digit (String input) {
		
		String emailRegex ="^[0-9]{8}$" ;
		
		Pattern emailPattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(input);
		
		return matcher.find() ;
	}
        public void ajustlabels(){
        labelusername.setTranslateX(-170);
        labelusername.setMinWidth(300);
        labelmdp.setTranslateX(-135);
        labelmdp.setMinWidth(300);
        labeltel.setTranslateX(-100);
        labeltel.setMinWidth(300);
        labeladress.setTranslateX(-170);
        labeladress.setMinWidth(300);
        labelemail.setTranslateX(-170);
        labelemail.setMinWidth(300);
    }
}
