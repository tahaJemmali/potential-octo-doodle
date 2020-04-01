package guisServices;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Evenement;
import evenement.ModelEventTable;
import evenement.ModelEventTable;
import evenement.ModelEventTable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import services.EventService;

public class ContentEventController implements Initializable {

    @FXML
    private VBox content_area;
    @FXML
    private TableColumn<ModelEventTable, Integer> ID;
    @FXML
    private TableColumn<ModelEventTable, String> titre;
    @FXML
    private TableColumn<ModelEventTable, String> description;
    @FXML
    private TableColumn<ModelEventTable, LocalDate> date_debut;
    @FXML
    private TableColumn<ModelEventTable, String> localisation;
    @FXML
    private TableColumn<ModelEventTable, Integer> max_participant;
    @FXML
    private TableColumn<ModelEventTable, String> image;
    @FXML
    private TableColumn<ModelEventTable, Float> avg;
    @FXML
    private TableColumn<ModelEventTable, Integer> first;
    @FXML
    private TableColumn<ModelEventTable, Integer> secound;
    @FXML
    private TableColumn<ModelEventTable, Integer> third;
    @FXML
    private TableView<ModelEventTable> tableEvent;

    private EventService eventService;
    private List<Evenement> ListEvents;
    private static Evenement event;
    final String web_path = "http://localhost/velo/web/";

    ObservableList<ModelEventTable> oblist = FXCollections.observableArrayList();

    public static Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventService = new EventService();
        ListEvents = eventService.getAll();
        float moyenne;
        int[] score = {};
        for (Evenement e : ListEvents) {
            ImageView img = new ImageView(new Image(web_path + e.getImage(), true));
            img.setFitWidth(400);
            img.setFitHeight(200);
            moyenne = (eventService.nombreParticipant(e.getId()) * 100) / e.getMax_participant();
            score = eventService.getScore(e.getScore_id());
            oblist.add(new ModelEventTable(e.getId(), e.getTitre(), e.getDescription(), e.getDate_debut(), e.getLocation(), e.getMax_participant(), img, moyenne, score[0], score[1], score[2]));
        }
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        localisation.setCellValueFactory(new PropertyValueFactory<>("location"));
        max_participant.setCellValueFactory(new PropertyValueFactory<>("max"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        avg.setCellValueFactory(new PropertyValueFactory<>("avg"));
        first.setCellValueFactory(new PropertyValueFactory<>("first"));
        secound.setCellValueFactory(new PropertyValueFactory<>("secound"));
        third.setCellValueFactory(new PropertyValueFactory<>("third"));
        tableEvent.setItems(oblist);

        tableEvent.setEditable(true);
        titre.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        localisation.setCellFactory(TextFieldTableCell.forTableColumn());

        date_debut.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        //image.setCellFactory(TextFieldTableCell.forTableColumn());
        max_participant.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        first.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        secound.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        third.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    @FXML
    public void ajouter(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement/addEvent.fxml"));
        Parent MainView2 = (Parent) loader.load();
        Scene MainScene2 = new Scene(MainView2);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(MainScene2);
        stage.centerOnScreen();
        this.stage = stage;
        stage.show();
    }

    /*
    *double click pour modifier
     */
    public void ModiferCellEvenement(CellEditEvent editedCell) throws IOException, SQLException {
        ModelEventTable eventSelected = tableEvent.getSelectionModel().getSelectedItem();
        int id = ID.getCellData(tableEvent.getSelectionModel().getSelectedIndex());
        eventService.modifier(editedCell.getTableColumn().getId(), editedCell.getNewValue(), id);
Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 alert1.setHeaderText(null);
 alert1.setContentText("Modification Effectué");
 ButtonType daccord = new ButtonType("OK");
  alert1.getButtonTypes().setAll(daccord);
  alert1.showAndWait();
    }

    public void ModiferImage(CellEditEvent editedCell) throws MalformedURLException, IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        File selectedFile = fileChooser.showOpenDialog(content_area.getScene().getWindow());
        if (selectedFile != null) {
            int id = ID.getCellData(tableEvent.getSelectionModel().getSelectedIndex());
            eventService.modifier(editedCell.getTableColumn().getId(), selectedFile.getAbsolutePath(), id);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 alert1.setHeaderText(null);
 alert1.setContentText("Modification Effectué");
 ButtonType daccord = new ButtonType("OK");
  alert1.getButtonTypes().setAll(daccord);
  alert1.showAndWait();
        } else {
            System.out.println("file is not valid");
        }
    }

    /*
    *double click pour modifier
     */
    @FXML
    public void refresh(ActionEvent event) throws IOException, InterruptedException {
        oblist.clear();
        ListEvents = eventService.getAll();
        float moyenne;
        int[] score = {};
        for (Evenement e : ListEvents) {
            ImageView img = new ImageView(new Image(web_path + e.getImage(), true));
            img.setFitWidth(400);
            img.setFitHeight(200);
            moyenne = (eventService.nombreParticipant(e.getId()) * 100) / e.getMax_participant();
            score = eventService.getScore(e.getScore_id());
            oblist.add(new ModelEventTable(e.getId(), e.getTitre(), e.getDescription(), e.getDate_debut(), e.getLocation(), e.getMax_participant(), img, moyenne, score[0], score[1], score[2]));
        }
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        localisation.setCellValueFactory(new PropertyValueFactory<>("location"));
        max_participant.setCellValueFactory(new PropertyValueFactory<>("max"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        avg.setCellValueFactory(new PropertyValueFactory<>("avg"));
        first.setCellValueFactory(new PropertyValueFactory<>("first"));
        secound.setCellValueFactory(new PropertyValueFactory<>("secound"));
        third.setCellValueFactory(new PropertyValueFactory<>("third"));
        tableEvent.setItems(oblist);
    }

    @FXML
    void supprimer(ActionEvent eventt) throws SQLException {
        final ObservableList<Evenement> listEvenement2 = FXCollections.observableArrayList();

        if (tableEvent.getSelectionModel().getSelectedCells().isEmpty()) {
 Alert alert = new Alert(Alert.AlertType.ERROR);
 alert.setHeaderText("Oops !");
 alert.setContentText("Veuiller sélectionner l'évenement à supprimer !");
 ButtonType daccord = new ButtonType("D'accord");
  alert.getButtonTypes().setAll(daccord);
  alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Vous voulez supprimer l'evenement ?");

            ButtonType deleteEventButton = new ButtonType("Supprimer Evenement");
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(deleteEventButton, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == deleteEventButton) {
                int id = ID.getCellData(tableEvent.getSelectionModel().getSelectedIndex());
                eventService.SupprimerEvenement(id);
           Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 alert1.setHeaderText(null);
 alert1.setContentText("Supression Effectué");
 ButtonType daccord = new ButtonType("OK");
  alert1.getButtonTypes().setAll(daccord);
  alert1.showAndWait();
            }
        }

    }
}
