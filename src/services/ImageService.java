/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author USER
 */
import entities.Images;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import utils.MyConnection;

public class ImageService {
    
        private Connection cnx;
        private Statement st;
        private PreparedStatement pre;
        
        public ImageService() {
        cnx = MyConnection.getInstance().getCnx();
    }
        
        public Set<Images> getAllImages_Produit(Produit produit) {

        Set<Images> listM = new HashSet <>();

        try {

            String req = "SELECT id,product,image FROM image where product= "+produit.getId();
            //System.out.println(produit.toString());
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
                
            while (res.next()) {
                Images m = new Images();

                m.setId(res.getInt(1));
                m.setProduit_id(res.getInt(2));
                m.setImage(res.getString(3));
                
               // System.out.println(m.toString());
                
                listM.add(m);
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listM;
    } 

    public void AjouterImagesProduit(Produit p, Images image) {
        
        int a=0;
           try {
            String req = "SELECT max(id) FROM product";
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
             a = res.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
           
        try {
            String req = "SET FOREIGN_KEY_CHECKS=0;";
            st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        try {
            String req = "INSERT INTO image (product,image,ordrer) VALUES (?,?,?)";

            pre = cnx.prepareStatement(req);
            
            pre.setInt(1,a);
            pre.setString(2, image.getImage());
            pre.setString(3,"default");
            
            pre.executeUpdate();
            
            System.out.println("Image ajoute!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        try {
            String req = "SET FOREIGN_KEY_CHECKS=1;";
            st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    public void AjouterImages(Produit p,Images image){
        try {
            String req = "INSERT INTO image (product,image,ordrer) VALUES (?,?,?)";

            pre = cnx.prepareStatement(req);
            
            pre.setInt(1,p.getId());
            pre.setString(2, image.getImage());
            pre.setString(3,"default");
            
            pre.executeUpdate();
            
            System.out.println("Image ajoute!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void supprimerImages(Produit p){
        try {
            String req = "Delete from image where product = "+p.getId();

            st = cnx.createStatement();

            st.executeUpdate(req);

            //System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
}
