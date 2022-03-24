/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import dbconnection.dbconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.farmerdelivery;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class HomeController implements Initializable {
    Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    farmerdelivery farmdev;

    @FXML
    private Label user;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXButton resetpass;
    @FXML
    private JFXButton deliveryrecord;
    @FXML
    private JFXButton farmerreport;
    @FXML
    private JFXButton registerfarmer;
    @FXML
    private JFXButton ratings;
    @FXML
    private JFXButton centrereport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void resetpassword(ActionEvent event) throws IOException {
          Parent parent = FXMLLoader.load(getClass().getResource("/gui/resetpassword.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.show();
    }

    @FXML
    private void backtologin(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
         Scene scene = new Scene(parent);
         
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              
                stage.setScene(scene);
                 stage.centerOnScreen();
                stage.show();
                
    }

    @FXML
    private void recorddelivery(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/gui/deliveryrecord.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.centerOnScreen();
         stage.show();
    }

    @FXML
    private void farmersreport(ActionEvent event) throws IOException {
     Parent parent = FXMLLoader.load(getClass().getResource("/gui/individualfarmerreport.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.centerOnScreen();
         stage.show();
    }

    @FXML
    private void registerfarmers(ActionEvent event) throws IOException {
          Parent parent = FXMLLoader.load(getClass().getResource("/gui/farmerregister.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.centerOnScreen();
         stage.show();
        
    }

    @FXML
    private void insertratings(ActionEvent event) throws IOException {
         Parent parent = FXMLLoader.load(getClass().getResource("/gui/rates.fxml"));
         Scene scene = new Scene(parent);
         Stage stage  = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(scene);
         stage.centerOnScreen();
         stage.show();
    }

    @FXML
    private void centresreport(ActionEvent event) {
    }
     void systemUser(){
            try{
         String query = "SELECT Username admin WHERE Username=?";
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          
          while(rs.next()){
        user.setText(rs.getString(1));
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      }    
    }
    
}
