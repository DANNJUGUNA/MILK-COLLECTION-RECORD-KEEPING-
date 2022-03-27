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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dbconnection.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.individualreport;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
void printRecipt() throws DocumentException, IOException, SQLException{
    DisplayFarmer();

  
   String start = startdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
   String end = enddate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    int farmNam = Integer.parseInt(farmernum.getText());
    String pdfnane = ""+farmNam+"report.pdf";
       Document document = new Document();
      //Create OutputStream instance.
	OutputStream outputStream = 
	    new FileOutputStream(new File("C:\\Users\\DAN\\Documents\\farmerRprt\\"+pdfnane+""));
        
        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);
 
        //Open the document.
        document.open();
 
        //Create Table object, Here 4 specify the no. of columns
         PdfPTable pdfPTable2 = new PdfPTable(1);
         PdfPCell pdfPC00= new PdfPCell(new Paragraph("                                    FARMER MONTHLY REPORT"));
           
        pdfPC00.setBorder(Rectangle.NO_BORDER);
        pdfPTable2.addCell(pdfPC00);
        
        
        PdfPTable pdfPTable3 = new PdfPTable(1);
        PdfPCell pdfPC002 = new PdfPCell(new Paragraph("                                                    "));
        pdfPC002.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC002);
        
        PdfPTable pdfPTable4 = new PdfPTable(1);
        PdfPCell pdfPC003 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC003.setBorder(Rectangle.NO_BORDER);
        pdfPTable3.addCell(pdfPC003);
        
        PdfPTable pdfPTable05 = new PdfPTable(2);
        PdfPCell pdfPC03 = new PdfPCell(new Paragraph("Name: "+rs.getString("firstname")+"\t "+rs.getString("secondname")+"\t "+rs.getString("surname")));
        PdfPCell pdfPC04= new PdfPCell(new Paragraph("Farmer Reg Number: "+farmNam));
        pdfPC04.setBorder(Rectangle.NO_BORDER);
        pdfPC03.setBorder(Rectangle.NO_BORDER);
        pdfPTable05.addCell(pdfPC03);
        pdfPTable05.addCell(pdfPC04);
        
        PdfPTable pdfPTable5 = new PdfPTable(3);
        PdfPCell pdfPC3 = new PdfPCell(new Paragraph("Village: "+rs.getString("village")));
        PdfPCell pdfPC4= new PdfPCell(new Paragraph("Report From Date: "+start));
        PdfPCell pdfPCTo= new PdfPCell(new Paragraph("Report To Date: "+end));
        pdfPC4.setBorder(Rectangle.NO_BORDER);
        pdfPC3.setBorder(Rectangle.NO_BORDER);
        pdfPCTo.setBorder(Rectangle.NO_BORDER);
        pdfPTable5.addCell(pdfPC3);
        pdfPTable5.addCell(pdfPC4);
        pdfPTable5.addCell(pdfPCTo);
        
        PdfPTable pdfPTable6 = new PdfPTable(1);
        PdfPCell pdfPC006 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC006.setBorder(Rectangle.NO_BORDER);
        pdfPTable6.addCell(pdfPC006);
        
        
         PdfPTable pdfPTable7 = new PdfPTable(1);
        PdfPCell pdfPC0061 = new PdfPCell(new Paragraph("                                                     "));
        pdfPC0061.setBorder(Rectangle.NO_BORDER);
        pdfPTable6.addCell(pdfPC0061);
        
        
        PdfPTable pdfPTable8 = new PdfPTable(1);
        PdfPCell pdfPC31 = new PdfPCell(new Paragraph("Date:"+dateFormat.format(new Date())));
        pdfPC31.setBorder(Rectangle.NO_BORDER);
        pdfPTable8.addCell(pdfPC31);
      
        
        
        PdfPTable pdfPTable = new PdfPTable(2);
        PdfPCell pdfPC1 = new PdfPCell(new Paragraph("AMOUNT DELIVERED"));
        PdfPCell pdfPC2= new PdfPCell(new Paragraph("DATE"));
        pdfPTable.addCell(pdfPC2);
        pdfPTable.addCell(pdfPC1);
       pdfPC006.setBorder(Rectangle.NO_BORDER);
       
 
        for(int i=0;i<data.size();i++){
            
        
        //Create cells
        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(data.get(i)[1]));
        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(data.get(i)[0]));
     
 
        //Add cells to table
        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(pdfPCell2);
        pdfPC006.setBorder(Rectangle.NO_BORDER);
        
 
        //Add content to the document using Table objects.
        }
       PdfPTable pdfPTable30 = new PdfPTable(2);
        PdfPCell pdfPC0020 = new PdfPCell(new Paragraph("                                                    "));
        pdfPC0020.setBorder(Rectangle.NO_BORDER);
        pdfPTable30.addCell(pdfPC0020);
          try{
           
          String famNo = "SELECT SUM(produce.amount), rates.cost FROM rates INNER JOIN produce WHERE produce.day>='"+start+"' AND produce.day<='"+end+"' AND rates.startday>='"+start+"' AND rates.endday<='"+end+"' AND produce.farmernumber='"+farmernum.getText()+"' "; 
          pst = conn.prepareStatement(famNo);
          rs = pst.executeQuery();
          while(rs.next()){
              int ltrAmount = rs.getInt("SUM(produce.amount)");
              double cst=rs.getDouble("rates.cost");
              double payment=ltrAmount*cst; 
              
        
        PdfPTable pdfPTable15 = new PdfPTable(3);
        PdfPCell pdfPC13 = new PdfPCell(new Paragraph("Total Amount Delivered: "+ltrAmount+"ltrs"));
        PdfPCell pdfPC14= new PdfPCell(new Paragraph("Cost per Litre: "+cst+"Ksh"));
        PdfPCell pdfPC140= new PdfPCell(new Paragraph("Total money payable: "+payment+"Ksh"));
        pdfPC14.setBorder(Rectangle.NO_BORDER);
        pdfPC13.setBorder(Rectangle.NO_BORDER);
        pdfPC140.setBorder(Rectangle.NO_BORDER);
        pdfPTable15.addCell(pdfPC13);
        pdfPTable15.addCell(pdfPC14);
         pdfPTable15.addCell(pdfPC140);
         
        
        
        
        document.add(pdfPTable2);document.add(pdfPTable05);document.add(pdfPTable3);document.add(pdfPTable4);document.add(pdfPTable5);document.add(pdfPTable6);
        document.add(pdfPTable);document.add(pdfPTable15);document.add(pdfPTable30 ); document.add(pdfPTable7);document.add(pdfPTable8);

        //Close document and outputStream.
        document.close();
        outputStream.close();
         DisplayAmount();
         TotalLitre();
            }    
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "iko apa");
       }
        
}
@FXML
    private void onprint(ActionEvent event) throws DocumentException, IOException, SQLException {
        printRecipt();
    }

}
