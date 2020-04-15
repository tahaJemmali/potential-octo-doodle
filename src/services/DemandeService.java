/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Demande;
import entities.Reparation;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

/**
 *
 * @author USER
 */
public class DemandeService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public DemandeService() {
        cnx = MyConnection.getInstance().getCnx();
    }




     
     public void add(Demande d){
          try {
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
           
            
            String req = "INSERT INTO Demande (description,dateDemande,etat,image, client_id) values (?,?,?,?,?)";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,"**demande manuelle**");
            pre.setDate(2,date);
            pre.setString(3,"confirmee");
            pre.setString(4,"none");
            pre.setInt(5,d.getUser().getId());

            pre.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex);
        }
     }
   
  
}
