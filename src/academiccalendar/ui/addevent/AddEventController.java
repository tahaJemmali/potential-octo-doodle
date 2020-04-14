/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academiccalendar.ui.addevent;

import guisServices.*;
import academiccalendar.data.model.Model;
import academiccalendar.database.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entities.Evenement;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import services.EventService;
import academiccalendar.ui.main.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author tahtouh
 */
public class AddEventController implements Initializable {

    @FXML
    private JFXTextField titre;
    @FXML
    private JFXButton annuler;
    @FXML
    private JFXDatePicker date_debut;
    @FXML
    private JFXTextArea description;
    @FXML
    private Button choisirImage;
    @FXML
    private JFXTextField max;
    @FXML
    private JFXTextField third;
    @FXML
    private JFXTextField first;
    @FXML
    private JFXTextField secound;
    @FXML
    private JFXTextField location;

    final String web_path = "http://localhost/velo/web/velo/uploads/";
    public static Stage stage;

    private EventService eventService;
    private String imageFile;
    @FXML
    private ImageView image;

    DBHandler databaseHandler;

    // Controllers
    private FXMLDocumentController mainController;

/////////Random algortihm////////////////
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    final java.util.Random rand = new java.util.Random();

// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
    final Set<String> identifiers = new HashSet<String>();

    //Set main controller
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
/////////Random algortihm////////////////
    //Function that fills the date picker based on the clicked date 
    void autofillDatePicker() {
       // Get selected day, month, and year and autofill date selection
       int day = Model.getInstance().event_day;
       int month = Model.getInstance().event_month + 1;
       int year = Model.getInstance().event_year;
       
       // Set default value for datepicker
       date_debut.setValue(LocalDate.of(year, month, day));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = new DBHandler();
        eventService = new EventService();
 //Fill the date picker
        autofillDatePicker();
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException, InterruptedException {

   
        /////////////////////////XCalander
           //////UPLOAD IMAGE
        String newName="uploads/"+randomIdentifier()+"."+ FilenameUtils.getExtension(imageFile);
         String PathTo= "C:/wamp64/www/velo/web/"+newName ; 
        Files.copy(Paths.get(imageFile), Paths.get(PathTo), StandardCopyOption.REPLACE_EXISTING);
        /////
        Evenement e=new Evenement(titre.getText(),description.getText(),newName,date_debut.getValue(),LocalDate.now(),Integer.parseInt(max.getText()),location.getText());
         eventService.addEvent(e, Integer.parseInt(first.getText()), Integer.parseInt(secound.getText()), Integer.parseInt(third.getText()));
        
         
              //////////////////////////calandrier
        // Get the calendar name
        String calendarName = Model.getInstance().calendar_name;

        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //If all data is inputted correctly and validated, then add the event:
        // Get the date value from the date picker
        String calendarDate = date_debut.getValue().format(myFormat);
        // Subject for the event
        String eventSubject = titre.getText();

        // Get term that was selected by the user
         String term = "FA SEM"; 
               float moyenne = (eventService.nombreParticipant(e.getId()) * 100) / e.getMax_participant();
                if(e.getDate_debut().compareTo(LocalDate.now()) < 0){
                    term = "SP SEM";
                }
                else if (moyenne == 100) {
                    term = "FA 1st Half";
                   
                } else if (moyenne >= 60) {
                    term = "FA I MBA";
                } else if (moyenne >= 40) {
                    term = "FA QTR";
                } else if (moyenne >= 20) {
                    term = "SU SEM";
                }else{// En cour
                   term = "FA SEM"; 
                }

        // variable that holds the ID value of the term selected by the user. It set to 0 becasue no selection has been made yet
        int chosenTermID = 0;

        // Get the ID of the selected term from the database based on the selected term's name
        chosenTermID = databaseHandler.getTermID(term);

        //---------------------------------------------------------
        //Insert new event into the EVENTS table in the database
        //Query to get ID for the selected Term
        String insertQuery = "INSERT INTO EVENTS VALUES ("
                + "'" + eventSubject + "', "
                + "'" + calendarDate + "', "
                + chosenTermID + ", "
                + "'" + calendarName + "'"
                + ")";

        //Check if insertion into database was successful, and show message either if it was or not
        if (databaseHandler.executeAction(insertQuery)) {
               Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 alert1.setHeaderText(null);
 alert1.setContentText("ajout avec succés");
 ButtonType daccord = new ButtonType("OK");
  alert1.getButtonTypes().setAll(daccord);
  alert1.showAndWait();
        } else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Adding Event Failed!\nThere is already an event with the same information");
            alertMessage.showAndWait();
        }
        //Show the new event on the calendar according to the selected filters
        mainController.repaintView();

         

        ((Stage) titre.getScene().getWindow()).close();
    }

    @FXML
    private void Cancel(ActionEvent event) {
        ((Stage) titre.getScene().getWindow()).close();
    }

    @FXML
    public void pickImage(ActionEvent actionEvent) throws MalformedURLException, IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        File selectedFile = fileChooser.showOpenDialog(image.getScene().getWindow());

        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toURL().toString());
            image.setImage(img);
            imageFile = selectedFile.getAbsolutePath();
        } else {
            System.out.println("file is not valid");
        }
    }
}
