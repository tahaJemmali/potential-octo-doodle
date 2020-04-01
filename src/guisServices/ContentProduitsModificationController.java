/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

import com.jfoenix.controls.JFXComboBox;
import entities.Images;
import entities.Produit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import services.ImageService;
import services.ProduitService;

/**
 *
 * @author USER
 */
public class ContentProduitsModificationController implements Initializable {

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
    private Label labelProduit ;
    
    @FXML
    private Button imageBtn; 
    @FXML
    private AnchorPane ankpaneIM;
    
    private List<File> files;
    
    private String imagesPath ="C:\\wamp64\\www\\integrationvelo\\web\\" ;
    
    private List<String> imageslist ;
    
    private ProduitService produitService ;
    
    private ImageService imageService ;
    
    private static Produit produit ;
    
    private List<String> extList = Arrays.asList("jpg", "jpeg", "png", "gif");
    
        List<Integer> x = new ArrayList<>(Arrays.asList(30,300,30,300,30,300));
        List<Integer> y = new ArrayList<>(Arrays.asList(30,30,240,240,460,460));
        
    private int i ;
    
    public static void setProduit(Produit p){
        produit=p;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        if (produit!=null)
        {setChamps();
        }
    }
    
    private void setChamps(){
    
    labelProduit.setText(produit.getReference());
    String Prix  = Double.toString(produit.getPrice());
    String Stock = String.valueOf(produit.getStock());

        
    reference.setText(produit.getReference()); 
    nom.setText(produit.getName()); 
    categorie.setValue(produit.getCategory()); 
    prix.setText(Prix); 
    stock.setText(Stock); 
    description.setText(produit.getDescription()); 
    
    fillComboBoxCategorie();
    fillImagesinAnkPane();
    
    }
    public void fillComboBoxCategorie() 
	{
		List<String> categorieList = new ArrayList<>();
		categorieList.add("Velo");
		categorieList.add("Piece de rechange");
		categorieList.add("Accessoire");
		categorie.getItems().setAll(categorieList);
	}
    
    public void create_single_vbox(String string){
        ImageView imageview = new ImageView("file:"+string);
        imageview.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
        imageview.setFitHeight(200);
        imageview.setFitWidth(240);
        
        AnchorPane vbox = new AnchorPane(imageview);

        vbox.setPrefHeight(200);
        vbox.setPrefWidth(240);
        
        vbox.setLayoutX(x.get(i));
        vbox.setLayoutY(y.get(i));
   
        ankpaneIM.getChildren().add(vbox); 
        }
    
    public void fillImagesinAnkPane(){
        ImageService is = new ImageService();
        Set<Images> ims = is.getAllImages_Produit(produit);
        for (Images image : ims){
            create_single_vbox(imagesPath+image.getImage());
            i++;
        } 
    }
    
    @FXML
    public void imageBtnAction() throws FileNotFoundException, IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des images pour le produit "+nom.getText());
        fileChooser.setInitialDirectory(new File("C:\\wamp64\\www\\integrationvelo\\web\\VTN"));
        Stage stage = (Stage)anchorpane.getScene().getWindow();
        files = fileChooser.showOpenMultipleDialog(stage);
        if (files!=null){
        if (files.size() <= 6){
            i=0;
            ankpaneIM.getChildren().clear();
        for (File file : files){
            String ext = FilenameUtils.getExtension(file.getName());
            if (extList.contains(ext)){
            create_single_vbox(file.getAbsolutePath());
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
    public void modifierBtnAction(){
       
       ModifierProduit(getAllFields());
       
       if (files!=null){
       //delete old phtos;
       imageService = new ImageService();
       imageService.supprimerImages(produit);
       
       //add new photos;
try {
            DeplaceFiles(produit);
        } catch (IOException ex) {
            System.out.println(ex);
        }
       }
  
       //redirect all products ;      
    }
    
    
    public Produit getAllFields(){
        
        String Reference = reference.getText();
        String Nom = nom.getText();
        String Categorie = categorie.getValue().toString();
        String Stock = stock.getText();
        String Prix = prix.getText();
        String Description = description.getText();
        
        Produit p = new Produit();
                p.setReference(Reference);
                p.setName(Nom);
                p.setCategory(Categorie);
                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);
                p.setPrice(Double.parseDouble(Prix));
                p.setStock(Integer.parseInt(Stock));
                p.setDescription(Description);
                
               return p;
    }
    
    public void ModifierProduit(Produit p){
        produitService = new ProduitService();
        produitService.ModifierProduit(produit,p);
    }
    
    public void DeplaceFiles(Produit p) throws FileNotFoundException, IOException{
        if (files!=null){
            for (File file : files){
                System.out.println(file.getAbsoluteFile());
                System.out.println(file);
                
                  FileInputStream in = new FileInputStream(file);
                  String ext = "."+FilenameUtils.getExtension(file.getName());
                  String string = getAlphaNumericString();
                  FileOutputStream ou = new FileOutputStream(imagesPath+"\\Products\\"+string+ext);         
                  System.out.println("IMAGE FEL WAMP : "+imagesPath+"\\Products\\"+string+ext);
                  Images image = new Images();
                  image.setImage("Products/"+string+ext);
                  imageService.AjouterImages(produit,image);
                  
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
}
