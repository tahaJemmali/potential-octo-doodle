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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.ImageService;
import services.ProduitService;

/**
 *
 * @author USER
 */
public class ContentProduitsController implements Initializable{
    
    @FXML
    private TableView<Produit> TableProduitView;
    @FXML
    private TableColumn<Produit,String> N;
    @FXML
    private TableColumn<Produit,String> Reference;
    @FXML
    private TableColumn<Produit,String> Nom;
    @FXML
    private TableColumn<Produit,String> Categorie;
    @FXML
    private TableColumn<Produit,String> Prix;
    @FXML
    private TableColumn<Produit,String> Stock;
    @FXML
    private TableColumn<Produit,String> Date;
    @FXML
    private TableColumn Action;
    @FXML
    private AnchorPane IVanchpane ;
    @FXML
    private Pagination paginator ;
    
    private MainController mc ;
    
        List<Integer> x = new ArrayList<>(Arrays.asList(30,360,700,1030,30,360,700,1030));
        List<Integer> y = new ArrayList<>(Arrays.asList(10,10,10,10,310,310,310,310));
        
    //private Pagination paginator ;
    
        private ProduitService produitService ;
        private Map<Produit,Set<Images>> ListProduits;
        
    public static Set<Images> images ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       produitService = new ProduitService();
       ListProduits = produitService.getAllProduit();
       LoadProduitTable();
       init_IVanchpane();
      // ListProduits.entrySet().stream().forEach((e)->{System.out.println(e.getKey().toString());ListProduits.get(e.getKey()).stream().forEach(s->System.out.println(s));});
    }
    
    public void init(MainController maincontroller){
        mc = maincontroller;
    }
    
    public void LoadProduitTable(){
        ObservableList <Produit> data = FXCollections.observableArrayList();
        
       ListProduits.entrySet().stream().forEach((p)->{
           data.add(p.getKey());
       });
       
		 Reference.setCellValueFactory(new PropertyValueFactory<> ("reference"));
		 Nom.setCellValueFactory(new PropertyValueFactory<> ("name"));
		 Categorie.setCellValueFactory(new PropertyValueFactory<> ("category"));
                 Prix.setCellValueFactory(new PropertyValueFactory<> ("price"));
                 Stock.setCellValueFactory(new PropertyValueFactory<> ("stock"));
                 Date.setCellValueFactory(new PropertyValueFactory<> ("date"));
		 //Edit.setCellValueFactory(new PropertyValueFactory<> ("Edit"));
		
		 Callback<TableColumn<Produit,String>,TableCell<Produit,String>> cellFactory = (param)->{
			 final TableCell<Produit,String> cell=new TableCell<Produit,String>(){
				 @Override
				 public void updateItem(String item,boolean empty) {
					 super.updateItem(item,empty);
					 if (empty) {
						 setGraphic(null);
						 setText(null);
					 }
                                         //IMAGE INSIDE BTN
                                                 /*Image image = new Image("http://localhost/integrationvelo/web/uploads/a066ae581218e66e790bcec29580540b.jpg",true);
                                                 editButton.setGraphic(new ImageView(image) );*/
					 else {
                                                 final Button viewButton = new Button();
						 final Button editButton = new Button();                                                 
                                                 final Button deleteButton = new Button();
                                                 
                                                 FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                                 view_icon.setStyle("-fx-fill : GREEN");
                                                 view_icon.setSize("17px");
                                                 
                                                FontAwesomeIconView delete_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                                 delete_icon.setStyle("-fx-fill : #FF2800");
                                                 delete_icon.setSize("17px");
                                                 
                                                 FontAwesomeIconView editicon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                                                 editicon.setStyle("-fx-fill : #FFA500");
                                                 editicon.setSize("17px");
                                                 
                                                 
                                                 editButton.setGraphic(editicon);
                                                 deleteButton.setGraphic(delete_icon);
                                                 viewButton.setGraphic(view_icon);
                                                 
                                                 deleteButton.setPrefWidth(30);
                                                 editButton.setPrefWidth(30);
                                                 viewButton.setPrefWidth(30);
                                                 
                                                 deleteButton.setPrefHeight(30);
                                                 editButton.setPrefHeight(30);
                                                 viewButton.setPrefHeight(30);
                                                 
                                                 /*viewButton.setStyle("-fx-background-color: #98FB98");
                                                 deleteButton.setStyle("-fx-background-color: #FF2800");
                                                 editButton.setStyle("-fx-background-color: #FFA500");*/

                                                 HBox pane = new HBox(viewButton,editButton,deleteButton);
                                                 editButton.setOnAction(event->{
                                                 Produit p = getTableView().getItems().get(getIndex());
                                                 
                                                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
						 alert.setContentText("Clicked Edit"+p.getReference());
						 alert.show();
                                                 
                                                 ModificationBtnCliked(p);
                                                 
						 });
                                                 deleteButton.setOnAction(event->{
                                                     Produit p = getTableView().getItems().get(getIndex());
                                                     
                                                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
						 alert.setContentText("Clicked Delete"+p.getReference());
						 alert.show();
                                                 //SUPPRESSION 
                                                 SupprimerBtnCliked(p);
						 });
                                                 viewButton.setOnAction(event->{
                                                     Produit p = getTableView().getItems().get(getIndex());
                                                     try {
                                                         images=ListProduits.get(p);
                                                         if (images.size()!=0){
                                                         FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/ImageShow.fxml"));
                                                        Parent root = loader.load();
                                                        AnchorPane anchpane = (AnchorPane)loader.getNamespace().get("anchpane");
                                                         Stage stage = new Stage (/*StageStyle.UNDECORATED*/);
                                                         Scene scene = new Scene(root);
                                                         stage.setScene(scene);

                                                         stage.show();
                                                         }
                                                         else{
                                                             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                            alert.setContentText("Pas d'images pour ce produit !");
                                                            alert.show();
                                                         }
                                                         
                                                     } catch (IOException ex) {
                                                         Logger.getLogger(ContentProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                                                     }
                                                 
                                                 });
						 setGraphic(pane);
						 setText(null);
					 }
				 };	 
			 };
			 return cell;
		 };
		 Action.setCellFactory(cellFactory);
		 TableProduitView.setItems(data);	
    }
    
    public int page_number(){
        double x=0;
        for(Produit p: ListProduits.keySet()){
            x++;
        }
        //System.out.println((int) Math.ceil(x/8));
        return (int) Math.ceil(x/8);
    }
    
        public void init_IVanchpane(){
            //int page = 0 ;
       // paginator = new Pagination(page_number());
        /*AnchorPane.setTopAnchor(paginator, 0.0);
        paginator.setPrefSize(1600,700);
        IVanchpane.getChildren().add(paginator);*/
        
        System.out.println(page_number()+"fahd");
        paginator.setPageCount(page_number());
        
        
        List<AnchorPane> ListAnchor = createAnchors();
        
        
        paginator.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return ListAnchor.get(pageIndex);
            }
        });
           
    }
        
        public List<AnchorPane> createAnchors(){
            List<AnchorPane> ListAnchor = new ArrayList<>();
            
        AnchorPane ankpane = new AnchorPane();
        int i=0;
        for(Produit p: ListProduits.keySet()){ //z lel stream //i-- si y pa d'image
            int z=0;
		for ( Images m : ListProduits.get(p))
		{   //System.out.println(p.toString()+" --  "+m.getImage());
                    if (m.getImage()==null){
                    System.out.println("le produit"+p.toString()+" n pas dimage"+m.getImage());
                    z=1;
                    i--;
                }
                //System.out.println(p+ListProduits.get(p).stream().findFirst().get().getImage()+ankpane+x.get(i)+y.get(i));
		  if (z==0){create_single_vbox(p,m.getImage(),ankpane,x.get(i),y.get(i));}
                  z=1;
                 // System.out.println("IMAGES NUMBER "+i);
		}
                
                //System.out.println("boucle : "+i+" produit : "+p.getName()+" position : "+x.get(i)+" "+y.get(i));
                i++;
                   if (i==8)
                   {
                       i=0;
                       ListAnchor.add(ankpane);
                       ankpane= new AnchorPane();
                   }
	}
            ListAnchor.add(ankpane);
            return ListAnchor;
        }
        
        public void create_single_vbox(Produit p,String image,AnchorPane ankpane,int x,int y){
        ImageView imageview = new ImageView("http://localhost/integrationvelo/web/"+image);
        imageview.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
        imageview.setFitHeight(260);
        imageview.setFitWidth(300);
        
                                                 final Button viewButton = new Button();
                                                 final Button editButton = new Button();
                                                 
                                                 FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                                 view_icon.setStyle("-fx-fill : GREEN");
                                                 view_icon.setSize("17px");
                                                 
                                                 FontAwesomeIconView editicon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                                                 editicon.setStyle("-fx-fill : #FFA500");
                                                 editicon.setSize("17px");
                                                 
                                                 editButton.setGraphic(editicon);
                                                 viewButton.setGraphic(view_icon);
                                                 
                                                 editButton.setPrefWidth(30);
                                                 viewButton.setPrefWidth(30);
                                                 
                                                 editButton.setPrefHeight(30);
                                                 viewButton.setPrefHeight(30);
                
        Label Nom = new Label(p.getName());    
        Label Reference = new Label(p.getReference());
        Label Categorie = new Label(p.getCategory());
        
        AnchorPane vbox = new AnchorPane(imageview,viewButton,editButton,Nom,Reference,Categorie);
        viewButton.setLayoutX(vbox.getLayoutX()+120);
        editButton.setLayoutX(vbox.getLayoutX()+150);
        
        Nom.setLayoutY(vbox.getLayoutY()+260);
        Reference.setLayoutY(vbox.getLayoutY()+270);
        Categorie.setLayoutY(vbox.getLayoutY()+280);

        vbox.setPrefHeight(300);
        vbox.setPrefWidth(300);
        
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);

        
        //paginator.setTopAnchor(vbox, 300.0);
        //AnchorPane.setLeftAnchor(editButton, 150.0);
        //String style = "-fx-border-color: rgba(0, 0, 0, 0.5);";
        //vbox.setStyle(style);
        
        //AnchorPane.setTopAnchor(vbox, 10.0);
        //AnchorPane.setLeftAnchor(vbox, 10.0);
        //IVanchpane.getChildren().add(vbox);
        //paginator.setCurrentPageIndex(2);
        
        ankpane.getChildren().add(vbox);

        
        }
        
        @FXML
        public void AjoutProduitBtn(){
            mc.AJOUTER_PRODUITS(); 
        }
        
        public void ModificationBtnCliked(Produit p){
            System.out.println(p.toString());
            mc.MODIFIER_PRODUIT(p);
        }
        
        public void SupprimerBtnCliked(Produit p){
            
            ImageService is = new ImageService();
            is.supprimerImages(p);
            ProduitService ps = new ProduitService();
            ps.supprimerProduit(p);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Produit "+p.getReference()+" supprime");
            alert.show();
            
        }
    
}