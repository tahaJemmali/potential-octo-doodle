package guisServices;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Images;
import entities.Produit;
import entities.User;
import static guisServices.ContentUserController.user;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ProduitService;
import services.UserService;

public class ContentHomeController implements Initializable {
    
    @FXML
    private HBox hb;
    
    @FXML
    private AnchorPane anchpane;
    
    private Map<Produit,Set <Images>> listeDesProduits ;
    
    private static boolean SideBar=true ;
    
    MediaView   mv = new MediaView();
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MediaPlayer mp ;
        Media me;
        String path = new File("src/images/Bike Build.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(true);
        mp.muteProperty().set(true);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mv.setPreserveRatio(false);
        
       MainController.init(this);
       //SideBar=true;
       ProduitService ps = new ProduitService();
       listeDesProduits = ps.getAllProduit();
       //MediaPlayer(1400,750);
       System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+SideBar);
       if (SideBar){
       MediaPlayer(1600,845);
       initPieChart(1100,600);
       initTableViewUsers(400,303);
       }
       else{
       MediaPlayer(1350,845);
       initPieChart(850,600);
       initTableViewUsers(400,303); 
       }
        
    }
    
    public void MediaPlayer(int width,int height){
        
        mv.setFitWidth(width);
        mv.setFitHeight(height);
        anchpane.getChildren().add(mv);
    }
    
    public void initPieChart(int width,int height){
        
        List<PieChart.Data> pieData = new ArrayList<>();
        listeDesProduits.keySet().stream().forEach(k->{pieData.add(new PieChart.Data(k.getName(), k.getStock()));});
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        pieData
                );
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Quantité restante pour chaque produit");
        
        HBox hboxChart = new HBox(chart);

        hboxChart.setStyle(
        "-fx-border-width: 0.5;"+
        "-fx-border-radius: 5;" + "-fx-border-color: grey;"
               /* + "-fx-background-color : white;"*/);
        hboxChart.setLayoutX(450);
        hboxChart.setLayoutY(20);
        chart.setPrefSize(width, height);
        
        if (SideBar){
        hboxChart.setOnMouseEntered(e ->{ hboxChart.setStyle("-fx-opacity: 1;");chart.setStyle("-fx-opacity: 1;");});
        hboxChart.setOnMouseExited(e -> hboxChart.setStyle("-fx-opacity: 0.3;"));
        chart.setStyle("-fx-opacity:0.3;");
        }else{
        chart.setStyle("-fx-opacity:0.99;");
        hboxChart.setStyle("-fx-background-color : white;"+"-fx-opacity: 0.99;");
                }
        if (SideBar)
        chart.setLegendSide(Side.RIGHT);
        else
            chart.setLegendSide(Side.BOTTOM);
        chart.setLabelLineLength(5);
        
        final Label caption = new Label();
        caption.setStyle("-fx-font: 24 arial;"
                + " -fx-text-fill: black;");
        if (!SideBar){
        for (final PieChart.Data data : chart.getData()) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                if (SideBar)
                caption.setTranslateX(e.getSceneX());
                else
                caption.setTranslateX(e.getSceneX()-250); 
                
                caption.setTranslateY(e.getSceneY()-70);
                caption.setText(String.valueOf(data.getPieValue()) + "%");
                
             }
            
        });
}}
        
        //chart.setLegendSide(null);
        anchpane.getChildren().add(hboxChart);
        anchpane.getChildren().add(caption);
    }
    
    public void update_home(){
        initPieChart(500,600);
    }
    
    public void initTableViewUsers(int width,int height){
        UserService us = new UserService();
        List<User> ListUsers = us.getAllUser();
        
         ObservableList <User> data = FXCollections.observableArrayList(); 

        ListUsers.stream().filter(u->u.getRoles().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}") && u.getLast_login()!=null)
             .sorted((u1,u2)->u2.getLast_login().compareTo(u1.getLast_login())).limit(5).forEach((user)->{
        User u = new User();
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        u.setLast_login(user.getLast_login());
        data.add(u);
        });
        

        TableView<User> tvu = new TableView();
               /* tvu.setStyle("    -fx-background-color: transparent;"
                        + "    -fx-base: transparent;");*/
               if (SideBar){
               tvu.setStyle("-fx-opacity:0.3;");
        tvu.setOnMouseEntered(e -> tvu.setStyle("-fx-opacity: 1;"));
        tvu.setOnMouseExited(e -> tvu.setStyle("-fx-opacity: 0.3;"));
               }else
                 tvu.setStyle("-fx-opacity:0.95;");  
        tvu.setLayoutX(20);
        tvu.setLayoutY(20);
        
        tvu.setPrefSize(width, height);
        TableColumn<User,Integer> N = new TableColumn("#");
        TableColumn<User,String> username = new TableColumn("Username");
        TableColumn<User,String> dercnx = new TableColumn("Dérnière connection");
        TableColumn<User,String> Im = new TableColumn();
        N.setStyle("-fx-alignment : CENTER");
        username.setStyle("-fx-alignment : CENTER");
        dercnx.setStyle("-fx-alignment : CENTER");
        Im.setStyle("-fx-alignment : CENTER");
                username.setCellValueFactory(new PropertyValueFactory<> ("username"));
		 dercnx.setCellValueFactory(new PropertyValueFactory<> ("last_login"));

                 N.setCellFactory(col -> {
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
        
                                                 //final Button btn = new Button();
                                                 final Circle crl = new Circle(10);
                                                 crl.setFill(Color.LIGHTGREEN);
                                                 //btn.setShape(crl);
                                                // btn.setStyle(item);
						 setGraphic(crl);
						 setText(null);
                                                 
					 }
				 };	 
			 };
			 return cell;
		 };
                 
        tvu.getColumns().addAll(N,username,dercnx,Im);
        Im.setCellFactory(cellFactory);
        tvu.setItems(data);
        anchpane.getChildren().add(tvu);
        
                 N.prefWidthProperty().bind(tvu.widthProperty().multiply(0.1));
                 username.prefWidthProperty().bind(tvu.widthProperty().multiply(0.2));
                 dercnx.prefWidthProperty().bind(tvu.widthProperty().multiply(0.5));
                 Im.prefWidthProperty().bind(tvu.widthProperty().multiply(0.19));
        
    }
    
    public void Resize(boolean flag){
        anchpane.getChildren().clear();
        
       SideBar=flag;
       ProduitService ps = new ProduitService();
       listeDesProduits = ps.getAllProduit();
       if (SideBar){
           MediaPlayer(1600,845);
       initPieChart(1100,600);
       initTableViewUsers(400,303);
       
       }
       else{
           MediaPlayer(1350,845);
       initPieChart(850,600);
       initTableViewUsers(400,303);
       }
    }

    
}
