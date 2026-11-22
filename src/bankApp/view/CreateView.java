/**
 * CreateView
 * 
 * Class implements nodes & logic to nodes associated to creating customers & accounts.
 * Class connected to associated controller.
 */


package bankApp.view;

import bankApp.controller.CreateController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class CreateView {
	CreateController controller;
	GridPane root;
	
	/**
	 * Constructor.
	 * @param controller CreateController.
	 */
	public CreateView(CreateController controller) {
		this.controller = controller;
		createUI();
	}
	
	
	/**
	 * Create 'Create' view.
	 */
	public void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		
		// ***************************************************************************
		// Create Customer
		Label lblCreateUser = new Label("New customer");
		root.add(lblCreateUser, 0, 3, 3, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		Label lblName = new Label("First name:");
		root.add(lblName, 0, 4, 1, 1);
		
		TextField tfName = new TextField();
		root.add(tfName, 1, 4, 1, 1);
		
		Label lblSurname = new Label("Last name:");
		root.add(lblSurname, 0, 5, 1, 1);
		
		TextField tfSurname = new TextField();
		root.add(tfSurname, 1, 5, 1, 1);
		
		Label lblPno = new Label("Ssn:");
		root.add(lblPno, 0, 6, 1, 1);
		
		TextField tfPno = new TextField();
		root.add(tfPno, 1, 6, 1, 1);
		
		Button btnCreateCustomer = new Button("Ok");
		root.add(btnCreateCustomer, 1, 7, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// hidden label.
		Label lbCustomerCreated = new Label("Ok");
		lbCustomerCreated.setVisible(false);
		root.add(lbCustomerCreated, 1, 8, 1, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// create customer.
		btnCreateCustomer.setOnAction(e -> {
			if(tfName.getText().isEmpty() || tfSurname.getText().isEmpty() || tfPno.getText().isEmpty()) {
				lbCustomerCreated.setVisible(false);
			}
			// check if pNo is 10 digits
			// show fail label
			boolean isPno = controller.isPno(tfPno.getText());
			if(!isPno) {
				lbCustomerCreated.setText("Ssn failed");
				lbCustomerCreated.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lbCustomerCreated.setVisible(false));
				pause.play();
			}
			// create customer
			// show created label
			else {
				controller.btnCreateCustomer(tfName.getText(), tfSurname.getText(), tfPno.getText());
				lbCustomerCreated.setText("Customer created");
				lbCustomerCreated.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lbCustomerCreated.setVisible(false));
				pause.play();
				tfName.clear();
				tfSurname.clear();
				tfPno.clear();
			}			
		});
        
		
		// ***************************************************************************
		// Create savings account
		Label lblCreateSavingsAcc = new Label("New savings account");
	 	// root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
        root.add(lblCreateSavingsAcc, 0, 13, 5, 1);
        
        // pNo
        Label lblSavingsPno = new Label("Ssn:");
        root.add(lblSavingsPno, 0, 14, 1, 1);
        TextField tfSavingsPno = new TextField();
        root.add(tfSavingsPno, 1, 14, 5, 1);
        
        Button btnCreateSavings = new Button("Ok");
        root.add(btnCreateSavings, 1, 15, 1, 1);
        
        Label lbSavingsCreated = new Label();
        lbSavingsCreated.setVisible(false);
		root.add(lbSavingsCreated, 1, 16, 5, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
        
		// create savings account
        btnCreateSavings.setOnAction(e -> {
        	if(tfSavingsPno.getText().isEmpty()) {
        		lbSavingsCreated.setVisible(false);
        	}
        	else {
        		String pNo = tfSavingsPno.getText();
        		if(!controller.customerExist(pNo)) {
        			lbSavingsCreated.setText("No such customer");
        			lbSavingsCreated.setVisible(true);
					PauseTransition pause = new PauseTransition(Duration.seconds(4));
					pause.setOnFinished(event -> lbSavingsCreated.setVisible(false));
					pause.play();
					return;
				}
        		controller.btnCreateSavings(tfSavingsPno.getText());
        		lbSavingsCreated.setText("Savings account created");
        		lbSavingsCreated.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lbSavingsCreated.setVisible(false));
				pause.play();
            	tfSavingsPno.clear();
        	}
		});


        
        // ***************************************************************************
 		// Create credit account
        Label lblCreateCreditAcc = new Label("New credit account");
        root.add(lblCreateCreditAcc, 0, 18, 5, 1);
        
        // pNp
        Label lblCreditPno = new Label("Ssn:");
        root.add(lblCreditPno, 0, 19, 1, 1);
        TextField tfCreditPno = new TextField();
        root.add(tfCreditPno, 1, 19, 5, 1);
        
        Button btnCreateCredit = new Button("Ok");
        root.add(btnCreateCredit, 1, 20, 1, 1);
        
        Label lbCreditCreated = new Label();
        lbCreditCreated.setVisible(false);
		root.add(lbCreditCreated, 1, 21, 5, 1); // root.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
		
		// create credit account
        btnCreateCredit.setOnAction(e -> {
        	if(tfCreditPno.getText().isEmpty()) {
        		lbCreditCreated.setVisible(false);
        	}
        	else {
        		String pNo = tfCreditPno.getText();
        		if(!controller.customerExist(pNo)) {
        			lbCreditCreated.setText("No such customer");
        			lbCreditCreated.setVisible(true);
					PauseTransition pause = new PauseTransition(Duration.seconds(4));
					pause.setOnFinished(event -> lbCreditCreated.setVisible(false));
					pause.play();
					return;
				}
        		controller.btnCreateCredit(tfCreditPno.getText());
        		lbCreditCreated.setText("Credit account created");
        		lbCreditCreated.setVisible(true);
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(event -> lbCreditCreated.setVisible(false));
				pause.play();
            	tfCreditPno.clear();
        	}
        	
		});
        
		
		root.setAlignment(Pos.TOP_LEFT);
	}
	
	/**
	 * Get Root.
	 * @return GridPane.
	 */
	public GridPane getRoot() {
		return root;
	}

}
