/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academiccalendar.ui.addevent;

import evenement.*;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.image.ImageView;

/**
 *
 * @author tahtouh
 */
public class ModelEventTable {
    int id;
     String titre;
     String description;
     LocalDate date_debut;
     String localisation;
     int max;
     ImageView image;
     float avg;
     int first;
     int secound;
     int third;

    public ModelEventTable(int id,String titre, String description, LocalDate date_debut, String location, int max, ImageView image, float avg, int first, int secound, int third) {
        this.id=id;
        this.titre = titre;
        this.description = description;
        this.date_debut = date_debut;
        this.localisation = location;
        this.max = max;
        this.image = image;
        this.avg = avg;
        this.first = first;
        this.secound = secound;
        this.third = third;
    }

    public int getId() {
        return id;
    }


    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecound() {
        return secound;
    }

    public void setSecound(int secound) {
        this.secound = secound;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }



    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

  

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getLocation() {
        return localisation;
    }

    public void setLocation(String location) {
        this.localisation = location;
    }
    
}
