package guisServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entities.Demande;
import entities.Ordre;
import entities.Reparation;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.DemandeService;
import services.OrdreService;
import services.ReparationService;
import services.UserService;

public class createRepController implements Initializable {
    
    private List<User>ListUser;
    
    @FXML
    private TableView<User> table_user;
    @FXML
    private TableColumn<User,String> username;
    @FXML
    private TableColumn<User,String> email;
    @FXML
    private TableColumn<User,String> action;
    
   
    private ObservableList <User> data = FXCollections.observableArrayList();
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService Us = new UserService();
         ListUser = Us.getAllUser();
         LoadTable();
    }
    
    /*@FXML
    public void createRep(){
        
        String text = id_client.getText();
        int id = Integer.parseInt(text);
        
        ReparationService rs = new ReparationService();
        Reparation r = new Reparation();
        
        r.setId(id);
        rs.add(r);
        
    }*/
    
    
    public void LoadTable(){
        
        
       ListUser.stream().forEach((r)->{
           //System.out.println(r.toString());
           User u = new User();
           u.setId(r.getId());
           u.setUsername(r.getUsername());
           u.setEmail(r.getEmail());
           u.setPassword(null);

           data.add(u);
           
       });

         username.setCellValueFactory(new PropertyValueFactory<> ("username"));
         email.setCellValueFactory(new PropertyValueFactory<> ("email"));
         action.setCellValueFactory(new PropertyValueFactory<> ("action"));
        
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
                                                 
                        
                         final Button btnSelect = new Button("Seléctionner");
                                HBox paneSelect = new HBox(btnSelect);
                               
                                
                                
                                
                                btnSelect.setOnAction(event->{
                                     
                                   User u = getTableView().getItems().get(getIndex());
                                    //System.out.println("hello there"+ r.getId());
                                    ReparationService rs = new ReparationService();
                                    Reparation newR = new Reparation();
                                    newR.setEtat("EnAttente");

                                    newR.setUser(u);
                                    Demande d = new Demande();
                                    d.setUser(u);
                                    DemandeService ds = new DemandeService();
                                    ds.add(d);
                                    newR.setDemande(d);
                                    rs.add(newR);
                                    
                         });
                         setGraphic(paneSelect);
                         setText(null);
                         
                         
                     }
                 };
             };
             return cell;
         };
         action.setCellFactory(cellFactory);

         table_user.setItems(data);
        
    }

}
