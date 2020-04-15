/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.sql.Date;

/**
 *
 * @author maily
 */
public class Demande {
    private int id;
    private String description;
    private Date dateDemande;
    private String Etat;
    private String image;
    private User user;
    private int notificationAdmin;
    private int notificationClient;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public String getEtat() {
        return Etat;
    }

    public String getImage() {
        return image;
    }

    public User getUser() {
        return user;
    }

    public int getNotificationAdmin() {
        return notificationAdmin;
    }

    public int getNotificationClient() {
        return notificationClient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setEtat(String Etat) {
        this.Etat = Etat;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotificationAdmin(int notificationAdmin) {
        this.notificationAdmin = notificationAdmin;
    }

    public void setNotificationClient(int notificationClient) {
        this.notificationClient = notificationClient;
    }
    
   
}
