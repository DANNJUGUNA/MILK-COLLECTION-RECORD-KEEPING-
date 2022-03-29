/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import dbconnection.dbconnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class UpdatefarmerdetailsController implements Initializable {

Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    @FXML
    private TextField boxnum;
    
    @FXML
    private TextField phonenum;
    @FXML
    private TextField idnum;
   
    @FXML
    private ToggleGroup gender;

    @FXML
    private Button update;
    @FXML
    private Button close;
    @FXML
    private TextField farmernumber;
    
    @FXML
    private Button search;
    @FXML
    private Label feedback;
   
    @FXML
    private JFXComboBox<?> villages;
    ObservableList<String> villge = FXCollections.observableArrayList("Village1","Village2 ","Village3","Village4");
     
    @FXML
    private TextField fname;
    @FXML
    private TextField sname;
    @FXML
    private TextField suname;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    String genders; 
    
     
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      //villages.setValue(villge);
     
    }    

    @FXML
    private void boxvalidate(KeyEvent event) {
                  boxnum.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          boxnum.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }

    @FXML
    private void namevalidate(KeyEvent event) {
                  fname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          fname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void secondnamevalidate(KeyEvent event) {
                sname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          sname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void surnamevalidate(KeyEvent event) {
                  suname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
         suname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
    }

    @FXML
    private void phonenumbervalidate(KeyEvent event) {
                   phonenum.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          phonenum.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }); 
    }

    @FXML
    private void idvalidate(KeyEvent event) {
                         idnum.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          idnum.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }); 
    }

    @FXML
    private void selectgender(ActionEvent event) {
                if (male.isSelected()) {
            genders = male.getText();
        } else if (female.isSelected()) {
            genders = female.getText();
        }
    }

    @FXML
    private void updatefarmer(ActionEvent event) {
           
     try{
       PreparedStatement pst = conn.prepareStatement("UPDATE farmers SET firstname= '"+fname.getText()+"',secondname= '"+sname.getText()+"',secondname= '"+sname.getText()+"',surname= '"+suname.getText()+"',phonenumber= '"+ phonenum.getText()+"',idnumber= '"+  idnum.getText()+"',box_no.= '"+ boxnum.getText()+"',village='"+villages.getValue()+"' WHERE farmernumber='"+farmernumber.getText()+"' ");
       pst.executeUpdate();
        }   catch(Exception ex){
         ex.printStackTrace();
     }
    }

    @FXML
    private void closebuttononaction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }


    

    @FXML
    private void searchfarmer(ActionEvent event) throws SQLException {  
        String a ="Male";
       String b="Female" ;
    try {
        String s="SELECT * FROM farmers WHERE farmernumber='"+farmernumber.getText()+"'";
        pst = conn.prepareStatement(s);
        rs = pst.executeQuery();
        while(rs.next()){
              int FamN = Integer.parseInt(farmernumber.getText());
        
       
       int test = rs.getInt("farmernumber");
       
           
             if(FamN==test){
                 
           fname.setText(rs.getString("firstname"));
        sname.setText(rs.getString("secondname"));
        suname.setText(rs.getString("surname"));
        phonenum.setText(rs.getString("phonenumber"));
        idnum.setText(rs.getString("idnumber"));
        boxnum.setText(rs.getString("box_no."));
                String vil=rs.getString("village");
              //  villages.setValue(vil);
       String gen=rs.getString("gender");
        if (      "b".equals(gen)){
              gender.selectToggle(female);
        } 
       else if("a".equals(gen)){
            gender.selectToggle(male);
                  }
        
        feedback.setText("member  exist ");
        }

        }
    } catch (SQLException ex) {
        Logger.getLogger(UpdatefarmerdetailsController.class.getName()).log(Level.SEVERE, null, ex);
    }
          
    }

    @FXML
    private void farmernumbervalidate(KeyEvent event) {
                    farmernumber.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          farmernumber.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }
    
    
}
