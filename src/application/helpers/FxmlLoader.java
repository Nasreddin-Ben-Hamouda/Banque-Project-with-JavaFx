package application.helpers;

import java.io.FileNotFoundException;

import java.net.URL;

import application.controllers.client.ShowClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {
     private Pane view;
     
     public FXMLLoader getPage(String fileName) {
    	 FXMLLoader loader = null;
    	 try {
    		loader = new FXMLLoader(getClass().getResource("/application/views/"+fileName+".fxml"));
    		 //view = loader.load();
    	 }catch(Exception e) {
    		 e.printStackTrace();
    		 //System.out.println("no page in "+fileName);
    	 }
    	 return loader;
     }
     
}
