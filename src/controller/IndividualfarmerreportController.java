/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dbconnection.*;
import model.individualreport;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author DAN
 */
public class IndividualfarmerreportController implements Initializable {
Connection conn = dbconnection.milk_db();
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    @FXML
    private JFXButton close;
    @FXML
    private JFXDatePicker startdate;
    @FXML
    private JFXDatePicker enddate;
    @FXML
    private JFXTextField farmernum;
    @FXML
    private JFXButton search;
    @FXML
    private Button print;
    @FXML
    private JFXTextArea report;
   
    /**
     * Initializes the controller class.
     */
      ArrayList<String[]> data = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onprint(ActionEvent event) {
    }

    @FXML
    private void closeinterface(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getreport(ActionEvent event) {
   DisplayFarmer();DisplayAmount();TotalLitre();
    }
void DisplayFarmer(){
   String start = startdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
   String end = enddate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
try{
    String famNo = " SELECT farmers.firstname,farmers.secondname,farmers.surname,farmers.village,produce.day,produce.amount FROM farmers INNER JOIN produce ON farmers.farmernumber='"+farmernum.getText()+"' AND produce.farmernumber='"+farmernum.getText()+"' AND produce.day BETWEEN '"+start+"' AND '"+end+"'ORDER BY day DESC "; 
          pst = conn.prepareStatement(famNo);
          rs = pst.executeQuery();
          if(rs.next()){
          String name1 = rs.getString("firstname");
              String name2 = rs.getString("secondname");
              String name3 = rs.getString("surname");              
              String vill=rs.getString("village");
               report.setText("\t\t\t\t\t\t\t\t\t\t\t FARMERS REPORT \n"
             //+"\t\n"+("D:\\4.2project\\milk\\src\\images/log")
            +"==========================================================================="
            + "\n\n\t\tName:\t "+name1+ "\t "+name2+ "\t "+name3  +"\t\t\tFARMER NO.:\t"+farmernum.getText()
            +"\n\n\t\tVILLAGE:\t"+vill+"\t\tFrom Date:\t"+startdate.getValue() +"\t\tTo Date:\t"+enddate.getValue()
            +"\n==========================================================================="
                  +"\n\n\tDAY\t\t\t\t\t\t\tAMOUNT"
             );
          }  
}catch(Exception ex){
    ex.printStackTrace();
}
}
public void DisplayAmount(){
   String start = startdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
   String end = enddate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
      try{
           
          String famNo = " SELECT day,amount FROM  produce WHERE farmernumber ='"+farmernum.getText()+"' AND day BETWEEN '"+start+"' AND '"+end+"'ORDER BY day DESC"; 
          pst = conn.prepareStatement(famNo);
          rs = pst.executeQuery();
          while(rs.next()){
              int ltrAmount = rs.getInt("amount");
              String Date=rs.getString("day");
              
              String[] singleData = new String[2];
              singleData[0] = ""+ltrAmount;
              singleData[1] = ""+Date;
              
              data.add(singleData);
                report.appendText("\n\n\t" +  " "+Date
              +"\t\t\t\t\t\t"+ltrAmount
                );
          }
         
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "iko apa");
       }

}
public void TotalLitre(){
   String start = startdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
   String end = enddate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
      try{
           
          String famNo = "SELECT SUM(produce.amount), rates.cost FROM rates INNER JOIN produce WHERE produce.day>='"+start+"' AND produce.day<='"+end+"' AND rates.startday>='"+start+"' AND rates.endday<='"+end+"' AND produce.farmernumber='"+farmernum.getText()+"' "; 
          pst = conn.prepareStatement(famNo);
          rs = pst.executeQuery();
          while(rs.next()){
              int ltrAmount = rs.getInt("SUM(produce.amount)");
              double cst=rs.getDouble("rates.cost");
              double payment=ltrAmount*cst;
                report.appendText("\n\n\t"+"Total Litres Delivered :: "+ltrAmount+"LRS"
                  + "\n\t"+"COST PER LITRE IN KSH :: "+cst   
                        +"\n\t"+"TOTAL PAYMENT FOR THE MONTH::"+payment
                );
                
//          String famN = " SELECT cost FROM  rates WHERE startday BETWEEN '"+start+"' AND  '"+end+"'"; 
//          pst = conn.prepareStatement(famN);
//          rs = pst.executeQuery();
//          
//             while(rs.next()){   
//             int ltrAmout = rs.getInt("cost");
//                report.appendText("\n\t"+"Total Litres Delivered :: "+ltrAmout
//                );
//              
//             } 
          //  int total = ltrAmout * ltrAmount;
          }    
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "iko apa");
       }

}

}
