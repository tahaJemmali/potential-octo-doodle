/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Pos;
import javafx.util.Duration;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.controlsfx.control.Notifications;

/**
 *
 * @author USER
 */
public class Excel {
        private Connection cnx;
        private Statement st;
        private PreparedStatement pre;
        
        public Excel() {
        cnx = MyConnection.getInstance().getCnx();
        }
        
    public void WritetoExcelProduits() throws  IOException, SQLException {
        
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        
                        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("List Products");
        
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("ID");
        HSSFCell cell1 = row0.createCell(1);
        cell1.setCellValue("REFERENCE");
        HSSFCell cell2 = row0.createCell(2);
        cell2.setCellValue("NOM");
        HSSFCell cell3 = row0.createCell(3);
        cell3.setCellValue("CATEGORIE");
        HSSFCell cell5 = row0.createCell(4);
        cell5.setCellValue("PRIX UNITAIRE");
        HSSFCell cell6 = row0.createCell(5);
        cell6.setCellValue("STOCK");
        HSSFCell cell7 = row0.createCell(6);
        cell7.setCellValue("DATE D'AJOUT");
       
        
                            int i = 1;
                            String req = "SELECT * FROM product";
                                st = cnx.createStatement();
                                ResultSet rs = st.executeQuery(req);
        while (rs.next())
        { int j=0;
            HSSFRow row = sheet.createRow(i);
            
            sheet.setColumnWidth(i, 10000);
            HSSFCell cell ;//= row.createCell(j);
                        
            cell = row.createCell(j++);
            cell.setCellValue(rs.getInt("id"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("refrence"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("Name"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("Category"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getFloat("Price"));
                        
            cell = row.createCell(j++);
            cell.setCellValue(rs.getInt("Stock"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("Date"));
            i++;
        }
        
        workbook.write(new FileOutputStream("C:\\Users\\USER\\Desktop\\Excel-Java\\Produits.xls"));
        workbook.close();
        notification("Fichier Excel ","Liste des produits est génere !");
        }
    public void WritetoExcelUsers() throws   IOException, SQLException {
        
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        
                        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("List Users");
        
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("ID");
        HSSFCell cell1 = row0.createCell(1);
        cell1.setCellValue("Username");
        HSSFCell cell2 = row0.createCell(2);
        cell2.setCellValue("Adresse e-mail");
        HSSFCell cell3 = row0.createCell(3);
        cell3.setCellValue("Derniere connection");
        HSSFCell cell5 = row0.createCell(4);
        cell5.setCellValue("Role");
        HSSFCell cell6 = row0.createCell(5);
        cell6.setCellValue("Adresse");
        HSSFCell cell7 = row0.createCell(6);
        cell7.setCellValue("Téléphone");
       
        
                            int i = 1;
                            String req = "SELECT * FROM user";
                                st = cnx.createStatement();
                                ResultSet rs = st.executeQuery(req);
        while (rs.next())
        { int j=0;
            HSSFRow row = sheet.createRow(i);
            
            sheet.setColumnWidth(i, 10000);
            HSSFCell cell ;//= row.createCell(j);
                        
            cell = row.createCell(j++);
            cell.setCellValue(rs.getInt("id"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("username"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("email"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("last_login"));
            
            cell = row.createCell(j++);
            String ll ="";
            if (rs.getString("roles").equals("a:2:{i:0;s:10:\"ROLE_ADMIN\";i:1;s:16:\"ROLE_SUPER_ADMIN\";}"))
                ll="Super Admin";
            else if (rs.getString("roles").equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}"))
                ll="Admin";
            else
                ll="User";
            cell.setCellValue(ll);
                        
            cell = row.createCell(j++);
            cell.setCellValue(rs.getString("address"));
            
            cell = row.createCell(j++);
            cell.setCellValue(rs.getInt("phone"));
            i++;
        }
        
        workbook.write(new FileOutputStream("C:\\Users\\USER\\Desktop\\Excel-Java\\Users.xls"));
        workbook.close();
        notification("Fichier Excel ","Liste des utilisateur est génere !");
        }
    
    public void notification (String title,String text)
	{
		Notifications notif  = Notifications.create();
		notif.title(title);
		notif.text(text);
		notif.graphic(null);
		notif.hideAfter(Duration.seconds(2));
		notif.position(Pos.CENTER);
                notif.showConfirm();

	}
}
