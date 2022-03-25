/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import dbconnection.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.farmerdelivery;


/**
 * FXML Controller class
 *
 * @author DAN
 */
public class DeliveryrecordController implements Initializable {
   
Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    farmerdelivery farmdev;
    
    
    @FXML
    private TextField farmernum;
    @FXML
    private TextField delivery;
    @FXML
    private Button print;
    @FXML
    private Button insert;
    @FXML
    private TextArea deliverynote;
        @FXML
    private Button close;
    @FXML
    private JFXComboBox<String> villages;
     ObservableList<String> village = FXCollections.observableArrayList("Village1","Village2 ","Village3","Village4");
    @FXML
    private TableView<farmerdelivery> tabledelivery;
    @FXML
    private TableColumn<farmerdelivery, Integer> colfarmernum;
    @FXML
    private TableColumn<farmerdelivery, String> colvillage;
    @FXML
    private TableColumn<farmerdelivery, String> coldate;
    @FXML
    private TableColumn<farmerdelivery, Double> colamount;
    
    
    @FXML
    private JFXDatePicker datedevry;
    @FXML
    private JFXButton update;
    @FXML
    private Label farmerNo;

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         villages.setItems(village);
         update.setDisable(true);
         insert.setDisable(true);
        showfarmer(); 
        
        //disbable();
      //  System.out.println(""+datedev);
    }  
    @FXML
    private void insertdata(ActionEvent event) throws SQLException {
    String datedev = datedevry.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
       
        try {
          String q = "INSERT INTO produce(id,farmernumber,village,day,amount)"+ " VALUES (?,?,?,?,?)";
          pst = conn.prepareStatement(q);
          pst.setInt(1,0);
          pst.setString(2,farmernum.getText());
          pst.setString(3, villages.getValue());
          pst.setString(4, datedev );
          pst.setString(5, delivery.getText());  
          pst.execute();setvalues(); showfarmer();
          clearfields();
          System.out.println("inserted");
        }   catch (SQLException ex) {
            System.out.println(ex);            }
          
    
    }
    //making farmernum textfield to only accept letters
    @FXML
    private void numvalidate(KeyEvent event) {
                  farmernum.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          farmernum.setText(newValue.replaceAll("[^\\d]", ""));
        }
    });
    }
    @FXML
    private void closebuttononaction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    } 
   public ObservableList<farmerdelivery> getfarmer(){
      ObservableList<farmerdelivery> farmerlist = FXCollections.observableArrayList(); 
      String query = "SELECT * FROM produce WHERE day = CURDATE()";
      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          
          
          while(rs.next()){
              farmdev = new farmerdelivery(rs.getInt("farmernumber"),rs.getString("village"),rs.getString("day"),rs.getDouble("amount"));
              farmerlist.add(farmdev);
          }
      }catch(SQLException ex){
        System.out.println(ex);  
      }
      return farmerlist;
    }
   
     public void showfarmer(){
      ObservableList<farmerdelivery> list =  getfarmer();
     colfarmernum.setCellValueFactory(new PropertyValueFactory<>("farmernumber"));
     colvillage.setCellValueFactory(new PropertyValueFactory<>("village"));
     coldate.setCellValueFactory(new PropertyValueFactory<>("day"));
     colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));

           
     tabledelivery.setItems(list);
}
      public void clearfields(){
            farmernum.setText("");villages.setValue("");delivery.setText("");
           //boxnum.setText("");//vallages.getValue("");
        }

    @FXML
    private void apudatefarmer() {
       farmerdelivery famdev = tabledelivery.getSelectionModel().getSelectedItem();
       villages.setValue(""+famdev.getVillage());farmernum.setText(""+famdev.getFnum());
      //datedevry.setValue("");
      delivery.setText(""+famdev.getAmount());
          
       update.setDisable(false);
       insert.setDisable(true);
   
    }
    public void setvalues(){
         
    deliverynote.setText("\t\t\t\t DELIVERY COLLECTION NOTE\n"
             //+"\t\n"+("D:\\4.2project\\milk\\src\\images/log")
            +"\t============================="
            + "\n\n\t\tDate:\t "+datedevry.getValue()
            +"\n\n\t\tFARMER NO.:\t"+farmernum.getText()
            //+"\n\nVILLAGE:\t"+village.getText()
            +"\n\n\t\tAMOUNT DELIVERED:\t"+delivery.getText()+"\t litres\n"
            +"\t==============================="
    );
    }
    public void disbable(){
    try{
        if((!delivery.getText().isEmpty()) || (!farmernum.getText().isEmpty()) ||( !villages.getValue().isEmpty())) {
        insert.setDisable(false);

    } else {
       insert.setDisable(true);  
    }
    }catch(HeadlessException ex){
        System.out.println(ex);
    }
    
    }

    @FXML
    private void updateTable(ActionEvent event) {
        
     try{
       PreparedStatement pst = conn.prepareStatement("UPDATE produce  SET amount= '"+delivery.getText()+"' WHERE farmernumber='"+farmernum.getText()+"' ");
       pst.executeUpdate();
       showfarmer();
       tabledelivery.requestFocus();
       update.setDisable(true);
       insert.setDisable(true);
       setvalues();
       clearfields();
     }   catch(Exception ex){
         ex.printStackTrace();
     }

    }

    @FXML
    private void testEnter(ActionEvent event) {
        
        
       try{
           
          String famNo = " SELECT farmernumber,village FROM  farmers WHERE farmernumber='"+farmernum.getText()+"'"; 
          pst = conn.prepareStatement(famNo);
          rs = pst.executeQuery();
          while(rs.next()){
              int FamN = Integer.parseInt(farmernum.getText());
              int test = rs.getInt("farmernumber");
              String vill=rs.getString("village");
              if((FamN==test)){
                 farmerNo.setText("Registed Member "+test);
                 villages.setValue(""+vill);
                 insert.setDisable(false);
              
                              
              }
              if(!(FamN==test)) {
                    farmerNo.setText("Not Regiseted Member"+test);
                     
              }
              
          }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "iko apa");
       }

    }
 
    }
    

