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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ImageService;
import services.ProduitService;
import services.UserService;
import utils.Excel;

/**
 *
 * @author USER
 */
public class ContentProduitsController implements Initializable{
    
    @FXML
    private TableView<Produit> TableProduitView;
    @FXML
    private TableColumn<Produit,Number> N;
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
    @FXML
    private JFXButton excel;
    @FXML
    private TextField Searsh ;
    
    private ObservableList <Produit> data;
    
    @FXML
    private Label total ;
    
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
       total.setText("Total "+ListProduits.size());
       
       FontAwesomeIconView  icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
       Image im = new Image("http://localhost/integrationvelo/web/uploads/tncTMhK9OSkg43qZfA7q1ISAvAntCz.jpg");
       
       //Searsh.setStyle("-fx-background-image:URL("+im+")");
       
      // ListProduits.entrySet().stream().forEach((e)->{System.out.println(e.getKey().toString());ListProduits.get(e.getKey()).stream().forEach(s->System.out.println(s));});
        FontAwesomeIconView iconexcel = new FontAwesomeIconView(FontAwesomeIcon.FILE_EXCEL_ALT);
        iconexcel.setStyle("-fx-fill : GREEN");
        iconexcel.setSize("20px");
        excel.setGraphic(iconexcel);
    }
    
    public void init(MainController maincontroller){
        mc = maincontroller;
    }
    
    public void LoadProduitTable(){
        data = FXCollections.observableArrayList();
        
       ListProduits.entrySet().stream()/*.filter(p->p.getKey().getCategory().equals("Velo"))*/.forEach((p)->{
           data.add(p.getKey());
       });
      

		 Reference.setCellValueFactory(new PropertyValueFactory<> ("reference"));
		 Nom.setCellValueFactory(new PropertyValueFactory<> ("name"));
		 Categorie.setCellValueFactory(new PropertyValueFactory<> ("category"));
                 Prix.setCellValueFactory(new PropertyValueFactory<> ("price"));
                 Stock.setCellValueFactory(new PropertyValueFactory<> ("stock"));
                 Date.setCellValueFactory(new PropertyValueFactory<> ("date"));
		 //Edit.setCellValueFactory(new PropertyValueFactory<> ("Edit"));
                 
		 Reference.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.17));
                 Nom.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.25));
                 Categorie.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.12));
                 Prix.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.1));
                 Stock.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.1));
                 Date.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.1));
                 Action.prefWidthProperty().bind(TableProduitView.widthProperty().multiply(0.1));

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
                                                 editbuttonOnClick(p);                                                
						 });
                                                 deleteButton.setOnAction(event->{
                                                     
                                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                    DialogPane dialogPane = alert.getDialogPane();
                                                    GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
                                                    grid.setStyle("-fx-background-color: #e2e2e2; ");
                                                alert.setTitle("Confirmation ");
                                                alert.setHeaderText("Confirmation");
                                                alert.setContentText("Voulez-vous vraiment supprimer ce produit ?");
                                                Optional<ButtonType> result = alert.showAndWait();
                                                if (result.get() == ButtonType.OK){
                                                Produit p = getTableView().getItems().get(getIndex()); 
                                                SupprimerBtnCliked(p);
                                                data.clear();
                                                ProduitService ps = new ProduitService();
                                                
                                                ps.getAllProduit().entrySet().stream().forEach((i)->{data.add(i.getKey());});
                                                
                                                } 
                                                 
						 });
                                                 viewButton.setOnAction(event->{
                                                 Produit p = getTableView().getItems().get(getIndex());
                                                 viewbuttonOnClick(p);
                                                 });
                                                 pane.setTranslateX(18);
                                                 pane.setTranslateY(5);
						 setGraphic(pane);
						 setText(null);
					 }
				 };	 
			 };
			 return cell;
		 };
                 N.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(TableProduitView.getItems().indexOf(column.getValue())+1));
		 Action.setCellFactory(cellFactory);
		 TableProduitView.setItems(data);

                 searsh();
    }
    public void viewbuttonOnClick(Produit p){
        
                                                     try {
                                                         images=ListProduits.get(p);
                                                         if (images.size()!=0){
                                                         FXMLLoader loader = new FXMLLoader(getClass().getResource("/guis/ImageShow.fxml"));
                                                        Parent root = loader.load();
                                                        AnchorPane anchpane = (AnchorPane)loader.getNamespace().get("anchpane");
                                                         Stage stage = new Stage (/*StageStyle.UNDECORATED*/);
                                                         Scene scene = new Scene(root);
                                                         
                                                         stage.setAlwaysOnTop(true);
                                                         stage.initModality(Modality.APPLICATION_MODAL);
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
    }
    public void editbuttonOnClick(Produit p){
        ModificationBtnCliked(p);
    }
    @FXML
    public void excel() throws IOException, SQLException{
        Excel excel = new Excel();
        excel.WritetoExcelProduits();
        
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
        
                                                 final JFXButton viewButton = new JFXButton();
                                                 final JFXButton editButton = new JFXButton();
                                                 
                                                 viewButton.setStyle("-fx-opacity:0.3;");
                                                 editButton.setStyle("-fx-opacity:0.3;");
                                                 
                                                        viewButton.setOnMouseEntered(e ->{ viewButton.setStyle("-fx-opacity: 1;");});
                                                        viewButton.setOnMouseExited(e -> viewButton.setStyle("-fx-opacity: 0.3;"));
                                                        
                                                        editButton.setOnMouseEntered(e ->{ editButton.setStyle("-fx-opacity: 1;");});
                                                        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-opacity: 0.3;"));
        
                                                        viewButton.setOnAction(e->{
                                                 viewbuttonOnClick(p);
                                                        });
                                                        editButton.setOnAction(e->{
                                                            editbuttonOnClick(p);
                                                        });
                                                        
                                                 FontAwesomeIconView view_icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                                 view_icon.setStyle("-fx-fill : #1184e8");
                                                 view_icon.setSize("75px");
                                                 
                                                 FontAwesomeIconView editicon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                                                 editicon.setStyle("-fx-fill : #FFA500");
                                                 editicon.setSize("75px");
                                                 
                                                 editButton.setGraphic(editicon);
                                                 viewButton.setGraphic(view_icon);
                                                 
                                                 editButton.setPrefWidth(100);
                                                 viewButton.setPrefWidth(100);
                                                 
                                                 editButton.setPrefHeight(100);
                                                 viewButton.setPrefHeight(100);
                
        Label Nom = new Label(p.getName());    
        Label Reference = new Label(p.getReference());
        Label Categorie = new Label("In : "+p.getCategory());
        
        Nom.setStyle("-fx-text-fill:black;");
        Reference.setStyle("-fx-text-fill:black;");
        Categorie.setStyle("-fx-text-fill:black;");
        
        AnchorPane vbox = new AnchorPane(imageview,viewButton,editButton,Nom,Reference,Categorie);
        viewButton.setLayoutX(vbox.getLayoutX()+50);
        editButton.setLayoutX(vbox.getLayoutX()+150);
        
        viewButton.setLayoutY(vbox.getLayoutY()+75);
        editButton.setLayoutY(vbox.getLayoutY()+75);
        
        Nom.setLayoutY(vbox.getLayoutY()+260);
        Reference.setLayoutY(vbox.getLayoutY()+271);
        Categorie.setLayoutY(vbox.getLayoutY()+282);

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

            notification("Confirmation","Produit "+p.getReference()+" supprime");
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
        

public void searsh(){
 // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Produit> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		Searsh.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(p -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (p.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches  name
				} else if (p.getReference().toLowerCase().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches refrence
				}
                                else if (p.getCategory().toLowerCase().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches category.
				}
				else if (String.valueOf(p.getDate()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Produit> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TableProduitView.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TableProduitView.setItems(sortedData);
}
    
}