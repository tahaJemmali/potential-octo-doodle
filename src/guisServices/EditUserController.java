/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.User;
import static guisServices.ContentUserController.user;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
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
import org.controlsfx.control.action.Action;
import services.UserService;
import utils.BCrypt;

/**
 *
 * @author USER
 */
public class EditUserController implements Initializable {

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
    private ComboBox role;
    @FXML
    private CheckBox enable;
    
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
    
    private static User user  ;
    
    private File file ;
    
    private List<String> extList = Arrays.asList("jpg", "jpeg", "png");

    final String web_path = "http://localhost/integrationvelo/web/";
    
    private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\uploads\\" ;

    private List <TextField> tfs = new ArrayList<>(Arrays.asList(username,email,mdp,adress,tel));
    
    private List <Button> buttons;
    
    private  ContentUserController cuc ;

    private boolean Check ;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //user=MainController.getCurrentUser();
        Check=false;
        user = ContentUserController.user;
        initProfil();
        fillComboBox();
     
    }
    
    public void init(ContentUserController editUserController){
        cuc=editUserController;
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
        initChamps();
        ajustlabels();
        initEditBtn();
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
        
        if (user.getRoles().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}"))
        role.setValue("Admin");
        else if (user.getRoles().equals("a:2:{i:0;s:10:\"ROLE_ADMIN\";i:1;s:16:\"ROLE_SUPER_ADMIN\";}"))
        role.setValue("Super Admin");
        
        if (user.getEnabled()==1)
            enable.setSelected(true);
        else
            enable.setSelected(false);
        
        initButtons();
        onBlur();
    }
    public void fillComboBox() 
	{
		List<String> categorieList = new ArrayList<>();
		categorieList.add("Admin");
                categorieList.add("Super Admin");
		role.getItems().setAll(categorieList);
                
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
            u.setPassword(us.getPassword_fromDB(user).getPassword());
        else
            u.setPassword(BCrypt.hashpw(mdp.getText(),BCrypt.gensalt()));
        
        u.setAddress(adress.getText());
        u.setPhone(Integer.parseInt(tel.getText()));
        
        if (role.getValue().equals("Admin"))
                u.setRoles("a:1:{i:0;s:10:\"ROLE_ADMIN\";}");
        else if (role.getValue().equals("Super Admin"))
                u.setRoles("a:2:{i:0;s:10:\"ROLE_ADMIN\";i:1;s:16:\"ROLE_SUPER_ADMIN\";}");
              
        if (enable.isSelected())
        u.setEnabled(1);
            else
            u.setEnabled(0);
        
        
        if (file!=null)
            DeplaceFiles(u);     
        else
            u.setPhoto(user.getPhoto());
        
        us.Modifier_User(u);
       // mc.profilUpdate(u);
        Exit();
        
        
        //update table in ContentUser
        update();
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
                                                            //Parent root = FXMLLoader.load(getClass().getResource("/guis/EditUser.fxml"));
                                                            FXMLLoader loader = new FXMLLoader (getClass().getResource("/guis/EditUser.fxml"));
                                                            Parent root = loader.load();
                                                            EditUserController euc = (EditUserController) loader.getController();
                                                            euc.init(cuc);
                                                            //System.out.println("YAAAA"+euc);
                                                           
                                                            Scene scene = new Scene(root);
                                                            Stage primaryStage = new Stage();
                                                            //primaryStage.setAlwaysOnTop(true);
                                                            //primaryStage.initModality(Modality.APPLICATION_MODAL);
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
               
                 
                 
		//N.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(TableUsersView.getItems().indexOf(column.getValue())));
               // Ne.setSortable(false);
               // Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(TableUsersView.getItems().indexOf(column.getValue())));

                //Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(TableUsersView.getItems().indexOf(column.getValue())+1));
                //cuc.Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(cuc.TableUsersView.getItems().indexOf(column.getValue())));
                //Ne.setCellFactory(cellFactory2);
                cuc.Action.setCellFactory(cellFactory);
                cuc.TableUsersView.setItems(cuc.data);
                cuc.searsh();
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
        

    @FXML
    public void SupprimerUser () { 
    Alert alert = new Alert(AlertType.CONFIRMATION);
    DialogPane dialogPane = alert.getDialogPane();
    GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
    grid.setStyle("-fx-background-color: #e2e2e2; ");
alert.setTitle("Confirmation ");
alert.setHeaderText("Confirmation");
alert.setContentText("Voulez-vous vraiment supprimer cet utilisateur ?");
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
            UserService us = new UserService();
            
            us.delete_User(user);
            
            Exit();
            update();

} 
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
