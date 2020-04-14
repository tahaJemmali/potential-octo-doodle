/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academiccalendar.ui.main;

import entities.Evenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

/**
 *
 * @author Pytrooooo
 */
public class CalendarAnnuelService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public CalendarAnnuelService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void AddCalendar(CalendarAnnuel t) {
        String req = "Insert into calendarannuel ( subject, term ,DateC)  values (?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getSubject());
            pst.setString(2, t.getTerm());
            pst.setDate(3, t.getDateC());

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ObservableList<Evenement> GetCalendar() {
        String req = "select * from Evenement";
        ObservableList<Evenement> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId(rs.getInt(1));
                e.setTitre(rs.getString(2));
                e.setDescription(rs.getString(3));
                e.setImage(rs.getString(4));
                e.setDate_debut(rs.getDate(5).toLocalDate());
                e.setDate_creation(rs.getDate(6).toLocalDate());
                e.setMax_participant(rs.getInt(7));
                e.setLocation(rs.getString(8));
                e.setScore_id(rs.getInt(9));
                  list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public ObservableList<CalendarAnnuel> GetDateCalendar() {
        String req = "select * from calendarannuel ";
        ObservableList<CalendarAnnuel> list = FXCollections.observableArrayList();
        CalendarAnnuel ca = new CalendarAnnuel();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new CalendarAnnuel(rs.getInt("id"), rs.getString("subject"), rs.getString("term"), rs.getDate("DateC")));

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public void DeleteCalendar(int id) {
        String req = "delete from calendarannuel where id = " + id + " ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(CalendarAnnuelService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateCalendar(CalendarAnnuel t) {
        String req = "update calendarannuel set subject =?,term =?,datec=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getSubject());
            pst.setString(2, t.getTerm());
            pst.setDate(3, t.getDateC());
            pst.setInt(4, t.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CalendarAnnuelService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
