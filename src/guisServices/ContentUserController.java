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
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ProduitService;
import services.UserService;
import utils.Excel;

/**
 *
 * @author USER
 */
public class ContentUserController implements Initializable {

    @FXML
    public TableView<User> TableUsersView ;
    @FXML
    public TableColumn<User,Integer> Ne;
    @FXML
    public TableColumn<User,String> username;
    @FXML
    public TableColumn<User,String> email;
    @FXML
    public TableColumn<User,String> tel;
    @FXML
    public TableColumn<User,String> adress;
    @FXML
    public TableColumn<User,String> dercnx;
    @FXML
    public TableColumn Action;
    private int i = 0;
    @FXML
    private TextField Searsh ;
    
    public ObservableList <User> data;

    @FXML
    private JFXButton excel ;
    
    public static User user;
    
    private List<User> ListUsers ; 
    
    private ContentUserController cuc ;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  ListUsers = LoginService.getListUsers();
       UserService us=  new UserService();
       ListUsers = us.getAllUser();
        cuc = this ;
        initTableUsers();
        // excel
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_EXCEL_ALT);
        icon.setStyle("-fx-fill : GREEN");
        icon.setSize("20px");
        icon.setTranslateX(6);
        excel.setGraphic(icon);
    }
    
    public void initTableUsers(){
        
       data =  FXCollections.observableArrayList();
       
              
       ListUsers.stream().filter(u->u.getRoles().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")).forEach((u)->{
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
           
           data.add(user);
       });
       
		 username.setCellValueFactory(new PropertyValueFactory<> ("username"));
		 email.setCellValueFactory(new PropertyValueFactory<> ("email"));
		 tel.setCellValueFactory(new PropertyValueFactory<> ("phone"));
		 adress.setCellValueFactory(new PropertyValueFactory<> ("address"));
		 dercnx.setCellValueFactory(new PropertyValueFactory<> ("last_login"));

                 username.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.1));
                 email.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.25));
                 tel.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.12));
                 adress.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.25));
                 dercnx.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.15));
                 Action.prefWidthProperty().bind(TableUsersView.widthProperty().multiply(0.08));
                 
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
                                                     user=u;
                                                     
                                                try {
                                                            //Parent root = FXMLLoader.load(getClass().getResource("/guis/EditUser.fxml"));
                                                            FXMLLoader loader = new FXMLLoader (getClass().getResource("/guis/EditUser.fxml"));
                                                            Parent root = loader.load();
                                                            EditUserController euc = (EditUserController) loader.getController();
                                                            euc.init(cuc);
                                                            System.out.println("YAAAA"+euc);
                                                           
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
                 Callback<TableColumn<User,String>,TableCell<User,String>> cellFactory2 = (param)->{
			 final TableCell<User,String> cell=new TableCell<User,String>(){
				 @Override
				 public void updateItem(String item,boolean empty) {
					 super.updateItem(item,empty);
					 if (empty) {
						 setGraphic(null);
						 setText(null);
					 }
                                         else {
                                                Label l = new Label(String.valueOf(i));
                                                l.setStyle("-fx-text-fill: black;");
                                                i++;
						 setGraphic(l);
						 setText(null);
					 }
				 };	 
			 };
			 return cell;
		 };
                Ne.setCellFactory(col -> {
  TableCell<User, Integer> indexCell = new TableCell<>();
  ReadOnlyObjectProperty<TableRow> rowProperty = indexCell.tableRowProperty();
  ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
    TableRow<User> row = rowProperty.get();
    if (row != null) { // can be null during CSS processing
      int rowIndex = row.getIndex();
      if (rowIndex < row.getTableView().getItems().size()) {
        return Integer.toString(rowIndex+1);
      }
    }
    return null;
  }, rowProperty);
  indexCell.textProperty().bind(rowBinding);
  return indexCell;
});
		//N.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(TableUsersView.getItems().indexOf(column.getValue())));
               // Ne.setSortable(false);
               // Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(TableUsersView.getItems().indexOf(column.getValue())));

                //Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(TableUsersView.getItems().indexOf(column.getValue())+1));
                //Ne.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(TableUsersView.getItems().indexOf(column.getValue())));
                //Ne.setCellFactory(cellFactory2);
                Action.setCellFactory(cellFactory);
                TableUsersView.setItems(data);
                searsh();
    }
    
    @FXML
    public void excel() throws IOException, SQLException{
        Excel excel = new Excel();
        excel.WritetoExcelUsers();
        
    }
    
    @FXML
    private void AddNewUser() throws IOException{
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/guis/AddUser.fxml"));
        Parent root = loader.load();
        AddUserController euc = (AddUserController) loader.getController();
        euc.init(cuc);
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void searsh(){
 // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<User> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		Searsh.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(p -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (p.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches  name
				} else if (String.valueOf(p.getPhone()).indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches refrence
				}
                                else if (p.getEmail().toLowerCase().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches category.
				}
				else if (p.getAddress().toLowerCase().toLowerCase().indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<User> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TableUsersView.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TableUsersView.setItems(sortedData);
}
    
}
