/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Ordre;
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
public class OrdreService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public OrdreService() {
        cnx = MyConnection.getInstance().getCnx();
    }



    /*public List<Reparation> getReparations() {

        List<Reparation> listR = new ArrayList<>();

        try {

            String req = "SELECT id,client_id,date_depot,etat FROM reparation";

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
                
                System.out.println(r.toString());
                listR.add(r);
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listR;
    }*/
    
    /*public void updateReparation(Reparation r, Reparation newR){
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
    }*/
    
    public void addOrdre(Ordre o){
         try {
            String req = "INSERT INTO ORDRE (id, demande_id, etat, dateOrdre) values (?,?,?,?) ";

            pre = cnx.prepareStatement(req);

            pre.setInt(1,o.getId());
            pre.setInt(2,o.getDemande().getId());
            pre.setString(3,o.getEtat());
            
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            
            pre.setDate(4,date);
            
            pre.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
   
    public User getUser(User user){
        return null ;
    }
}
