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
public class Reparation {

    private int id;
    private User user;
    private Date date_depot;
    private String etat;
    private Demande demande;
    
    

    public Reparation() {
    }
    
    

    public Reparation(int id, User user, Date date_depot, String etat) {
        this.id = id;
        this.user = user;
        this.date_depot = date_depot;
        this.etat = etat;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }
    
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate_depot() {
        return date_depot;
    }

    public String getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user= user;
    }

    public void setDate_depot(Date date_depot) {
        this.date_depot = date_depot;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Reparation other = (Reparation) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reparation{" + "id=" + id + ", user=" + user + ", date_depot=" + date_depot + ", etat=" + etat + '}';
    }
     
}
