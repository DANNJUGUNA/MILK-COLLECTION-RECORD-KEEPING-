/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import dbconnection.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ForgetpasswordController implements Initializable {
    Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;

    @FXML
    private HBox hb1;
    @FXML
    private AnchorPane finish;
    @FXML
    private JFXTextField userd;
    @FXML
    private JFXButton resetd;
    @FXML
    private HBox hb2;
    @FXML
    private JFXTextField usera;
    @FXML
    private JFXButton reseta;
    @FXML
    private HBox hb3;
    @FXML
    private JFXTextField userm;
    @FXML
    private JFXButton resetm;
    @FXML
    private JFXButton back;
    @FXML
    private HBox hbd;
    @FXML
    private JFXPasswordField passd;
    @FXML
    private JFXPasswordField cpassd;
    @FXML
    private JFXButton finishd;
    @FXML
    private HBox hba;
    @FXML
    private JFXPasswordField passa;
    @FXML
    private JFXPasswordField cpassa;
    @FXML
    private JFXButton finisha;
    @FXML
    private HBox hbm;
    @FXML
    private JFXPasswordField passm;
    @FXML
    private JFXPasswordField cpassm;
    @FXML
    private JFXButton finishm;
    
    
    
    
    
     public static ForgetpasswordController instance;
    
    
    public ForgetpasswordController(){
        instance = this;
    }
    
    public static ForgetpasswordController getInstance (){
        
        return instance;
    
    }
    
    public void driver(){
        hb1.setVisible(true);
        hb2.setVisible(false);
        hb3.setVisible(false);
        
        hbd.setVisible(false);
        hba.setVisible(false);
        hbm.setVisible(false);
    
    }
    public void park(){
    
         hb1.setVisible(false);
        hb2.setVisible(true);
        hb3.setVisible(false);
        
        hbd.setVisible(false);
        hba.setVisible(false);
        hbm.setVisible(false);
    
    }
    public void manager(){
    
     hb1.setVisible(false);
        hb2.setVisible(false);
        hb3.setVisible(true);
        
        hbd.setVisible(false);
        hba.setVisible(false);
        hbm.setVisible(false);
    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void resetd(MouseEvent event) {
        
         
        try {
           String uname = userd.getText();
            
           String r="SELECT * FROM admin WHERE username='"+uname+"'";
              st = conn.createStatement();
          rs = st.executeQuery(r);
            
            
         ;
            
            int count = 0;
            while(rs.next()){
            
               count++;
            
            
            }
            
            if(count == 1){
            hb1.setVisible(false);
            hbd.setVisible(true);
            
            
            }else{
            
                //UNIVERSAL.AlertE("User Does not Exit!!!");
              
            
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ForgetpasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    

    @FXML
    private void back(MouseEvent event) {
        
//        try {
//            UNIVERSAL.ViewsCallD("MAIN", "DAYCARE MANAGEMENT SYSTEM");
//            UNIVERSAL.Hide(finish);
//        } catch (IOException ex) {
//            Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    
    public boolean passconfirmd(){
    
    String rpass = passd.getText();
    
    String conpass = cpassd.getText();
    
    if (!conpass.equals(rpass)){
    
    //UNIVERSAL.AlertE("Password Mismatch");
    return false;
    }
    
    
    return true;
    }
    
    
     public boolean passconfirma(){
    
    String rpass = passa.getText();
    
    String conpass = cpassa.getText();
    
    if (!conpass.equals(rpass)){
    
    //UNIVERSAL.AlertE("Password Mismatch");
    return false;
    }
    
    
    return true;
    }
     
      public boolean passconfirmm(){
    
    String rpass = passm.getText();
    
    String conpass = cpassm.getText();
    
    if (!conpass.equals(rpass)){
    
    //UNIVERSAL.AlertE("Password Mismatch");
    return false;
    }
    
    
    return true;
    }
    
    
    @FXML
    private void finishd(MouseEvent event) {
        if(passconfirmd()){
        try {
           
            String sq = "UPDATE admin SET pass = ? WHERE email = ?";
              st = conn.createStatement();
          rs = st.executeQuery(sq);
            pst.setString(1, passd.getText());
            pst.setString(2, userd.getText());
            
            pst.executeUpdate();
          //  UNIVERSAL.AlertC("Password Reset Succefully!!!");
            passd.setText(null);
           userd.setText(null);
            cpassd.setText(null);
            hb1.setVisible(true);
           hbd.setVisible(false);
           
        } catch (SQLException ex) {
            Logger.getLogger(ForgetpasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @FXML
    private void reseta(MouseEvent event) {
    }

    @FXML
    private void resetm(MouseEvent event) {
    }

    @FXML
    private void finisha(MouseEvent event) {
    }

    @FXML
    private void finishm(MouseEvent event) {
    }
    
    

    
}
