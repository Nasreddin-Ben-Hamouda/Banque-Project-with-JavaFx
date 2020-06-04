package application.controllers.client;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import DAO.DaoClientTable;
import entities.Address;
import entities.Client;
import entities_enums.CivilState;
import entities_enums.Continent;
import entities_enums.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
public class ListClientsController implements Initializable {
	
    @FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<ClientInterface> tableview;
    @FXML private TableColumn<ClientInterface, String> number;
    @FXML private TableColumn<ClientInterface, String> empName;
    @FXML private TableColumn<ClientInterface, String> email;
    @FXML private TableColumn<ClientInterface, String> cin;
    @FXML private TableColumn<ClientInterface, String> tel;
    
    @FXML private TableColumn<ClientInterface, String> dateNaiss;
    @FXML private TableColumn<ClientInterface, String> sex;
    @FXML private TableColumn<ClientInterface, String> etatCivile;
    @FXML private TableColumn<ClientInterface, String> adresse;
    
   
                  
    //observalble list to store data
    private final ObservableList<ClientInterface> dataList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        //we must create a ClientInterface filled with client entities class with attributes like this  this.EmpID = new SimpleIntegerProperty(id);           
        number.setCellValueFactory(new PropertyValueFactory<>("id"));        
    	empName.setCellValueFactory(new PropertyValueFactory<>("nom"));        
        email.setCellValueFactory(new PropertyValueFactory<>("email"));        
        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));        
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        //tel.setText(arg0);
 
        dateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        etatCivile.setCellValueFactory(new PropertyValueFactory<>("etatCivile"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        
        
        List<Client> res1 = DaoClientTable.getAllClients();
        List<ClientInterface> resFinal = new LinkedList<>();
        
        for(int i=0;i<res1.size();i++) {
        	resFinal.add(new ClientInterface(res1.get(i)));
        }
        dataList.addAll(resFinal);
 
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ClientInterface> filteredData = new FilteredList<>(dataList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(client -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (client.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (client.getCin().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (client.getSex().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else if (client.getEtatCivile().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else{
				     return false; // Does not match.
				}
			});
		});
		
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ClientInterface> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);
    } 
}

    
