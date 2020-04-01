/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author USER
 */
public class UserService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public UserService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    /*public void ajouuterPersonne(User u) {

        try {
            String req = "INSERT INTO user (nom, prenom) VALUES "
                    + "('" + u.getNom() + "', '" + u.getPrenom() + "')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }*/

    /*public void ajouuterPersonne1(User u) {

        try {
            String req = "INSERT INTO user (nom, prenom) VALUES (?, ?)";

            pre = cnx.prepareStatement(req);

            pre.setString(1, u.getNom());
            pre.setString(2, u.getPrenom());

            pre.executeUpdate();

            System.out.println("Insertion 2 Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }*/

    public List<User> getAllUser() {

        List<User> listP = new ArrayList<>();

        try {

            String req = "SELECT id,username,password,email,address,roles,last_login,enabled,photo FROM user";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                User u = new User();

                u.setId(res.getInt(1));
                u.setUsername(res.getString(2));
                u.setPassword(res.getString(3));
                u.setEmail(res.getString(4));
                u.setAddress(res.getString(5));
                u.setRoles(res.getString(6));
                u.setLast_login(res.getString(7));
                u.setEnabled(res.getInt(8));
                u.setPhoto(res.getString(9));

                listP.add(u);
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
    
    public User getUser(User user){
        return null ;
    }
}
