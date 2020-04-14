/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.control.Notifications;
import services.UserService;
import utils.BCrypt;
import utils.Mail;

/**
 *
 * @author USER
 */
public class AddUserController implements Initializable {

    @FXML
    private Circle circleimg ;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField adress;
    @FXML
    private TextField tel;
    @FXML
    private JFXComboBox role;
    @FXML
    private JFXButton jfxb ;
    //@FXML
    
    @FXML
    private Label labelusername;
    @FXML
    private Label labelemail;
    @FXML
    private Label labeltel;
    @FXML
    private Label labeladress;
    
    private static User u = new User() ;
    
    private File file ;
    
    private List<String> extList = Arrays.asList("jpg", "jpeg", "png");

    final String web_path = "http://localhost/integrationvelo/web/";
    
    private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\uploads\\" ;

    private static MainController mc ;
    
    FileChooser fileChooser = new FileChooser();
    
    private String pass ;
    
    private  ContentUserController cuc ;
    
    public void init(ContentUserController editUserController){
        cuc=editUserController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillComboBox();
        initProfilImage();
        jfxb.setOnAction(e->editImageBtn());
        ajustlabels();
        onBlur();
    }
    
       public void fillComboBox() 
	{
		List<String> categorieList = new ArrayList<>();
		categorieList.add("Admin");
                categorieList.add("Super Admin");
		role.getItems().setAll(categorieList);
                role.setValue("Admin");
	}
       public void initProfilImage(){
        Image imv = new Image(web_path+"uploads/default.jpg");
        circleimg.setFill(new ImagePattern(imv));
        circleimg.setStroke(Color.WHITE); 
        circleimg.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
        initEditBtn();
    }
        public void initEditBtn(){
        FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
        view_icon.setStyle("-fx-fill : BLACK");
        view_icon.setSize("100px");
        view_icon.setTranslateX(13);
        jfxb.setGraphic(view_icon);
        jfxb.setStyle("-fx-opacity: 0.1;");
        jfxb.setShape(circleimg);
        jfxb.setOnMouseEntered(e -> jfxb.setStyle("-fx-opacity: 0.8;"));
        jfxb.setOnMouseExited(e -> jfxb.setStyle("-fx-opacity: 0.1;"));
    }
        public void editImageBtn(){
        fileChooser.setTitle("Image de profil ");
        fileChooser.setInitialDirectory(new File("C:\\wamp64\\www\\integrationvelo\\web\\uploads"));
        Stage stage = (Stage)circleimg.getScene().getWindow();
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
        public void notification (String title,String text)
	{
		Notifications notif  = Notifications.create();
		notif.title(title);
		notif.text(text);
		notif.graphic(null);
		notif.hideAfter(Duration.seconds(2));
		notif.position(Pos.CENTER);
                notif.darkStyle();
                notif.showError();

	}
        @FXML
        public void Annuler(){
            ((Stage)circleimg.getScene().getWindow()).close();
        }
        @FXML
        public void Enregistrer() throws IOException{
            //verif champs
            if (!valide()){
            //recuperer
            
            u.setUsername(username.getText());
            u.setEmail(email.getText());
            u.setAddress(adress.getText());
            u.setPhone(Integer.parseInt(tel.getText()));
            
            //roles 
            if (role.getValue().equals("Admin"))
                u.setRoles("a:1:{i:0;s:10:\"ROLE_ADMIN\";}");
            else if (role.getValue().equals("Super Admin"))
                u.setRoles("a:2:{i:0;s:10:\"ROLE_ADMIN\";i:1;s:16:\"ROLE_SUPER_ADMIN\";}");
                u.setPhoto("uploads/default.jpg");
                DeplaceFiles(u); 
                    
            pass=getAlphaNumericString();
            //send pass to u.getmail
            new Thread(this::myBackgroundTask).start();
            u.setPassword(BCrypt.hashpw(pass,BCrypt.gensalt()));
            
            UserService us = new UserService();
            us.AddUser(u);
            
            //update table in ContentUser
            update();
            
            Annuler();
            
            
            }
        }
         public String getAlphaNumericString() 
    { int n=8;
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
         
         public void myBackgroundTask(){
        Mail.sendMail_fp(email.getText(),pass);
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
         public void update(){
        //System.out.println("Edit + this"+cuc);
        cuc.data.clear();
        UserService us = new UserService();
        
              us.getAllUser().stream().filter(u->u.getRoles().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")).forEach((u)->{
           User user = new User();
           
           user.setId(u.getId());
           user.setUsername(u.getUsername());
           user.setEmail(u.getEmail());
           user.setPhone(u.getPhone());
           user.setAddress(u.getAddress());
           user.setLast_login(u.getLast_login());
           user.setRoles(u.getRoles());
           user.setPhoto(u.getPhoto());
           user.setPassword(null);
           user.setEnabled(u.getEnabled());
           
           cuc.data.add(user);
       });
       
		 cuc.username.setCellValueFactory(new PropertyValueFactory<> ("username"));
		 cuc.email.setCellValueFactory(new PropertyValueFactory<> ("email"));
		 cuc.tel.setCellValueFactory(new PropertyValueFactory<> ("phone"));
		 cuc.adress.setCellValueFactory(new PropertyValueFactory<> ("address"));
		 cuc.dercnx.setCellValueFactory(new PropertyValueFactory<> ("last_login"));

                 cuc.username.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.1));
                 cuc.email.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.25));
                 cuc.tel.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.12));
                 cuc.adress.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.25));
                 cuc.dercnx.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.15));
                 cuc.Action.prefWidthProperty().bind(cuc.TableUsersView.widthProperty().multiply(0.08));
                 
                 
                 Callback<TableColumn<User,String>,TableCell<User,String>> cellFactory = (param)->{
			 final TableCell<User,String> cell=new TableCell<User,String>(){
				 @Override
				 public void updateItem(String item,boolean empty) {
					 super.updateItem(item,empty);
					 if (empty) {
						 setGraphic(null);
						 setText(null);
					 }
                                         else {
                                            FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
                                            view_icon.setStyle("-fx-fill : BLACK");
                                            view_icon.setSize("20px");
        
                                                 final Button viewButton = new Button();
                                                 viewButton.setGraphic(view_icon);
						 setGraphic(viewButton);
						 setText(null);
                                                 viewButton.setOnAction(e->{
                                                     User u = getTableView().getItems().get(getIndex());
                                                     cuc.user=u;
                                                     
                                                try {
                                                            FXMLLoader loader = new FXMLLoader (getClass().getResource("/guis/EditUser.fxml"));
                                                            Parent root = loader.load();
                                                            EditUserController euc = (EditUserController) loader.getController();
                                                            euc.init(cuc);
                                                            System.out.println("YAAAA"+euc);
                                                           
                                                            Scene scene = new Scene(root);
                                                            Stage primaryStage = new Stage();
                                                            primaryStage.setAlwaysOnTop(true);
                                                            primaryStage.initModality(Modality.APPLICATION_MODAL);
                                                            primaryStage.setScene(scene);
                                                            primaryStage.show();
                                                    
                                                } catch (IOException ex) {
                                                    Logger.getLogger(ContentUserController.class.getName()).log(Level.SEVERE, null, ex);
                                                }
        
                                                 });
					 }
				 };	 
			 };
			 return cell;
		 };
                cuc.Action.setCellFactory(cellFactory);
                cuc.TableUsersView.setItems(cuc.data);
                cuc.searsh();
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
        else if (!us.getAllUser().stream().noneMatch(u->u.getUsername().equals(username.getText())))
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
        else if (!us.getAllUser().stream().noneMatch(u->u.getEmail().equals(email.getText())))
        {   labelemail.setText("*E-mail déja existe !");
            email.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            email.setStyle("-fx-border-color: none;");
            labelemail.setText("");
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
        else if (!us.getAllUser().stream().noneMatch(u->String.valueOf(u.getPhone()).equals(tel.getText())))
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
        else if (!us.getAllUser().stream().noneMatch(u->u.getUsername().equals(username.getText())))
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
        else if (!us.getAllUser().stream().noneMatch(u->u.getEmail().equals(email.getText())))
        {   labelemail.setText("*E-mail déja existe !");
            email.setStyle("-fx-border-color: red;");  
        }
        else {
            email.setStyle("-fx-border-color: none;");
            labelemail.setText("");
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
        else if (!us.getAllUser().stream().noneMatch(u->String.valueOf(u.getPhone()).equals(tel.getText())))
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
    public void ajustlabels(){
        labelusername.setTranslateX(-170);
        labelusername.setMinWidth(300);
        labeltel.setTranslateX(-160);
        labeltel.setMinWidth(300);
        labeladress.setTranslateX(-170);
        labeladress.setMinWidth(300);
        labelemail.setTranslateX(-170);
        labelemail.setMinWidth(300);
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
}
