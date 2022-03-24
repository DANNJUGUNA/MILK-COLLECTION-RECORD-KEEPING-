/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dbconnection.*;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class LoginController implements Initializable {
    Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;


    @FXML
    private TextField username;
    @FXML
    private PasswordField pass;
    @FXML
    private Button login;
    @FXML
    private Button cancel;
    @FXML
    private Label logintextlable;

    Scene scene;

    public void onwritting(ActionEvent event) {
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                username.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
    } 
   /* public void onfilling(ActionEvent event){
    pass.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            pass.setText(newValue.replaceAll("[^\\d]", ""));
    } */

    /**
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void loginOnAction(ActionEvent event) throws SQLException, IOException {

        /**
         *
         * @param event
         */

        String usernme = username.getText();
        String pas = pass.getText();
        try {
                 if ((username.getText().isEmpty())&(pass.getText().isEmpty())) {
                logintextlable.setText("Please enter Username and password");
                return;
            }

                 else if (username.getText().isEmpty()) {
                logintextlable.setText(" Please enter Username");
                return;
            }
                 else if (pass.getText().isEmpty()) {
                logintextlable.setText("Please enter your password");
                return;
            }


            //showAlert(Alert.AlertType.CONFIRMATION, JoptionPane.getScene().getWindow(), "Registration Successful!", "Welcome " + nameField.getText());


            String q = "SELECT Username,password FROM `admin` WHERE Username = ? AND password = ?";
            pst = conn.prepareStatement(q);
            pst.setString(1, usernme);
            pst.setString(2, pas);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                logintextlable.setText("WELCOME");
                Parent register = FXMLLoader.load(getClass().getResource("/gui/home.fxml"));
                Scene scene = new Scene(register);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.centerOnScreen();
                window.show();

            } else {
                logintextlable.setText("wrong password");
                pass.setText("");
                
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void infoBox(String inforMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(inforMessage);
        alert.showAndWait();

    }


    @FXML
    public void cancelOnAction(ActionEvent e) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}


    