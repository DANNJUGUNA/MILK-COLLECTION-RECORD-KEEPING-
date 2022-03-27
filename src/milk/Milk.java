/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package milk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author DAN
 */
public class Milk extends Application {
    
   @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/deliveryrecord.fxml"));
        Parent root=FXMLLoader.load(getClass().getResource("/gui/deliveryrecord.fxml"));
        stage.setTitle("milk collection record keeing application");
        //stage.setFullScreen(true);
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
    
