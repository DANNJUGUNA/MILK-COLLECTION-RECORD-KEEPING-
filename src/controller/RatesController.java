/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dbconnection.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class RatesController implements Initializable {
Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    @FXML
    private JFXDatePicker start;
    @FXML
    private JFXDatePicker end;
    @FXML
    private JFXButton insert;
    @FXML
    private JFXTextField cost;
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
    private void insertrates(ActionEvent event)throws SQLException{
    String startday = start.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    String endday = end.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
   try{
       String d="INSERT INTO rates(id,startday,endday,cost)"+ " VALUES (?,?,?,?)";
    pst = conn.prepareStatement(d);
          pst.setInt(1,1);
          pst.setString(2,startday);
          pst.setString(3, endday);
          pst.setString(4, cost.getText());
          pst.execute();
          clearfields();
   }
   catch(SQLException ex){
       System.out.println(ex); 
   }
    }

    @FXML
    private void costvalidate(KeyEvent event) {
          cost.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          cost.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });  
    }
     public void clearfields(){
            cost.setText("");start.setValue(null);end.setValue(null);
           //boxnum.setText("");//vallages.getValue("");
        }

    @FXML
    private void exist(ActionEvent event) {
         Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    
}
