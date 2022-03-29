/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import model.farmer;
import dbconnection.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    
    
     int min = 00000;  
     int max = 10000; 
     int farmerNo =(int)(Math.random()*(max-min+1)+min);
     int min1 = 000000;  
     int max1 = 100000; 
        
  //   Random rnd = new Random();
     int receiptNo =(int)(Math.random()*(max1-min1+1)+min1);
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
          String q = "INSERT INTO `farmers`( `firstname`, `secondname`, `surname`, `idnumber`, `box_no.`, `village`, `phonenumber`, `farmernumber`, `gender`)"+ " VALUES (?,?,?,?,?,?,?,?,?)";
          pst = conn.prepareStatement(q);
          
          pst.setString(1,firstname.getText());
          pst.setString(2, secondname.getText());  
          pst.setString(3, surname.getText());
          pst.setString(4, idnum.getText());
          pst.setString(5, boxnum.getText());  
          pst.setString(6, vallages.getValue());
          pst.setString(7, phonenum.getText());
          pst.setInt(8,farmerNo);
          pst.setString(9, genders);  
          pst.execute();
          setValues();
          clearfields();
            System.out.println("success");
        } catch (SQLException ex) {
            System.out.println(ex);            }
   
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
     private void Printdeliverynote() throws FileNotFoundException, DocumentException, IOException { 
        
     
       String pdfnane = ""+receiptNo+"farmer.pdf";
       
       Document document = new Document();
      //Create OutputStream instance.
	OutputStream outputStream = 
	    new FileOutputStream(new File("C:\\Users\\DAN\\Documents\\farmerRprt\\"+pdfnane+""));
        
        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);
 
        //Open the document.
        document.open();
 
        //Create Table object, Here 4 specify the no. of columns
//         PdfPTable pdfPTable2 = new PdfPTable(1);
//         PdfPCell pdfPC00= new PdfPCell(new Paragraph("                                    DELIVERY COLLECTION NOTE"));
//           
//        pdfPC00.setBorder(Rectangle.NO_BORDER);
//        pdfPTable2.addCell(pdfPC00);
        
        
        PdfPTable pdfPTable3 = new PdfPTable(1);
        PdfPCell pdfPC002 = new PdfPCell(new Paragraph("                                                    "));
        pdfPC002.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC002);
        
        PdfPTable pdfPTable4 = new PdfPTable(1);
        PdfPCell pdfPC003 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC003.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC003);
        
       
        
        PdfPTable pdfPTable6 = new PdfPTable(1);
        PdfPCell pdfPC006 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC006.setBorder(Rectangle.NO_BORDER);
        pdfPTable6.addCell(pdfPC006);
        
        PdfPTable pdfPTable7 = new PdfPTable(1);
       
        PdfPCell pdfPC40= new PdfPCell(new Paragraph("          "+   reciept.getText()));
        
        pdfPC40.setBorder(Rectangle.NO_BORDER);
        
        pdfPTable7.addCell(pdfPC40);
        
        
        
        
        document.add(pdfPTable3);document.add(pdfPTable4);document.add(pdfPTable6);document.add(pdfPTable7);
        

        //Close document and outputStream.
        document.close();
        outputStream.close();
 
     
     
    }

    @FXML
    private void reciept(ActionEvent event) throws DocumentException, IOException {
        Printdeliverynote();
    }
}
