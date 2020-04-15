package guisServices;

import entities.Ordre;
import entities.Reparation;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.OrdreService;
import services.ReparationService;

public class ContentReparationController implements Initializable {
    
    private List<Reparation>ListReparation;
    
    
    @FXML
    private TableView<Reparation> table_reparation;
    @FXML
    private TableColumn<Reparation,String> id;
    @FXML
    private TableColumn<Reparation,Integer> id_client;
    @FXML
    private TableColumn<Reparation,String> date_depot;
    @FXML
    private TableColumn<Reparation,String> etat;
    @FXML
    private TableColumn action;
    @FXML
    private Button createRep;
    @FXML
    private Button createUser;

    @FXML
    private ImageView image ;
    
    @FXML
    private void createUserWindow(){
       try {
                 Parent root = FXMLLoader.load(getClass().getResource("/guis/createUser.fxml"));
                 Scene scene = new Scene(root);
                 
                 Stage primaryStage = new Stage();
                 primaryStage.setScene(scene);
                 primaryStage.show();
             } catch (IOException ex) {
                 Logger.getLogger(ContentReparationController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
 

    @FXML
    private void display(){
       try {
                 Parent root = FXMLLoader.load(getClass().getResource("/guis/createRep.fxml"));
                 Scene scene = new Scene(root);
                 
                 Stage primaryStage = new Stage();
                 primaryStage.setScene(scene);
                 primaryStage.show();
             } catch (IOException ex) {
                 Logger.getLogger(ContentReparationController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
 
    
    private ObservableList <Reparation> data = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ReparationService Rs = new ReparationService();
         ListReparation = Rs.getReparations();
         LoadTable();
         
         /*createRep.setOnAction(event->{
             System.out.println("or8od");
         });*/
         
        /*createRep.setOnAction(event->{
             try {
                 Parent root = FXMLLoader.load(getClass().getResource("/guis/createRep.fxml"));
                 Scene scene = new Scene(root);
                 
                 Stage primaryStage = new Stage();
                 primaryStage.setScene(scene);
                 primaryStage.show();
             } catch (IOException ex) {
                 Logger.getLogger(ContentReparationController.class.getName()).log(Level.SEVERE, null, ex);
             }
                         });*/
         
    }
    public void LoadTable(){
        
        
       ListReparation.stream().forEach((r)->{
           System.out.println(r.toString());
           data.add(r);
       });

         id.setCellValueFactory(new PropertyValueFactory<> ("id"));
         
         /*id_client.setCellValueFactory(new Callback<CellDataFeatures<Reparation,String>,ObservableValue<String>>(){
             @Override
                public ObservableValue<String> call(CellDataFeatures<Reparation, String> param) {
                    return new SimpleStringProperty(String.valueOf(param.getValue().getUser().getId()));
                }
         });*/
         id_client.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(table_reparation.getItems().indexOf(column.getValue())+1));
         date_depot.setCellValueFactory(new PropertyValueFactory<> ("date_depot"));
         etat.setCellValueFactory(new PropertyValueFactory<> ("etat"));
        
                id_client.prefWidthProperty().bind(table_reparation.widthProperty().multiply(0.1));
                id.prefWidthProperty().bind(table_reparation.widthProperty().multiply(0.2));
                 date_depot.prefWidthProperty().bind(table_reparation.widthProperty().multiply(0.2));
                 etat.prefWidthProperty().bind(table_reparation.widthProperty().multiply(0.2));
                 action.prefWidthProperty().bind(table_reparation.widthProperty().multiply(0.295));
                 
         Callback<TableColumn<Reparation,String>,TableCell<Reparation,String>> cellFactory = (param)->{
             final TableCell<Reparation,String> cell=new TableCell<Reparation,String>(){
                 @Override
                 public void updateItem(String item,boolean empty) {
                     super.updateItem(item,empty);
                     if (empty) {
                         setGraphic(null);
                         setText(null);
                     }
                     else {
                                                 
                         final Button btnEncours = new Button("En cours");
                         final Button btnLivrable = new Button("Livrable");
                                //deleteButton.setPrefWidth(30);
                                HBox paneEncours = new HBox(btnEncours, btnLivrable);
                               
                                btnEncours.setOnAction(event->{
                                    Reparation r = getTableView().getItems().get(getIndex());
                                    //System.out.println("hello there"+ r.getId());
                                    ReparationService rs = new ReparationService();
                                    Reparation newR = new Reparation();
                                    newR.setEtat("Encours");
                                    rs.updateReparation(r, newR);
                                    table_reparation.getItems().clear();
                                    
                                    table_reparation.getItems().addAll(rs.getReparations());
                                   
                                    
                         });
                                
                                
                                btnLivrable.setOnAction(event->{
                                     
                                    Reparation r = getTableView().getItems().get(getIndex());
                                    //System.out.println("hello there"+ r.getId());
                                    ReparationService rs = new ReparationService();
                                    Reparation newR = new Reparation();
                                    newR.setEtat("Livrable");
                                    rs.updateReparation(r, newR);
                                    
                                    Ordre o = new Ordre();
                                    o.setDemande(r.getDemande());
                                    o.setEtat("depot");    
                                    
                                    o.setDemande(r.getDemande());
                                    
                                    
                                    OrdreService os = new OrdreService();
                                    //os.addOrdre(o);
                                    table_reparation.getItems().clear();
                                    
                                    table_reparation.getItems().addAll(rs.getReparations());
                                   
                                    
                         });
                                paneEncours.setTranslateX(60);
                                paneEncours.setTranslateY(4);
                         setGraphic(paneEncours);
                         setText(null);
                         
                         
                     }
                 };
             };
             return cell;
         };
         action.setCellFactory(cellFactory);

         table_reparation.setItems(data);
        
    }
    
    @FXML
    public void refresh(){
        ReparationService rs = new ReparationService();
        
        table_reparation.getItems().clear();
        table_reparation.getItems().addAll(rs.getReparations());
        
    }

    
}
