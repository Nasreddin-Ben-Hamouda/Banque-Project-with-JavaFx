package application.controllers.client;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import DAO.DaoAccountTable;
import application.controllers.counter.AccountInterface;
import entities.Account;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.Person;
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

public class ListAccountsForClientController implements Initializable{
	@FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<AccountInterface> tableview;
  
    @FXML private TableColumn<AccountInterface, String> type;
    @FXML private TableColumn<AccountInterface, String> money;
    @FXML private TableColumn<AccountInterface, String> threshold;
    @FXML private TableColumn<AccountInterface, String> isAvailable;
    
    @FXML private TableColumn<AccountInterface, String> openingDate;
    @FXML private TableColumn<AccountInterface, String> closingDate;
    @FXML private TableColumn<AccountInterface, String> owner;
    @FXML private TableColumn<AccountInterface, String> createdBy;
    private Long clientId;
    
    //observalble list to store data
    private final ObservableList<AccountInterface> dataList = FXCollections.observableArrayList();
    
    public void setClientId(Long id) {
    	this.clientId = id;
        //we must create a ClientInterface filled with client entities class with attributes like this  this.EmpID = new SimpleIntegerProperty(id);           
       // EmpID.setCellValueFactory(new PropertyValueFactory<>("EmpID"));       
    	type.setCellValueFactory(new PropertyValueFactory<>("type"));        
    	money.setCellValueFactory(new PropertyValueFactory<>("money"));        
    	threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));        
    	isAvailable.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        //tel.setText(arg0);
 
    	openingDate.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
    	closingDate.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
    	owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
    	createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));

    	List<Account> res1 = DaoAccountTable.getAllAccountsForOneClient(this.clientId);

    	List<AccountInterface> resFinal = new LinkedList<>();
    	for(int i=0;i<res1.size();i++) {
    		resFinal.add(new AccountInterface(res1.get(i)));
    	}
    	dataList.addAll(resFinal);
        
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AccountInterface> filteredData = new FilteredList<>(dataList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(account -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (account.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (account.getOwner().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (account.getIsAvailable().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else  {
				     return false; // Does not match.
				}
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<AccountInterface> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);
    }
            

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      

    } 

}
