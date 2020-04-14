/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author tahtouh
 */
public class Evenement {
    private int id;
    private String titre;
    private String description;
    private String image;
    private LocalDate date_debut;
    private LocalDate date_creation;
    private int max_participant;
    private String location;
    private int score_id;
    private User u;

    public Evenement() {
    }

    public Evenement(String titre, String description, String image, LocalDate date_debut, LocalDate date_creation, int max_participant, String location) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.date_debut = date_debut;
        this.date_creation = date_creation;
        this.max_participant = max_participant;
        this.location = location;
    }
    

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public int getMax_participant() {
        return max_participant;
    }

    public String getLocation() {
        return location;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }
    

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public void setMax_participant(int max_participant) {
        this.max_participant = max_participant;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", image=" + image + ", date_debut=" + date_debut + ", date_creation=" + date_creation + ", max_participant=" + max_participant + ", location=" + location + ", score_id=" + score_id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
