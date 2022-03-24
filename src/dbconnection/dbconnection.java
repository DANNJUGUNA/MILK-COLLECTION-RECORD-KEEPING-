package dbconnection;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author KARONEI
 */
public class dbconnection {

    Connection conn;

    public static Connection milk_db() {

        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

            //JOptionPane.showMessageDialog(null, "Succefull Connected");
            return conn;
        } catch (ClassNotFoundException | SQLException | HeadlessException ex) {
           // System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex.toString());
            return null;
        }

    }
}