/**
 * LoginView
 * 
 * Class implements nodes resembling a login - no logic.
 * Class connected to associated controller.
 */

package bankApp.view;

import bankApp.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {
	LoginController controller;
	GridPane root;
	
	/**
	 * Constructor.
	 * @param controller LoginController.
	 */
	public LoginView(LoginController controller) {
		this.controller = controller;
		createUI();
	}
	
	/**
	 * Create login view.
	 */
	public void createUI() {
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		
		Label lbluserName = new Label("user name:");
		root.add(lbluserName, 0, 0);
		
		TextField tfUserName = new TextField();
		root.add(tfUserName, 3, 0);

		Label lblPassword = new Label("password:");
		root.add(lblPassword, 0, 1);
		
		TextField tfPassword = new TextField();		
		root.add(tfPassword, 3, 1);

		Button btnLogin = new Button("Login");
		btnLogin.setOnAction(e -> controller.btnLogin());
		root.add(btnLogin, 3, 3);
		
		root.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Get Root.
	 * @return GridPane.
	 */
	public GridPane getRoot() {
		return root;
	}
}
