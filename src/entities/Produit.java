/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author USER
 */
public class Produit {
    private int id;
    private String reference;
    private String name;
    private String category;
    private double price;
    private int stock;
    private Date date;
    private String description;


    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setReference(String refrence) {
        this.reference = refrence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    @Override
    public String toString (){
        return "ID PRODUIT  : "+id+" Refrence : "+reference;/*+" Nom : "+name+" categorie : "+category+" prix : "+price+" stock : "
                +stock+" date d'ajout : "+date+" description : "+description;*/

    }
    @Override
   public boolean equals(Object o) {
	if (o==null) {
		System.out.println("Object null");
		return false;
	}
    if ((o instanceof Produit)) 
	{
                final Produit e = (Produit) o ;
                if (this.getReference().equals(e.getReference()) || this.getName().equals(e.getName()) ) 
                {
                return true;
                }
                else {
                    return false;
                }   
	}
		return false;
   }
}
