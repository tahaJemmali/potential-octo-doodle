/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<User> getAllUser() {

        List<User> listP = new ArrayList<>();

        try {

            String req = "SELECT id,username,password,email,address,roles,last_login,enabled,photo,phone FROM user";

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
                u.setPhone(res.getInt(10));

                listP.add(u);
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
    
    public List<User> getAllUser1() {

        List<User> listP = new ArrayList<>();

        try {

            String req = "SELECT username FROM user";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                User u = new User();

                u.setUsername(res.getString(1));

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
    
    public User getUser1(User user){
        User u = new User();
        try {
            String req = "SELECT id,username,password,email,address,roles,last_login,enabled,photo,phone FROM user where id = "+user.getId();
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {

                u.setId(res.getInt(1));
                u.setUsername(res.getString(2));
                u.setPassword(res.getString(3));
                u.setEmail(res.getString(4));
                u.setAddress(res.getString(5));
                u.setRoles(res.getString(6));
                u.setLast_login(res.getString(7));
                u.setEnabled(res.getInt(8));
                u.setPhoto(res.getString(9));
                u.setPhone(res.getInt(10));

            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return u ;
    }
    
    public void Update_UserPassword(User user){        
            try {
            String req = "UPDATE user SET password = ? "+
                    " WHERE email = ? ";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,user.getPassword());
            pre.setString(2,user.getEmail());

            pre.executeUpdate();

            System.out.println("user password modifie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
       }
    
    public void Update_User(User user){
        try {
            String req = "UPDATE user SET username = ? ,password = ? ,email = ? ,address = ? ,photo = ? ,phone = ? ,username_canonical = ?,email_canonical = ? "+ /*, enabled = ? ,roles = ?*/
                    " WHERE id = ? ";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,user.getUsername());
            pre.setString(2,user.getPassword());
            pre.setString(3,user.getEmail());
            pre.setString(4,user.getAddress());
            pre.setString(5,user.getPhoto());
            pre.setInt(6,user.getPhone());
            //pre.setInt(7,user.getEnabled());
            //pre.setString(8,user.getRoles());
            
            pre.setString(7,user.getUsername());
            pre.setString(8,user.getEmail());
            
            pre.setInt(9,user.getId());
            

            pre.executeUpdate();

            System.out.println("user modifie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void Modifier_User(User user){
        try {
            String req = "UPDATE user SET username = ? ,password = ? ,email = ? ,address = ? ,photo = ? ,phone = ?, enabled = ? ,roles = ?,username_canonical = ?,email_canonical = ?  "+ 
                    " WHERE id = ? ";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,user.getUsername());
            pre.setString(2,user.getPassword());
            pre.setString(3,user.getEmail());
            pre.setString(4,user.getAddress());
            pre.setString(5,user.getPhoto());
            pre.setInt(6,user.getPhone());
            pre.setInt(7,user.getEnabled());
            pre.setString(8,user.getRoles());
            
            pre.setString(9,user.getUsername());
            pre.setString(10,user.getEmail());
            
            pre.setInt(11,user.getId());
            

            pre.executeUpdate();

            System.out.println("user modifie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void AddUser(User user){
        try {
            String req = "INSERT INTO user (username,username_canonical,email,email_canonical,enabled,password,roles,photo,address,phone) VALUES (?,?,?,?,?,?,?,?,?,?) ";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,user.getUsername());
            pre.setString(2,user.getUsername());
            pre.setString(3,user.getEmail());
            pre.setString(4,user.getEmail());
            pre.setInt(5,1);
            
            pre.setString(6,user.getPassword());
            
            pre.setString(7,user.getRoles());
            
            pre.setString(8,user.getPhoto());
            
            pre.setString(9,user.getAddress());
            
            pre.setInt(10,user.getPhone());

            pre.executeUpdate();

            System.out.println("user ajout� !");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public User getPassword_fromDB(User user){
        User u = new User();
        try {
            String req = "SELECT password FROM user where id = "+user.getId();
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                u.setPassword(res.getString(1));
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return u ;
    }
    
    public void delete_User(User u){
        try {
            String req = "Delete from user where id = "+u.getId();

            st = cnx.createStatement();

            st.executeUpdate(req);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void UserLoggedIn(User user){
        try {
            String req = "UPDATE user SET last_login = ?  "+ 
                    " WHERE id = ? ";

            pre = cnx.prepareStatement(req);
            
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
        
            pre.setString(1,String.valueOf(dtf.format(now)));
            pre.setInt(2,user.getId());

            pre.executeUpdate();

            System.out.println("user login modifie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void addUser(User u){
        try {

            String req = "INSERT INTO USER ( username, username_canonical, email,email_canonical,enabled,password,roles, address, phone) values (?,?,?,?,?,?,?,?,?)";

            pre = cnx.prepareStatement(req);

            pre.setString(1,u.getUsername());
            pre.setString(2,u.getUsername());
            pre.setString(3,u.getEmail());
            pre.setString(4,u.getEmail());
            pre.setInt(5,u.getEnabled());
            pre.setString(6,u.getPassword());
            pre.setString(7,u.getRoles());
            pre.setString(8,u.getAddress());
            pre.setInt(9,u.getPhone());

            pre.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
