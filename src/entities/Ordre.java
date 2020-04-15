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
public class Ordre {
    
    private int id;
    private String etat;
    private Date dateOrdre;
    private Demande demande;

    public int getId() {
        return id;
    }

    public String getEtat() {
        return etat;
    }

    public Date getDateOrdre() {
        return dateOrdre;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setDateOrdre(Date dateOrdre) {
        this.dateOrdre = dateOrdre;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }
    
    
    
}
