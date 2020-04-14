/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Images;
import entities.Produit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.control.Notifications;
import services.ImageService;
import services.ProduitService;

/**
 *
 * @author USER
 */
public class ContentProduitsAjoutController implements Initializable {

    @FXML
    private AnchorPane anchorpane ;
    @FXML
    private TextField reference; 
    @FXML
    private TextField nom;
    @FXML
    private JFXComboBox<String> categorie;
    @FXML
    private TextField prix;
    @FXML
    private TextField stock;
    @FXML
    private TextArea description;
    
    @FXML
    private AnchorPane ankpaneIM;
    
    @FXML
    private Button imageBtn; 
    
    @FXML
    private Label labelref;
    @FXML
    private Label labelnom;
    @FXML
    private Label labelprix;
    @FXML
    private Label labelstock;
    
    
    private List<File> files;
    
    private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\Products\\" ;
    
    private List<String> imageslist ;
    
    private ProduitService produitService ;
    
    private ImageService imageService ;
    
    private List<String> extList = Arrays.asList("jpg", "jpeg", "png", "gif");
    
        List<Integer> x = new ArrayList<>(Arrays.asList(30,300,30,300,30,300));
        List<Integer> y = new ArrayList<>(Arrays.asList(30,30,240,240,460,460));
        
    private int i ;
    
    private static MainController mc ;

    public static void init(MainController mainController){
        mc=mainController;
    }
    
    public void fillComboBoxCategorie() 
	{
		List<String> categorieList = new ArrayList<>();
		categorieList.add("Velo");
		categorieList.add("Piece de rechange");
		categorieList.add("Accessoire");
		categorie.getItems().setAll(categorieList);
                categorie.setValue("Accessoire");
	}
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageslist = new ArrayList<>();
        fillComboBoxCategorie();
        onBlur();
    }
    
    @FXML
    public void imageBtnAction() throws FileNotFoundException, IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des images pour le produit "+nom.getText());
        fileChooser.setInitialDirectory(new File("C:\\wamp64\\www\\integrationvelo\\web\\VTN"));
        Stage stage = (Stage)anchorpane.getScene().getWindow();
        files = fileChooser.showOpenMultipleDialog(stage);
        if (files != null){
        if (files.size() <= 6){
            i=0;
            ankpaneIM.getChildren().clear();
        for (File file : files){
            String ext = FilenameUtils.getExtension(file.getName());
            if (extList.contains(ext)){
            create_single_vbox(file.getAbsolutePath(),ankpaneIM);
            i++;
            }
        }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Maximum 6 images svp");
            alert.show();
        }  
    }
    }
    @FXML
    private void listedesproduitsbtn(){
        mc.PRODUITS();
    }
    @FXML
    public void AjouterProduitBtnAction(){
       //TODO
       if (!valide()){
       if (files!=null){
       Produit produit = new Produit();
       AjouterProduit(getAllFields());
       
       produit = getProduit(produit);
       
        try {
            DeplaceFiles(produit);
        } catch (IOException ex) {
            System.out.println(ex);
        }finally{
           notification("Confirmation","Produit bien ajoute");
        }
       }
       else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Il faut choisir au moins une image !");
           alert.show();
       } 
       
       mc.PRODUITS();
       
       }
       //redirect all products ;      
    }
    public void notification (String title,String text)
	{
		Notifications notif  = Notifications.create();
		notif.title(title);
		notif.text(text);
		notif.graphic(null);
		notif.hideAfter(Duration.seconds(2));
		notif.position(Pos.CENTER);
                notif.showConfirm();

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
    
    public void DeplaceFiles(Produit p) throws FileNotFoundException, IOException{
        if (files!=null){
            for (File file : files){
                System.out.println(file.getAbsoluteFile());
                System.out.println(file);
                
                  FileInputStream in = new FileInputStream(file);
                  String ext = "."+FilenameUtils.getExtension(file.getName());
                  String string = getAlphaNumericString();
                  FileOutputStream ou = new FileOutputStream(imagesPath+string+ext);         
                  System.out.println("IMAGE FEL WAMP : "+imagesPath+string+ext);
                  Images image = new Images();
                  image.setImage("Products/"+string+ext);
                   AjouterImageProduit(p,image);
                  
BufferedInputStream bin = new BufferedInputStream(in);
  BufferedOutputStream bou = new BufferedOutputStream(ou);
  int b=0;
  while(b!=-1){
   b=bin.read();
   bou.write(b);
  }
  bin.close();
  bou.close();
            }
        }
    }
    
    public Produit getAllFields(){
        /*LocalDate today = LocalDate.now();
        System.out.println("today : "+today);
        java.util.Date myDate = new java.util.Date(today.toString());
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());*/
        
        String Reference = reference.getText();
        String Nom = nom.getText();
        String Categorie = categorie.getValue().toString();
        String Stock = stock.getText();
        String Prix = prix.getText();
        String Description = description.getText();
        
        Produit p = new Produit();
                //p.setId(res.getInt(1));
                p.setReference(Reference);
                p.setName(Nom);
                p.setCategory(Categorie);
                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);
               // Double d =  Double.parseDouble(Prix);
               // p.setPrice(Double.parseDouble(df.format(Double.parseDouble(Prix))));
                p.setPrice(Double.parseDouble(Prix));
                p.setStock(Integer.parseInt(Stock));
                //p.setDate(sqlDate);
                p.setDescription(Description);
                
               /* System.out.println(Reference);
                System.out.println(Nom);
                System.out.println(Categorie);
                System.out.println(Float.parseFloat(Prix));
                System.out.println(Integer.parseInt(Stock));
                //System.out.println(sqlDate);
                System.out.println(Description);*/
               return p;
    }
    
    public void AjouterImageProduit(Produit p,Images image){
        //TODO
        imageService = new ImageService();
        imageService.AjouterImagesProduit(p,image);
    }
    
    public void AjouterProduit(Produit p){
        //TODO
        produitService = new ProduitService();
        produitService.AjouterProduit(p);
        
    }
    
    public Produit getProduit(Produit p){
        //TODO
        produitService = new ProduitService();
        //System.out.println(" JUST SEC LAST PRODU L_? "+produitService.getProduit(p).toString());
        return produitService.getProduit(p);
         
    }
    
    public void create_single_vbox(String image,AnchorPane ankpane){
        ImageView imageview = new ImageView("file:"+image);
        imageview.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
        imageview.setFitHeight(200);
        imageview.setFitWidth(240);
        
        AnchorPane vbox = new AnchorPane(imageview);

        vbox.setPrefHeight(200);
        vbox.setPrefWidth(240);
        
        vbox.setLayoutX(x.get(i));
        vbox.setLayoutY(y.get(i));
   
        ankpane.getChildren().add(vbox); 
        }
    
    public boolean valide(){
        ProduitService ps = new ProduitService();

		    	  if (reference.getText().isEmpty())
        {
            labelref.setText("*Référence est vide!");
            reference.setStyle("-fx-border-color: red;");return true;
        }
        else if (!reference.getText().matches("^[A-Z0-9 _]*[A-Z0-9][A-Z0-9 _]*$")) // a changerr
        {   labelref.setText("*Référence invalide !");
            reference.setStyle("-fx-border-color: red;");  return true;
        }
        else if (!ps.getAllProduit().keySet().stream().noneMatch(p->p.getReference().equals(reference.getText())))
        {   labelref.setText("*Référence déja existe !");
            reference.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            reference.setStyle("-fx-border-color: none;");
            labelref.setText("");
        }
                          
                          if (nom.getText().isEmpty())
        {
            labelnom.setText("*Nom est vide!");
            nom.setStyle("-fx-border-color: red;");return true;
        }
        else if (!nom.getText().matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")) 
        {   labelnom.setText("*Nom invalide !");
            nom.setStyle("-fx-border-color: red;");  return true;
        }
        else if (!ps.getAllProduit().keySet().stream().noneMatch(p->p.getName().equals(nom.getText())))
        {   labelnom.setText("*Nom déja existe !");
            nom.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            nom.setStyle("-fx-border-color: none;");
            labelnom.setText("");
        }
                          
                            if (prix.getText().isEmpty())
        {
            labelprix.setText("*prix est vide!");
            prix.setStyle("-fx-border-color: red;");return true;
        }
        else if (!prix.getText().matches("^(?=.{0,9}$)^[0-9]*(?:\\.[0-9]*)?$")) 
        {   labelprix.setText("*prix invalide !");
            prix.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            prix.setStyle("-fx-border-color: none;");
            labelprix.setText("");
        }
                            
                            if (stock.getText().isEmpty())
        {
            labelstock.setText("*stock est vide!");
            stock.setStyle("-fx-border-color: red;");return true;
        }
        else if (!Valid_4Digit(stock.getText()))  
        {   labelstock.setText("*stock invalide !");
            stock.setStyle("-fx-border-color: red;");  return true;
        }
        else {
            stock.setStyle("-fx-border-color: none;");
            labelstock.setText("");
        }
                          
        return false;
    }
    
    public void onBlur(){
        ProduitService ps = new ProduitService();
        
        reference.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	 if (reference.getText().isEmpty())
        {
            labelref.setText("*Référence est vide!");
            reference.setStyle("-fx-border-color: red;");
        }
        else if (!reference.getText().matches("^[A-Z0-9 _]*[A-Z0-9][A-Z0-9 _]*$")) // a changerr
        {   labelref.setText("*Référence invalide !");
            reference.setStyle("-fx-border-color: red;");  
        }
        else if (!ps.getAllProduit().keySet().stream().noneMatch(p->p.getReference().equals(reference.getText())))
        {   labelref.setText("*Référence déja existe !");
            reference.setStyle("-fx-border-color: red;");  
        }
        else {
            reference.setStyle("-fx-border-color: none;");
            labelref.setText("");
        }
	}
	});
        
        nom.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	 if (nom.getText().isEmpty())
        {
            labelnom.setText("*Nom est vide!");
            nom.setStyle("-fx-border-color: red;");
        }
        else if (!nom.getText().matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")) 
        {   labelnom.setText("*Nom invalide !");
            nom.setStyle("-fx-border-color: red;"); 
        }
        else if (!ps.getAllProduit().keySet().stream().noneMatch(p->p.getName().equals(nom.getText())))
        {   labelnom.setText("*Nom déja existe !");
            nom.setStyle("-fx-border-color: red;"); 
        }
        else {
            nom.setStyle("-fx-border-color: none;");
            labelnom.setText("");
        }
	}
	});
        
        prix.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	if (prix.getText().isEmpty())
        {
            labelprix.setText("*prix est vide!");
            prix.setStyle("-fx-border-color: red;");
        }
        else if (!prix.getText().matches("^(?=.{0,9}$)^[0-9]*(?:\\.[0-9]*)?$")) 
        {   labelprix.setText("*prix invalide !");
            prix.setStyle("-fx-border-color: red;");  
        }
        else {
            prix.setStyle("-fx-border-color: none;");
            labelprix.setText("");
        }
	}
	});
        
         stock.focusedProperty().addListener((ov, oldV, newV) -> {
		      if (!newV) { // focus lost
		    	if (stock.getText().isEmpty())
        {
            labelstock.setText("*stock est vide!");
            stock.setStyle("-fx-border-color: red;");
        }
        else if (!Valid_4Digit(stock.getText())) 
        {   labelstock.setText("*stock invalide !");
            stock.setStyle("-fx-border-color: red;");  
        }
        else {
            stock.setStyle("-fx-border-color: none;");
            labelstock.setText("");
        }
	}
	});
        
        
    }
    public static boolean Valid_4Digit (String input) {
		
		String emailRegex ="^[1-9]{1,4}$" ;
		
		Pattern emailPattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(input);
		
		return matcher.find() ;
	}
}
