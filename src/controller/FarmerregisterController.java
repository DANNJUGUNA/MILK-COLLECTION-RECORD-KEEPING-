/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.jfoenix.controls.JFXComboBox;
import model.farmer;
import dbconnection.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.image.Image;
  

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class FarmerregisterController implements Initializable {
Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    //gui commponnets
    @FXML
    private TextField firstname;
    @FXML
    private TextField secondname;
    @FXML
    private TextField surname;
    @FXML
    private TextField idnum;
    @FXML
    private TextField phonenum;
   
     @FXML
    private TextField boxnum;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
     @FXML
    private Button register;
      @FXML
    private Button print;
       @FXML
    private Button close;
    
       @FXML
       private TextArea reciept;
    @FXML
    private ToggleGroup gender;
    String genders; 
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    
     int min = 000000;  
     int max = 100000; 
     int farmerNo =(int)(Math.random()*(max-min+1)+min);
    @FXML
    private JFXComboBox<String> vallages;
     ObservableList<String> vallage = FXCollections.observableArrayList("Village1","Village2 ","Village3","Village4");
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      vallages.setItems(vallage);
    }    

    @FXML
    private void RegisterFarmer(ActionEvent event) {

        try {
          String q = "INSERT INTO `farmers`(`id`, `firstname`, `secondname`, `surname`, `idnumber`, `box_no.`, `village`, `phonenumber`, `farmernumber`, `gender`)"+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2,firstname.getText());
          pst.setString(3, secondname.getText());  
          pst.setString(4, surname.getText());
          pst.setString(5, idnum.getText());
          pst.setString(6, boxnum.getText());  
          pst.setString(7, vallages.getValue());
          pst.setString(8, phonenum.getText());
          pst.setInt(9,farmerNo);
          pst.setString(10, genders);  
          pst.execute();
          setValues();
          clearfields();
            System.out.println("success");
        } catch (SQLException ex) {
            System.out.println(ex);            }
   
    }
   /* @FXML
    public void generatepdf(ActionEvent event){
    PDFont font =  PDType1Font.HELVETICA_BOLD ; 
             PDDocument doc    = new PDDocument();
             PDPage page = new PDPage();
             PDPageContentStream content;
            try {
                content = new PDPageContentStream(doc, page);
                content.beginText();
                content.moveTextPositionByAmount(300, 400);
                content.setFont(font, 40);
                content.drawString(reciept.getText());

               content.endText();
                content.close();
                doc.addPage(page);
                doc.save("example.pdf");
               } catch (IOException ex) {
                Logger.getLogger(JavaFXApplication5.class.getName()).log(Level.SEVERE, null, ex);
            }
    }*/
    @FXML
    public void pdf(ActionEvent event){
        //.setLineWrap(true);
    }
    
@FXML
        public void selectgender(ActionEvent event) {
        if (male.isSelected()) {
            genders = male.getText();
        } else if (female.isSelected()) {
            genders = female.getText();
        }
    }
        public void clearfields(){
            firstname.setText("");secondname.setText("");surname.setText("");
            idnum.setText("");boxnum.setText("");phonenum.setText("");vallages.setValue("");
        }
        public void setValues(){
            reciept.setText("\t\t\t\t\t\tFARMER REGISTRATION  "
                    //+"\t\t\t/images/log.png"
                    + "\n\nDate\t "+dateFormat.format(new Date())
                    +"\n\nName:\t "+firstname.getText()
                    +"\t"+secondname.getText()+ "\t"+surname.getText()
                  
                    +"\n\nPhone No. "+phonenum.getText()+"\t\t\t\tId No. "+idnum.getText()
                    +"\n\nFarmer Registration No.\t "+farmerNo
                    +"\n\n\t\t\tthank for registering as new member"
                    
                    
                    
                    );
        }
//making textfield firstname to accept only letters
    @FXML
    private void namevalidate(KeyEvent event) {
                  firstname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          firstname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
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
    //printing 

    @FXML
    private void boxvalidate(KeyEvent event) {
                 boxnum.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          boxnum.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }); 
    }

    @FXML
    private void secondnamevalidate(KeyEvent event) {
                 secondname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
          secondname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });
        
    }

    @FXML
    private void surnamevalidate(KeyEvent event) {
                 surname.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
         surname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
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
    private void closebuttononaction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
