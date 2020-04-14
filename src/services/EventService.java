/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Evenement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import utils.MyConnection;

/**
 *
 * @author tahtouh
 */
public class EventService {
    private final Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public EventService() {
        cnx=MyConnection.getInstance().getCnx();
    }
     
    
    public List<Evenement> getAll() {
//System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        List<Evenement> listP = new ArrayList<>();

        try {

            String req = "SELECT * FROM Evenement";
            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
               
              /*  System.out.println(res.getInt(1));
                System.out.println(res.getString(2));
                System.out.println(res.getString(3));
                System.out.println(res.getString(4));
                
                System.out.println(res.getDate(5).toLocalDate());
                
                System.out.println(res.getDate(6).toLocalDate());
                System.out.println(res.getInt(7));
                System.out.println(res.getString(8));
                System.out.println(res.getInt(9));*/
                
                Evenement e = new Evenement();
                e.setId(res.getInt(1));
                e.setTitre(res.getString(3));
                e.setDescription(res.getString(4));
                e.setImage(res.getString(5));
                e.setDate_debut(res.getDate(6).toLocalDate());
                e.setDate_creation(res.getDate(7).toLocalDate());
                e.setMax_participant(res.getInt(8));
                e.setLocation(res.getString(9));
                e.setScore_id(res.getInt(2));
                
                
                listP.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
 
        return listP;
    }
    
    public void ajouterScore(int first,int secound,int third){
                try {
            String req = "INSERT INTO Score (first, secound,third) VALUES (?, ?, ?)";

            pre = cnx.prepareStatement(req);

            pre.setInt(1, first);
            pre.setInt(2, secound);
            pre.setInt(3, third);
            pre.executeUpdate();

            System.out.println("Insertion  Reussie!"); 

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    
       public void addEvent(Evenement e,int first,int secound,int third) {       
int id=6;
        try {
            ajouterScore(first,secound,third);
          st = cnx.createStatement();
        String req1 = "SELECT max(id) FROM score ";
           ResultSet res = st.executeQuery(req1);
             while (res.next()) {
            id=res.getInt(1);
             }
             System.out.println(id);
           String req = "INSERT INTO Evenement (titre, description,image,date_debut,date_creation,max_participant,localisation,score_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pre = cnx.prepareStatement(req);

            pre.setString(1, e.getTitre());
            pre.setString(2, e.getDescription());
            pre.setString(3, e.getImage());
            pre.setDate(4, Date.valueOf(e.getDate_debut()));
            pre.setDate(5, Date.valueOf(e.getDate_creation()));
            pre.setInt(6, e.getMax_participant());
            pre.setString(7, e.getLocation());
            pre.setInt(8, id);
            pre.executeUpdate();
            System.out.println("Insertion  Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
       
       public void modifier(String nomColumn,Object o,int id) throws IOException, SQLException
       {
           String type="";
           String table="Evenement";
           if (o instanceof String)
           {
                if(nomColumn.equals("image"))
                {
                    ////Delete old image
                    SupprimerImage(id);
          //////UPLOAD IMAGE
        String newName="uploads/"+randomIdentifier()+"."+ FilenameUtils.getExtension((String) o);
         String PathTo= "C:/wamp64/www/integrationvelo/web/"+newName ; 
        Files.copy(Paths.get((String) o), Paths.get(PathTo), StandardCopyOption.REPLACE_EXISTING);
        /////
        o=newName;
                }
           
           }
           else if(o instanceof LocalDate) {
    
           }
           else{
               if (nomColumn.equals("first") || nomColumn.equals("secound")||nomColumn.equals("third"))
               {
                   table="score";
                    String req1="Select score_id from Evenement where id="+id;
                   st = cnx.createStatement();
                      ResultSet res = st.executeQuery(req1);
                     while (res.next()) {
                        id=res.getInt(1);
                        }
               }   
           }   
        String req = "UPDATE `"+table+"` SET `"+nomColumn+"` = '" + o + "' WHERE `"+table+"`.`id` = " + id ;
        System.out.println(req);
        pre = cnx.prepareStatement(req);

        pre.execute();
       }
       
    /*Random algorithm */
      
final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
final java.util.Random rand = new java.util.Random();
final Set<String> identifiers = new HashSet<String>();


       public String randomIdentifier() {
    StringBuilder builder = new StringBuilder();
    while(builder.toString().length() == 0) {
        int length = rand.nextInt(5)+5;
        for(int i = 0; i < length; i++) {
            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
        }
        if(identifiers.contains(builder.toString())) {
            builder = new StringBuilder();
        }
    }
    return builder.toString();
       }
        /////////Random algortihm////////////////
       

      
       public int nombreParticipant(int id_event){
      
            int participant=0;
        try {

            String req = "SELECT count(*) FROM participation where event_id="+id_event;

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
             while (res.next()) {
            participant=res.getInt(1);
             }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
 
        return participant;
       }
       
       public int[] getScore(int id){
int a1=-1;  
int a2=-1;  
int a3=-1;  
           try {
            String req = "SELECT * FROM score where id="+id;

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while(res.next()){
                a1=res.getInt(2);
                a2=res.getInt(3);
                a3=res.getInt(4);
            }           
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return new int[] {a1,a2,a3};
       }
       
       public void SupprimerEvenement(int id) throws SQLException{
            ////Delete old image
                    SupprimerImage(id);
        String req = "DELETE FROM `Evenement` WHERE `id` = " + id ;
        pre = cnx.prepareStatement(req);
        pre.execute();  
       } 
       
       public void SupprimerImage(int id) throws SQLException{
           String image="";
      String req1="Select image from Evenement where id="+id;
                   st = cnx.createStatement();
                      ResultSet res = st.executeQuery(req1);
                     while (res.next()) {
                        image=res.getString(1);
                        }
   
           try{
      File file = new File("C:/wamp64/www/integrationvelo/web/"+image);

      if(file.delete()){
       System.out.println(file.getName() + " est supprimé.");
      }else{
       System.out.println("Opération de suppression echouée");
      }

     }catch(Exception e){
      e.printStackTrace();
     }
           
       }
}
