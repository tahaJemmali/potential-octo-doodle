/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisServices;

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
    
     final String web_path = "http://localhost/integrationvelo/web/";
      public static Stage stage ;
     
        private EventService eventService ;
        private String imageFile;
    @FXML
    private ImageView image;

/////////Random algortihm////////////////
final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

final java.util.Random rand = new java.util.Random();

// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
final Set<String> identifiers = new HashSet<String>();

public String randomIdentifier() {
    StringBuilder builder = new StringBuilder();
    while(builder.toString().length() == 0) {
        int length = rand.nextInt(5)+5;
        for(int i = 0; i < length; i++) {
            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
        }
        if(identifiers.contains(builder.toString())) {
            builder = new StringBuilder();
        }
    }
    return builder.toString();
}
/////////Random algortihm////////////////



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         eventService = new EventService();
        
    }    
    
    @FXML
    private void ajouter(ActionEvent event) throws IOException, InterruptedException{ 
        //////UPLOAD IMAGE
        String newName="uploads/"+randomIdentifier()+"."+ FilenameUtils.getExtension(imageFile);
         String PathTo= "C:/wamp64/www/integrationvelo/web/"+newName ; 
        Files.copy(Paths.get(imageFile), Paths.get(PathTo), StandardCopyOption.REPLACE_EXISTING);
        /////
        Evenement e=new Evenement(titre.getText(),description.getText(),newName,date_debut.getValue(),LocalDate.now(),Integer.parseInt(max.getText()),location.getText());
         eventService.addEvent(e, Integer.parseInt(first.getText()), Integer.parseInt(secound.getText()), Integer.parseInt(third.getText()));
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 alert1.setHeaderText(null);
 alert1.setContentText("ajout avec succés");
 ButtonType daccord = new ButtonType("OK");
  alert1.getButtonTypes().setAll(daccord);
  alert1.showAndWait();
       ((Stage) titre.getScene().getWindow()).close();
    }
        @FXML
    private void Cancel(ActionEvent event) {
        ((Stage) titre.getScene().getWindow()).close();
    }
    
    @FXML
    public void pickImage (ActionEvent actionEvent) throws MalformedURLException, IOException {

       FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        File selectedFile = fileChooser.showOpenDialog(image.getScene().getWindow());

        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toURL().toString());
            image.setImage(img);
            imageFile=selectedFile.getAbsolutePath();
        } else {
         System.out.println("file is not valid");
        }
    }
}
