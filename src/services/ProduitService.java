/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Images;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import utils.MyConnection;
/**
 *
 * @author USER
 */
public class ProduitService {
        private Connection cnx;
        private Statement st;
        private PreparedStatement pre;
        
        public ProduitService() {
        cnx = MyConnection.getInstance().getCnx();
    }
        
        
       public Map<Produit,Set <Images>> getAllProduit() {
        ImageService is = new ImageService();
        
        //List<Produit> listP = new ArrayList<>();
        Map <Produit,Set <Images>> Produits = new TreeMap <> ((p1,p2)->p1.getId()-p2.getId());
        
        try {

            String req = "SELECT * FROM product";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Produit p = new Produit();
                //System.out.println(res.getString(3));
                p.setId(res.getInt(1));
                p.setReference(res.getString(2));
                p.setName(res.getString(3));
                p.setCategory(res.getString(4));
                p.setPrice(res.getDouble(5));
                p.setStock(res.getInt(6));
                p.setDate(res.getDate(7));
                p.setDescription(res.getString(8));
                
                Produits.put(p, is.getAllImages_Produit(p));

            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return Produits;
    }
       
       public void AjouterProduit(Produit p) {
           int a=0;
           try {
            String req = "SELECT max(id) FROM product";
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
             a = res.getInt(1)+1;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
           
        try {
            String req = "INSERT INTO product (id,refrence, Name,Category,Price,Stock,Date,Description) VALUES (?,?,?,?,?,?,?,?)";

            pre = cnx.prepareStatement(req);
            
            pre.setInt(1,a);
            pre.setString(2, p.getReference());
            pre.setString(3, p.getName());
            pre.setString(4, p.getCategory());
            pre.setDouble(5, p.getPrice());
            pre.setInt(6, p.getStock());
            
            
            long millis=System.currentTimeMillis();  
            java.sql.Date date=new java.sql.Date(millis);  
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy/MM/dd" );
            //LocalDate localDate = LocalDate.parse( date.toString(), formatter );
            
            System.out.print(" ----- "+date.toString());
            
            pre.setObject( 7, date );
            pre.setString(8, p.getDescription());

            pre.executeUpdate();

            System.out.println("Produit ajoute!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }/*finally{
                    System.out.println( "ZZZZZZZZZZZDDDDDDDDD : "+getProduit(p).toString());

        }*/
        
       

    }
       
       public Produit getProduit(Produit produit){
           Produit p = new Produit();
           try {
            String req = "SELECT * FROM product where refrence = "+produit.getReference();
            
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                //System.out.println(res.getString(3));
                p.setId(res.getInt(1));
                p.setReference(res.getString(2));
                p.setName(res.getString(3));
                p.setCategory(res.getString(4));
                p.setPrice(res.getDouble(5));
                p.setStock(res.getInt(6));
                p.setDate(res.getDate(7));
                p.setDescription(res.getString(8));
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return p;
       }
       
       public void supprimerProduit(Produit p){
           
        try {
            String req = "Delete from product where id = "+p.getId();

            st = cnx.createStatement();

            st.executeUpdate(req);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
       
       public void ModifierProduit(Produit oldP,Produit newP){
            try {
            String req = "UPDATE product SET refrence = ? ,"+
                    "Name = ? ,"+
                    "Category = ? ,"+
                    "Price = ? ,"+
                    "Stock = ? ,"+
                    "Description = ? "+
                    " WHERE id = ? ";

            pre = cnx.prepareStatement(req);
            
            pre.setString(1,newP.getReference());
            pre.setString(2, newP.getName());
            pre.setString(3, newP.getCategory());
            pre.setDouble(4, newP.getPrice());
            pre.setInt(5, newP.getStock());
            pre.setString(6, newP.getDescription());
            pre.setInt(7, oldP.getId());

            pre.executeUpdate();

            System.out.println("Produit modifie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
       }
        public Produit getProduitId(Produit produit){
           Produit p = new Produit();
           try {
            String req = "SELECT * FROM product where id = "+produit.getId();
            
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                //System.out.println(res.getString(3));
                p.setId(res.getInt(1));
                p.setReference(res.getString(2));
                p.setName(res.getString(3));
                p.setCategory(res.getString(4));
                p.setPrice(res.getDouble(5));
                p.setStock(res.getInt(6));
                p.setDate(res.getDate(7));
                p.setDescription(res.getString(8));
            }
        
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return p;
       }
        
}
