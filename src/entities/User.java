/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import utils.BCrypt;

/**
 *
 * @author USER
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private int phone;
    private String roles;
    private String last_login;
    private int enabled;
    private String photo;
    
    public User (){
    }
    
    public User (String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public String getRoles() {
        return roles;
    }

    public String getLast_login() {
        return last_login;
    }

    public int getEnabled() {
        return enabled;
    }

    public String getPhoto() {
        return photo;
    }
    
    @Override
    public String toString (){
        return "ID : "+id+" Username : "+username+" Password : "+password+" Email : "+email+" Address : "+address+" Phone : "
                +phone+" Roles : "+roles+" Last login : "+last_login+" Enabled : "+enabled+" Photo : "+photo;
    }
   @Override
   public boolean equals(Object o) {
	if (o==null) {
		System.out.println("Object null");
		return false;
	}
    if ((o instanceof User)) 
	{
                final User e = (User) o ;
                /*System.out.println(this.getUsername());
                System.out.println(e.getUsername());*/
                //System.out.println("Alooooooo : "+ BCrypt.checkpw(e.getPassword(),this.getPassword()));
                if (this.getUsername().equals(e.getUsername()))
                {
                    if (this.getPassword()!=null){
                    if (BCrypt.checkpw(e.getPassword(),this.getPassword())){
                return true;
                            }
                    }
                }
                else {
                    //System.out.println("Object and this NON egaux");
                    return false;
                }   
		}
    //else {
		//System.out.println("Object is not a user");
		return false;
	//}
    
   }

   public boolean givenPassword_whenHashing_thenVerifying(String hash,String password) throws NoSuchAlgorithmException 
   { //jBCrypt bc = new jBCrypt ();
       
       System.out.println("aloooooooooo");
    System.out.println(hash);
    System.out.println(password);
    System.out.println("aloooooooooo");
    
    //String hash = "35454B055CC325EA1AF2126E27707052";
    //String password = "ILoveJava";
         
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes());
    byte[] digest = md.digest();
    String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
       
    System.out.println(myHash.equals(hash));
    return myHash.equals(hash);
   // return BCrypt.checkpw("","");
    //assertThat(myHash.equals(hash)).isTrue();
}
   
    /*public static boolean matching(String orig, String compare){
    
        String md5 = null;
    
    try{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(compare.getBytes());
        byte[] digest = md.digest();
        md5 = new BigInteger(1, digest()).toString(16);

        return md5.equals(orig);

        } 
            catch (NoSuchAlgorithmException e) {
        return false;
            }

    }*/
    
}
