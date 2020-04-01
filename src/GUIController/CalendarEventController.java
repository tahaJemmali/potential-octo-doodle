/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author tahtouh
 */
public class CalendarEventController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox centerArea;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton Reload;
    @FXML
    private JFXButton pdf;
    @FXML
    private JFXButton excel;
    @FXML
    private HBox weekdayHeader;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label calendarNameLbl;
    @FXML
    private Label monthLabel;
    @FXML
    private JFXComboBox<?> selectedYear;
    @FXML
    private JFXListView<?> monthSelect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ReloadAction(ActionEvent event) {
    }

    @FXML
    private void pdfAction(ActionEvent event) {
    }

    @FXML
    private void excelAction(ActionEvent event) {
    }
    
}
