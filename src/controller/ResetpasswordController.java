/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dbconnection.dbconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class ResetpasswordController implements Initializable {
     Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;

    @FXML
    private JFXTextField tfUsername;
  
    @FXML
    private Label notice;
    @FXML
    private JFXTextField oldpass;
    @FXML
    private JFXTextField newpass;

    @FXML
    private JFXTextField confirmnewpass;
    @FXML
    private Button backlogin;
    @FXML
    private JFXButton close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   

    @FXML
    private void changepass(ActionEvent event) {
        String npass=newpass.getText();
        String user=tfUsername.getText();
       if(tfUsername.getText().isEmpty()){
         notice.setText("Fail !Empty field");  
        tfUsername.requestFocus();
        }
         else if(oldpass.getText().isEmpty()){
          notice.setText("Fail !Empty field");  
      oldpass.requestFocus();       
        }else if(confirmnewpass.getText().isEmpty()){
             notice.setText("Fail !Empty field");  
          confirmnewpass.requestFocus(); 
       }
        else if(newpass.getText().isEmpty()){
           notice.setText("Fail !Empty field");  
        newpass.requestFocus();  
        }else if(oldpass.getText().matches(confirmnewpass.getText())){
         notice.setText("Fail !Your old Password should not be the same with the New Password !!");  
     oldpass.requestFocus();  confirmnewpass.requestFocus();     
          
    }
        else{
         try{
      String p="UPDATE admin SET password = '"+npass+"' WHERE Username = '"+user+"'";
       pst = conn.prepareStatement(p);
            pst.executeUpdate();
            
             notice.setText("Success !Successfully Set New Password");
              clearField();
       }catch(Exception ex){
           System.out.print("helolo");
         notice.setText("Fail !Failed to Set new password");
          clearField();
       }
    }}

    @FXML
    private void loginagain(ActionEvent event) throws IOException {
    Parent back = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
    Scene Scene = new Scene(back);  
   Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Scene);
                window.show();
    
    }
    public void clearField(){
        tfUsername.setText("");newpass.setText("");oldpass.setText("");confirmnewpass.setText("");
                   
    }

    @FXML
    private void closewindow(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
    

