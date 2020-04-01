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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import static javafx.scene.control.Pagination.STYLE_CLASS_BULLET;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class ImageShowController implements Initializable {

    @FXML
    private AnchorPane anchpane ;
    
    private Pagination pagination;
    
    private Set<Images> images = ContentProduitsController.images;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button next = new JFXButton() ;
        Button previous = new JFXButton() ;
        if (images.size()!=0){
        pagination = new Pagination(images.size());
        AnchorPane.setTopAnchor(pagination, 0.0);
        AnchorPane.setTopAnchor(next, 300.0);
        AnchorPane.setLeftAnchor(next, 670.0);
        AnchorPane.setTopAnchor(previous, 300.0);
        pagination.setPrefSize(700,600);
        
        //pagination.getStyleClass().add("pagination");
        pagination.setStyle(" -fx-arrows-visible : false ");
        pagination.getStyleClass().add(STYLE_CLASS_BULLET);
       // pagination.setStyle(" --fx-border-color:  #0E5D79; ");
      //  pagination.setStyle(" -fx-tooltip-visible :false ");
        //pagination.setStyle(" -fx-max-page-indicator-count :false ");
       // pagination.setStyle(" -fx-page-information-visible :false ");
        	
       next.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        nextSlide();
    }
});
   
       previous.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        previousSlide();
    }
});
       
       List<String> imagesP = new ArrayList<>();
       
       images.stream().forEach(s->{imagesP.add("http://localhost/integrationvelo/web/"+s.getImage());});
                
        pagination.setPageFactory(n -> {
               ImageView imageview = new ImageView(imagesP.get(n));
               imageview.setFitHeight(600);
               imageview.setFitWidth(700);
               //imageview.setStyle("-fx-background-color: skyblue, derive(skyblue, 25%);");
               imageview.getStyleClass().add("imageview");
               return imageview;
                        });
       
        /*images.stream().forEach(s->{pagination.setPageFactory(n ->    
        {ImageView imageview = new ImageView("http://localhost/integrationvelo/web/"+s.getImage());
        System.out.println(s.getImage());
        imageview.setFitHeight(500);
        imageview.setFitWidth(600);
        return imageview ;
        }     
        );});*/
        
        FontAwesomeIconView next_icon = new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_RIGHT);
                                                 next_icon.setStyle("-fx-fill : WHITE");
                                                 next_icon.setSize("20px");
                                                 
        FontAwesomeIconView previous_icon = new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_LEFT);
                                                 previous_icon.setStyle("-fx-fill : WHITE");
                                                 previous_icon.setSize("20px");
                                                 
                               next.setGraphic(next_icon);   
                               previous.setGraphic(previous_icon); 
                               
                next.getStyleClass().add("arrows");
                previous.getStyleClass().add("arrows");
                
                next.setPrefHeight(50);
                previous.setPrefHeight(50);
                
                next.setPrefWidth(30);
                previous.setPrefWidth(30);
                
        anchpane.getChildren().add(pagination);
        anchpane.getChildren().add(next);
        anchpane.getChildren().add(previous);
        
        
        images.stream().forEach(s->System.out.println(s.getImage()));
        System.out.println("hc : "+this.hashCode());
        System.out.println("IMAGE SIZE : "+images.size());
        }
        
        else {
        System.out.println("0 images");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	alert.setContentText("Pas d'images pour ce produit !");
	alert.show();
                                                         
        }
//        System.out.println(pagination.getCurrentPageIndex());
       // pagination = new Pagination(images.size());
      //  pagination.setPageFactory(n -> new ImageView(images.get(n));      
    }
    
  
    
private void nextSlide() {
    
    if (pagination.getCurrentPageIndex()==pagination.getPageCount()-1)
    {
        pagination.setCurrentPageIndex(0);
    }
        else
    {pagination.setCurrentPageIndex(Math.min(pagination.getPageCount() - 1, pagination.getCurrentPageIndex() + 1));}
        System.out.println(pagination.getCurrentPageIndex());
    }
private void previousSlide() {
    
    if (pagination.getCurrentPageIndex()==0)
    {
        pagination.setCurrentPageIndex(pagination.getPageCount());
    }
    else{pagination.setCurrentPageIndex(Math.max(0, pagination.getCurrentPageIndex() - 1));}
        System.out.println(pagination.getCurrentPageIndex());
    }
    
    
}
