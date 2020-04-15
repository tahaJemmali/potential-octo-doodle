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
public class ReparationService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public ReparationService() {
        cnx = MyConnection.getInstance().getCnx();
    }



    public List<Reparation> getReparations() {

        List<Reparation> listR = new ArrayList<>();

        try {

            String req = "SELECT id,client_id,date_depot,etat,id_demande FROM reparation";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Reparation r = new Reparation();
                
                UserService userService = new UserService();
                User u = new User();
                u.setId(res.getInt(2));
                User user = userService.getUser(u);
                
                r.setId(res.getInt(1));
                r.setUser(user);
                r.setDate_depot(res.getDate(3));
                r.setEtat(res.getString(4));
                Demande d = new Demande();
                
                //String idt = String.valueOf(res.getInt(5));
                
                //if (idt==null)
                  //  d.setId(0);
                //else
                
                d.setId(res.getInt(5));
                
                r.setDemande(d);
                //r.getDemande().setId(res.getInt(5));
                
                System.out.println(r.toString());
                listR.add(r);
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listR;
    }
    
    public void updateReparation(Reparation r, Reparation newR){
        try {
            String req = "UPDATE reparation SET etat = ? "+
                    
                    " WHERE id = ? ";

            pre = cnx.prepareStatement(req);

            pre.setString(1,newR.getEtat());
            pre.setInt(2,r.getId());
            
            pre.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
   
    public User getUser(User user){
        return null ;
    }
    
     public void add(Reparation r){
        try {
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);


            String req = "INSERT INTO REPARATION (date_depot, etat, client_id, id_demande) values (?,?,?,?)";

            pre = cnx.prepareStatement(req);
            pre.setDate(1,date);
            pre.setString(2,"EnAttente");
            pre.setInt(3,r.getUser().getId());
            pre.setInt(4,r.getDemande().getId());
            //pre.setInt(4,9);
            pre.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
   
  
}
