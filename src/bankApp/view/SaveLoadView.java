/**
 * SaveLoadView
 * 
 * Class view for saving bank and transaction to file and opening bank and transaction from file.
 * Class connected to associated controller.
 */


package bankApp.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Optional;

import bankApp.controller.SaveLoadController;
import bankApp.model.Account;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class SaveLoadView {
	private SaveLoadController controller;
	private GridPane root;
	private int accountId;
	private String pNo;
	
	/**
	 * Constructor.
	 * @param controller SaveLoadController.
	 */
	public SaveLoadView(SaveLoadController controller) {
		this.controller = controller;
		createView();
	}
	
	
	/**
	 * Create save & load view.
	 */
	public void createView() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		// save Bank to File
		Label lblSaveBank = new Label("Save Bank to File");
		root.add(lblSaveBank, 0, 0);
		Button btnSaveToFile = new Button("Save to file");
		root.add(btnSaveToFile, 0, 1);

		
		// filter file type, .dat
		ExtensionFilter datExtension = new ExtensionFilter("Binary files", "*.dat");
		
		btnSaveToFile.setOnAction(e -> {
			File selectedFile = null;
			
			try {
				FileChooser fileChooser = new FileChooser();

			    // title (seems not visible on MacOS?)
			    fileChooser.setTitle("Save Bank File");
			    // open directory
			    fileChooser.setInitialDirectory(new File("src/bankApp/files/"));
			    // add filter (on MacOS, only .txt is possible to choose (highlighted) ?)
				// extension choose not visible on MacOS?
			    fileChooser.getExtensionFilters().add(datExtension);
			    // use Stage as "owner"
			    selectedFile = fileChooser.showSaveDialog(controller.getStage());
			    // If file not chosen, cancel
			    if (selectedFile == null) {
			        return;
			    }
			} catch (IllegalArgumentException ex) {
				showExceptionAlert("File path", "No such file path");
				return;
			} catch (Exception ex) {
				showExceptionAlert("File path", "No such file path");
				return;
			}
		    

		    // If file already exist
		    // show alert
		    if (selectedFile.exists()) {
		        Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION);
		        overwriteAlert.setTitle("Overwrite file");
		        overwriteAlert.setHeaderText(null);
		        overwriteAlert.setContentText("File already exist. Save anyway?");

		        Optional<ButtonType> answer = overwriteAlert.showAndWait();

		        // if user cancel
		        if (answer.isEmpty() || answer.get() != ButtonType.OK) {
		            return;
		        }
		    }
		    // save to file
		    try {
		    	controller.saveBank(selectedFile.getPath());
//		    	controller.saveBank(selectedFile.getAbsolutePath());
		    } catch (NoSuchFileException nsfe) {
		    	showExceptionAlert("File path", "No such file path");
		    }  catch (NotSerializableException se) {
		    	showExceptionAlert("Serialize", "Failed Serializing!");
			} catch (FileNotFoundException fe) {
				showExceptionAlert("File not found", "File not found!");
			} catch (ClassNotFoundException ce) {
				showExceptionAlert("File error", "Wrong file type, or corrupt file!");
			} catch (IOException ie) {
				showExceptionAlert("IO Exception", "Problem reading the file!");
			}
		    
		});
		
		
		
		// Load Bank from File
		Label lblOpenBank = new Label("Open Bank File");
		root.add(lblOpenBank, 0, 4);
		Button btnOpenBank = new Button("Open");
		root.add(btnOpenBank, 0, 5);
		
		// ***********************
		// ***********************
		// ***********************
		// ***********************
		// hidden label
		Label lblFileOpened = new Label();
		lblFileOpened.setVisible(false);
		root.add(lblFileOpened, 1, 5, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		btnOpenBank.setOnAction( e -> {
			File selectedFile = null;
			
			try {
				FileChooser openChooser = new FileChooser();
				
				// add filter (on MacOS, only .txt is possible to choose (highlighted))
				// extension choose not visible on MacOS?
				openChooser.getExtensionFilters().addAll(datExtension);
				// title (seems not visible on MacOS?)
				openChooser.setTitle("Open Bank file");
				// open directory
				openChooser.setInitialDirectory(new File("src/bankApp/files/"));
				
				// use Stage as "owner"
				selectedFile = openChooser.showOpenDialog(controller.getStage());
				
				// show label
				lblFileOpened.setText("File opened");
				lblFileOpened.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblFileOpened.setVisible(false));
				pause.play();
			}  catch (IllegalArgumentException ex) {
				showExceptionAlert("Path/permission", "Path or permission fail!");
			} catch (Exception ex) {
			showExceptionAlert("Path/permission", "Path or permission fail!");
		}
			
			
			if(selectedFile != null) {
				// selectedFile.getPath() get path to selected file
				try {
					controller.loadBank(selectedFile.getPath());
				} catch (NotSerializableException se) {
					showExceptionAlert("Deserialize", "Failed deserializing!");
				} catch (FileNotFoundException fe) {
					showExceptionAlert("File not found", "File not found!");
				} catch (ClassNotFoundException ce) {
					showExceptionAlert("File error", "Wrong file type, or corrupt file!");
				} catch (IOException ie) {
					showExceptionAlert("IO Exception", "Problem reading the file!");
				}
				
			}
		});
		
		
		// save account transactions to File
		Label lblAccTransactions = new Label("Save statement to File");
		root.add(lblAccTransactions, 0, 8, 1, 1);  // (columnIndex, rowIndex, columnSpan, rowSpan)
		Label lblSsn = new Label("Ssn:");
		root.add(lblSsn, 0, 9, 1, 1);
		TextField tfSsn = new TextField();
		root.add(tfSsn, 1, 9, 1, 1);
		
		Button btnCustomerAccounts = new Button("Show accounts");
		root.add(btnCustomerAccounts, 1, 10);
		
		ListView<Account> customerAccountsList = new ListView<>();
		
		// Click btn -> show accounts
		btnCustomerAccounts.setOnAction(e -> {
			// Get Accounts of customer
			// set Accounts to ListView
			ArrayList<Account> accounts = controller.getAccounts(tfSsn.getText());
			// prevent from crash if no accounts
			if(accounts == null || accounts.isEmpty()) {
				return;
			}
			
			// get pNp
			pNo = tfSsn.getText();
			customerAccountsList.getItems().setAll(controller.getAccounts(pNo));
		});
		
		customerAccountsList.setPrefWidth(350);
		customerAccountsList.setPrefHeight(120);
		root.add(customerAccountsList, 0, 11, 4, 5);  // (columnIndex, rowIndex, columnSpan, rowSpan)
		
		
		ListView<String> transactionsList = new ListView<>();
		
		// Click account -> get transactions
		customerAccountsList.setOnMouseClicked(e -> {
			Account clickedAccount = customerAccountsList.getSelectionModel().getSelectedItem();
			
			if(customerAccountsList.getItems().isEmpty()) {
				return;
			}
			// prevent crash if click beside object inside ListView 
			if(clickedAccount == null) {
				return;
			}
			// get account id
			accountId = clickedAccount.getAccountNumber();
			
			ArrayList<String> transactions = controller.getTransactions(tfSsn.getText(), accountId);
			
			if(transactions == null || transactions.isEmpty()) {
				transactionsList.getItems().setAll("No transactions");
			}
			else {
				transactionsList.getItems().setAll(transactions);
			}
		});
		
		transactionsList.setPrefWidth(450);
		transactionsList.setPrefHeight(120);
		root.add(transactionsList, 6, 11, 9, 5);  // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		
		Button btbSaveTransToFile = new Button("Save statement");
		root.add(btbSaveTransToFile, 0, 17);	
		
		// hidden label
		Label lblStatementSaved = new Label();
		lblStatementSaved.setVisible(false);
		root.add(lblStatementSaved, 1, 17, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// click btn
		// send pNo & account id to SaveLoadAccountStatement
		btbSaveTransToFile.setOnAction(e -> {
			
			// prevent crash on empty list
			if(customerAccountsList.getItems().isEmpty()) {
				return;
			}
			
			try {
				controller.saveStatement(pNo, accountId);
				lblStatementSaved.setText("Statement saved");
				lblStatementSaved.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lblStatementSaved.setVisible(false));
				pause.play();
			} catch (FileNotFoundException fe) {
				showExceptionAlert("Path/permission", "Path or permission fail!");
					fe.printStackTrace();
			} catch (IOException ie) {
				showExceptionAlert("IO Exception", "Problem writing file!");
			}
		});
		
		
		// Open statement .txt
		Label lblOpenStatement = new Label("Open statement");
		root.add(lblOpenStatement, 0, 19);
		
		Button btnOpenStatement = new Button("Open statement");
		root.add(btnOpenStatement, 0, 20);
		
	
		// open statement
		btnOpenStatement.setOnAction(e -> {
			TextArea statementArea =  new TextArea();
			statementArea.setEditable(false);
			// create Dialog
			Dialog<String> dialog = new Dialog<>();
			dialog.setTitle("Show statement");
			dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			
			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
			statementArea.setPrefHeight(300);
			statementArea.setPrefWidth(450);
			
			// btn to open FileChooser
			Button btnOpen = new Button("Open");
			VBox contentBox = new VBox(statementArea, btnOpen);
			dialog.getDialogPane().setContent(contentBox);
			
			// filter file type, .txt
			ExtensionFilter txtExtension = new ExtensionFilter("Text files", "*.txt");
			
			// btn in Dialog Window to open FileChooser
			btnOpen.setOnAction( ev -> {
				File selectedFile = null;
				try {
					FileChooser openChooser = new FileChooser();
					
					// add filter (on MacOS, only .txt is possible to choose (highlighted))
					// extension choose not visible on MacOS?
					openChooser.getExtensionFilters().addAll(txtExtension);
					// title (seems not visible on MacOS?)
					openChooser.setTitle("Open statement");
					// open directory
					openChooser.setInitialDirectory(new File("src/bankApp/files/"));
					
					// use Stage as "owner"
					selectedFile = openChooser.showOpenDialog(controller.getStage());
				} catch (IllegalArgumentException ex) {
					showExceptionAlert("Path/permission", "Path or permission fail!");
				} catch (Exception ex) {
					showExceptionAlert("Path/permission", "Path or permission fail!");
				}
				
				if(selectedFile != null) {
					// selectedFile.getPath() get path to selected file
					// read file
					// set text to TextArea
					try {
						statementArea.setText(controller.loadStatement(selectedFile.getPath()));	
					} catch(FileNotFoundException fe) {
						showExceptionAlert("Path/permission", "Path or permission fail!");
					} catch(IOException ie) {
						showExceptionAlert("IO Exception", "Problem opening file!");
					}
				}
			});
			dialog.show();
		});
		root.setAlignment(Pos.TOP_LEFT);
	}
	
	
	// Helper Alert
	private void showExceptionAlert(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText(null);
	    alert.setTitle(title);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	/**
	 * Get Root.
	 * @return GridPane.
	 */
	public GridPane getRoot() {
		return root;
	}
}